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

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.data.dao.IDiscountByPassengerTypeDao;
import com.rumbo.favs.data.entities.DiscountByPassengerType;
import com.rumbo.favs.data.entities.DiscountByPassengerTypeGroup;
import com.rumbo.favs.data.utilities.ManageProperties;

/**
 * Infant Price DAO
 * 
 */
public class DiscountByPassengerTypeDaoImpl implements IDiscountByPassengerTypeDao{
	
	private Node nodeDiscountByPassengerType = null;
	
	private final String DISCOUNTBYPASSENGERTYPEFILE = "discountByPassengerTypeFile";
	
	private final String CSVEXTENSION = ".csv";
	
	public DiscountByPassengerTypeDaoImpl(){
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
			String daysToDepartureDateFile = manageProperties.getFilesProperty(DISCOUNTBYPASSENGERTYPEFILE);			
			if (daysToDepartureDateFile != null && !daysToDepartureDateFile.isEmpty()){
				csvToXmlDiscountByPassengerType(csvFileResourceFolder + daysToDepartureDateFile + CSVEXTENSION);
			}
		}		
	}	
	
	private void csvToXmlDiscountByPassengerType(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty() ){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
			DiscountByPassengerTypeGroup discountByPassengerTypeGroup = new DiscountByPassengerTypeGroup();

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvDiscountByPassengerType = line.split(splitBy);
					DiscountByPassengerType discountByPassengerType = 
							new DiscountByPassengerType(csvDiscountByPassengerType[0],csvDiscountByPassengerType[1]);
					discountByPassengerTypeGroup.getDiscountByPassengerTypeGroup().add(discountByPassengerType);
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
				JAXBContext jaxbContext = JAXBContext.newInstance(DiscountByPassengerTypeGroup.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				DOMResult res = new DOMResult();
				jaxbMarshaller.marshal(discountByPassengerTypeGroup, res);

				if (res != null){
					setNodeDiscountByPassengerType(res.getNode());
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
	public DiscountByPassengerType getDiscountPercent(PassengerType passengerType) {
		
		try {			
			if (getNodeDiscountByPassengerType() != null){
			    
			    XPath xPath =  XPathFactory.newInstance().newXPath();
				
			    //Query
				String expression = "/discountByPassengerTypeGroup/discountByPassengerType[" + IDiscountByPassengerTypeDao.PASSENGERTYPE + "='"+ passengerType.toString() + "']";		    
				NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getNodeDiscountByPassengerType(), XPathConstants.NODESET);
				
				//None result
				if (nodeList.getLength() == 0){
					return null;
				}
							
	            Node nNode = nodeList.item(0);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element element = (Element) nNode;
	               return getDiscountByPassengerType(element);
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
	private DiscountByPassengerType getDiscountByPassengerType(Element element){
		
		DiscountByPassengerType discountByPassengerType = null;
		
		if (element != null){
			discountByPassengerType = new DiscountByPassengerType(element.getElementsByTagName(IDiscountByPassengerTypeDao.PASSENGERTYPE).item(0).getTextContent(),
					element.getElementsByTagName(IDiscountByPassengerTypeDao.DISCOUNTPERCENT).item(0).getTextContent());			
		}		
		
		return discountByPassengerType;
	}

	public Node getNodeDiscountByPassengerType() {
		return nodeDiscountByPassengerType;
	}

	public void setNodeDiscountByPassengerType(Node nodeDiscountByPassengerType) {
		this.nodeDiscountByPassengerType = nodeDiscountByPassengerType;
	}	
	
}
