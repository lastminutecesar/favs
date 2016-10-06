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

import com.rumbo.favs.data.dao.IDaysToDepartureDateDao;
import com.rumbo.favs.data.entities.DaysToDepartureDate;
import com.rumbo.favs.data.entities.DaysToDepartureDateGroup;
import com.rumbo.favs.data.utilities.ManageProperties;

/**
 * DaysToDepartureDate DAO
 * Manage days to departure date discount
 * 
 * This implimentation works with csv files
 * and turn the information into dom object
 * to let launch queries
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class DaysToDepartureDateDaoImpl implements IDaysToDepartureDateDao{
	
	private Node nodeDaysToDepartureDate = null;
	
	private final String DAYSTODEPARTUREDATEFILE = "daysToDepartureDateFile";
	
	private final String CSVEXTENSION = ".csv";
	
	public DaysToDepartureDateDaoImpl(){
		loadFile();
	}
	
	/**
	 * Get csv file name from properties file
	 * and turn the information into dom object
	 * 
	 */
	private void loadFile(){
		
		ManageProperties manageProperties = new ManageProperties();
		
		String csvFileResourceFolder = manageProperties.getConfigProperty(ManageProperties.CSV_FILE_RESOURCE_FOLDER);
		
		if (csvFileResourceFolder != null && !csvFileResourceFolder.isEmpty()){			
			String daysToDepartureDateFile = manageProperties.getFilesProperty(DAYSTODEPARTUREDATEFILE);			
			if (daysToDepartureDateFile != null && !daysToDepartureDateFile.isEmpty()){
				csvToXmlDaysToDepartureDate(csvFileResourceFolder + daysToDepartureDateFile + CSVEXTENSION);
			}
		}		
	}	
	
	/**
	 * From csvFile turn the information into dom object
	 * 
	 * @param csvFile
	 */
	private void csvToXmlDaysToDepartureDate(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
			DaysToDepartureDateGroup daysToDepartureDateGroup = new DaysToDepartureDateGroup();

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvDaysToDepartureDate = line.split(splitBy);
					DaysToDepartureDate daysToDepartureDate = 
							new DaysToDepartureDate(csvDaysToDepartureDate[0],csvDaysToDepartureDate[1],csvDaysToDepartureDate[2]);
					daysToDepartureDateGroup.getDaysToDepartureDateGroup().add(daysToDepartureDate);
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
			
			// Get dom object through JAXB
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(DaysToDepartureDateGroup.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				DOMResult res = new DOMResult();
				jaxbMarshaller.marshal(daysToDepartureDateGroup, res);
				
				if (res != null){
					setNodeDaysToDepartureDate(res.getNode());
				}
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get days to departure date 
	 * by a given day
	 * 
	 * @param int days
	 * @return DaysToDepartureDate
	 */
	public DaysToDepartureDate getDiscountPercent(int days) {
		
		try {			
			if (getNodeDaysToDepartureDate() != null){
			    
			    XPath xPath =  XPathFactory.newInstance().newXPath();
				
			    // Query
				String expression = "/daysToDepartureDateGroup/daysToDepartureDate[" + IDaysToDepartureDateDao.MIN + "<=" + days + " and " + IDaysToDepartureDateDao.MAX + ">=" + days + "]";	
				NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getNodeDaysToDepartureDate(), XPathConstants.NODESET);
				
				// None result
				if (nodeList.getLength() == 0){
					return null;
				}
				
				// Get business object from dom (query result)
	            Node nNode = nodeList.item(0);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element element = (Element) nNode;
	               return getDaysToDepartureDate(element);
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
	 * Get DaysToDepartureDate from dom object
	 * 
	 * @param element 
	 * @return DaysToDepartureDate
	 */
	private DaysToDepartureDate getDaysToDepartureDate(Element element){
		
		DaysToDepartureDate daysToDepartureDate = null;
		
		if (element != null){
			daysToDepartureDate = new DaysToDepartureDate(element.getElementsByTagName(IDaysToDepartureDateDao.MIN).item(0).getTextContent(),
					element.getElementsByTagName(IDaysToDepartureDateDao.MAX).item(0).getTextContent(),
					element.getElementsByTagName(IDaysToDepartureDateDao.DISCOUNTPERCENT).item(0).getTextContent());			
		}		
		
		return daysToDepartureDate;
	}

	public Node getNodeDaysToDepartureDate() {
		return nodeDaysToDepartureDate;
	}

	public void setNodeDaysToDepartureDate(Node nodeDaysToDepartureDate) {
		this.nodeDaysToDepartureDate = nodeDaysToDepartureDate;
	}	
	
}
