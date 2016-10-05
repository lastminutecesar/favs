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

import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.entities.Airport;
import com.rumbo.favs.data.entities.AirportGroup;
import com.rumbo.favs.data.utilities.ManageProperties;

/**
 * Infant Price DAO
 * 
 */
public class AirportDaoImpl implements IAirportDao{

	private Node nodeAirport = null;
	
	private final String AIRPORTSFILE = "airportsFile";
	
	private final String CSVEXTENSION = ".csv";
	
	public AirportDaoImpl(){
		loadFile();
	}
	
	/**
	 * Write xml files from csv files
	 * 
	 * Get all files from files.Properties
	 * 
	 */
	private void loadFile(){
		
		ManageProperties manageProperties = new ManageProperties();
		
		String csvFileResourceFolder = manageProperties.getConfigProperty(ManageProperties.CSV_FILE_RESOURCE_FOLDER);
		
		if (csvFileResourceFolder != null && !csvFileResourceFolder.isEmpty()){						
			
			String airportFile = manageProperties.getFilesProperty(AIRPORTSFILE);			
			if (airportFile != null && !airportFile.isEmpty()){
				csvToXmlAirport(csvFileResourceFolder + airportFile + CSVEXTENSION);
			}
		}		
	}	
	
	private void csvToXmlAirport(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
			AirportGroup airportGroup = new AirportGroup();

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvAirport = line.split(splitBy);
					Airport airport = new Airport(csvAirport[0],csvAirport[1]);
					airportGroup.getAirportGroup().add(airport);
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
				JAXBContext jaxbContext = JAXBContext.newInstance(AirportGroup.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				
				DOMResult res = new DOMResult();
				jaxbMarshaller.marshal(airportGroup, res);
				
				if (res != null){
					setNodeAirport(res.getNode());
				}				
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get infant price by airline
	 * 
	 * @return InfantPrice
	 */
	public Airport getAirportByIataCode(String iataCode) {
		
		try {			
			if (getNodeAirport() != null){
			    
			    XPath xPath =  XPathFactory.newInstance().newXPath();
				
			    //Query
				String expression = "/airportGroup/airport[" + IAirportDao.IATACODE + "='"+ iataCode + "']";		    
				NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getNodeAirport(), XPathConstants.NODESET);
				
				//None result
				if (nodeList.getLength() == 0){
					return null;
				}
							
		        Node nNode = nodeList.item(0);
		        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		           Element element = (Element) nNode;
		           return getAirport(element);
		        }
			}            
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get infantPrice from xml element
	 * 
	 * @return InfantPrice
	 */
	private Airport getAirport(Element element){
		
		Airport airport = null;
		
		if (element != null){
			airport = new Airport(element.getElementsByTagName(IAirportDao.IATACODE).item(0).getTextContent(),
					element.getElementsByTagName(IAirportDao.CITY).item(0).getTextContent());			
		}		
		
		return airport;
	}

	public Node getNodeAirport() {
		return nodeAirport;
	}

	public void setNodeAirport(Node nodeAirport) {
		this.nodeAirport = nodeAirport;
	}	
		
}
