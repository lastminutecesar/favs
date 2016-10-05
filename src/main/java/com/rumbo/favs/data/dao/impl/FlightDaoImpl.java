package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.dom.DOMResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rumbo.favs.data.dao.IFlightDao;
import com.rumbo.favs.data.entities.AirportGroup;
import com.rumbo.favs.data.entities.Flight;
import com.rumbo.favs.data.entities.FlightGroup;
import com.rumbo.favs.data.utilities.ManageProperties;

/**
 * Flights (Itinerary) DAO
 * 
 */
public class FlightDaoImpl implements IFlightDao {
		
	private Node nodeFlight = null;
	
	private final String FLIGHTSFILE = "flightsFile";
	
	private final String CSVEXTENSION = ".csv";
	
	public FlightDaoImpl(){
		loadFile();
	}
	
	/**
	 * Write xml files from csv files
	 * 
	 */
	private void loadFile(){
		
		ManageProperties manageProperties = new ManageProperties();
		
		String csvFileResourceFolder = manageProperties.getConfigProperty(ManageProperties.CSV_FILE_RESOURCE_FOLDER);
		
		if (csvFileResourceFolder != null && !csvFileResourceFolder.isEmpty()){						
			
			String flightFile = manageProperties.getFilesProperty(FLIGHTSFILE);			
			if (flightFile != null && !flightFile.isEmpty()){
				csvToXmlFlight(csvFileResourceFolder + flightFile + CSVEXTENSION);
			}
		}		
	}	
	
	private void csvToXmlFlight(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
			FlightGroup flightGroup = new FlightGroup();

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvFlight = line.split(splitBy);
					Flight flight = new Flight(csvFlight[0],csvFlight[1],csvFlight[2],csvFlight[3]);
					flightGroup.getFlightGroup().add(flight);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			// Write xml file with JAXB
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(FlightGroup.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				
				DOMResult res = new DOMResult();
				jaxbMarshaller.marshal(flightGroup, res);
				
				if (res != null){
					setNodeFlight(res.getNode());
				}				
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get available flights from origin and destination
	 * 
	 * @return Flights
	 */
	public FlightGroup getFlightsByItinerary(String origin, String destination) {
		
		FlightGroup flights = new FlightGroup();
				
		try {			
			if (getNodeFlight() != null){
			    
			    XPath xPath =  XPathFactory.newInstance().newXPath();
				
			    //Query
				String expression = "/flightGroup/flight[" + IFlightDao.ORIGIN + "='" + origin + "' and " + IFlightDao.DESTINATION + "='" + destination  + "']";		    
				NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getNodeFlight(), XPathConstants.NODESET);
				
				//None result
				if (nodeList.getLength() == 0){
					return null;
				}
				
				//Iterate query result
				for (int i = 0; i < nodeList.getLength(); i++) {
		            Node nNode = nodeList.item(i);
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element element = (Element) nNode;
		               Flight flight = getFlight(element);
		               if (flight != null){
		            	   flights.getFlightGroup().add(flight);
		               }
		            }
		         }
			}		
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flights;
	}
	
	/**
	 * Get flight from xml element
	 * 
	 * @return Flights
	 */
	private Flight getFlight(Element element){
		
		Flight flight = null;
		
		if (element != null){
			flight = new Flight(element.getElementsByTagName(IFlightDao.ORIGIN).item(0).getTextContent(),
					element.getElementsByTagName(IFlightDao.DESTINATION).item(0).getTextContent(),
					element.getElementsByTagName(IFlightDao.AIRLINE).item(0).getTextContent(),
					element.getElementsByTagName(IFlightDao.BASEPRICE).item(0).getTextContent());			
		}		
		
		return flight;
	}

	public Node getNodeFlight() {
		return nodeFlight;
	}

	public void setNodeFlight(Node nodeFlight) {
		this.nodeFlight = nodeFlight;
	}
	
}
