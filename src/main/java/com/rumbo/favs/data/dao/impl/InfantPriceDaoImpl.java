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

import com.rumbo.favs.data.dao.IInfantPricesDao;
import com.rumbo.favs.data.entities.InfantPrice;
import com.rumbo.favs.data.entities.InfantPriceGroup;
import com.rumbo.favs.data.utilities.ManageProperties;

/**
 * Infant Price DAO
 * 
 */
public class InfantPriceDaoImpl implements IInfantPricesDao{
	
	private Node nodeInfantPrice = null;
	
	private final String INFANTPRICESFILE = "infantPricesFile";
	
	private final String CSVEXTENSION = ".csv";
	
	public InfantPriceDaoImpl(){
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
			String daysToDepartureDateFile = manageProperties.getFilesProperty(INFANTPRICESFILE);			
			if (daysToDepartureDateFile != null && !daysToDepartureDateFile.isEmpty()){
				csvToXmlInfantPrices(csvFileResourceFolder + daysToDepartureDateFile + CSVEXTENSION);
			}
		}		
	}	
	
	private void csvToXmlInfantPrices(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
			InfantPriceGroup infantPriceGroup = new InfantPriceGroup();

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvInfantPrice = line.split(splitBy);
					InfantPrice infantPrice = new InfantPrice(csvInfantPrice[0],csvInfantPrice[1],csvInfantPrice[2]);
					infantPriceGroup.getInfantPrice().add(infantPrice);
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
				JAXBContext jaxbContext = JAXBContext.newInstance(InfantPriceGroup.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				
				DOMResult res = new DOMResult();
				jaxbMarshaller.marshal(infantPriceGroup, res);

				if (res != null){
					setNodeInfantPrice(res.getNode());
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
	public InfantPrice getInfantPriceByAirline(String airline) {
		
		try {			
			if (getNodeInfantPrice() != null){
			    
			    XPath xPath =  XPathFactory.newInstance().newXPath();
				
			    //Query
				String expression = "/infantPriceGroup/infantPrice[" + IInfantPricesDao.IATACODE + "='"+ airline + "']";		    
				NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getNodeInfantPrice(), XPathConstants.NODESET);
				
				//None result
				if (nodeList.getLength() == 0){
					return null;
				}
							
		        Node nNode = nodeList.item(0);
		        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		           Element element = (Element) nNode;
		           return getInfantPrice(element);
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
	private InfantPrice getInfantPrice(Element element){
		
		InfantPrice infantprice = null;
		
		if (element != null){
			infantprice = new InfantPrice(element.getElementsByTagName(IInfantPricesDao.IATACODE).item(0).getTextContent(),
					element.getElementsByTagName(IInfantPricesDao.NAME).item(0).getTextContent(),
					element.getElementsByTagName(IInfantPricesDao.PRICE).item(0).getTextContent());			
		}		
		
		return infantprice;
	}

	public Node getNodeInfantPrice() {
		return nodeInfantPrice;
	}

	public void setNodeInfantPrice(Node nodeInfantPrice) {
		this.nodeInfantPrice = nodeInfantPrice;
	}	
	
}
