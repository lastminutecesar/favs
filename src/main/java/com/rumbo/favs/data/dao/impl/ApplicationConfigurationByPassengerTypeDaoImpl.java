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

import com.rumbo.favs.business.bean.ApplicationConfigurationType;
import com.rumbo.favs.data.dao.IApplicationConfigurationByPassengerTypeDao;
import com.rumbo.favs.data.entities.ApplicationConfigurationByPassengerType;
import com.rumbo.favs.data.entities.ApplicationConfigurationByPassengerTypeGroup;
import com.rumbo.favs.data.utilities.ManageProperties;

/**
 * Infant Price DAO
 * 
 */
public class ApplicationConfigurationByPassengerTypeDaoImpl implements IApplicationConfigurationByPassengerTypeDao{	
	
	private Node nodeApplicationConfigurationByPassengerType = null;
	
	private final String APPLICATIONCONFIGURATIONFILE = "applicationConfigurationFile";
	
	private final String CSVEXTENSION = ".csv";
	
	public ApplicationConfigurationByPassengerTypeDaoImpl(){
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
			String daysToDepartureDateFile = manageProperties.getFilesProperty(APPLICATIONCONFIGURATIONFILE);			
			if (daysToDepartureDateFile != null && !daysToDepartureDateFile.isEmpty()){
				csvToXmlApplicationConfigurationByPassengerType(csvFileResourceFolder + daysToDepartureDateFile + CSVEXTENSION);
			}
		}		
	}	
	
	private void csvToXmlApplicationConfigurationByPassengerType(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
			ApplicationConfigurationByPassengerTypeGroup applicationConfigurationByPassengerTypeGroup = new ApplicationConfigurationByPassengerTypeGroup();

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvApplicationConfigurationByPassengerType = line.split(splitBy);
					ApplicationConfigurationByPassengerType applicationConfigurationByPassengerType = 
							new ApplicationConfigurationByPassengerType(csvApplicationConfigurationByPassengerType[0],csvApplicationConfigurationByPassengerType[1],csvApplicationConfigurationByPassengerType[2],
									csvApplicationConfigurationByPassengerType[3],csvApplicationConfigurationByPassengerType[4],csvApplicationConfigurationByPassengerType[5]);
					applicationConfigurationByPassengerTypeGroup.getApplicationConfigurationByPassengerTypeGroup().add(applicationConfigurationByPassengerType);
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
				JAXBContext jaxbContext = JAXBContext.newInstance(ApplicationConfigurationByPassengerTypeGroup.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				
				DOMResult res = new DOMResult();
				jaxbMarshaller.marshal(applicationConfigurationByPassengerTypeGroup, res);

				if (res != null){
					setNodeApplicationConfigurationByPassengerType(res.getNode());
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
	public ApplicationConfigurationByPassengerType getApplicationConfigurationByName(ApplicationConfigurationType property) {
		
		try {			
			if (getNodeApplicationConfigurationByPassengerType() != null){
			    
			    XPath xPath =  XPathFactory.newInstance().newXPath();
				
			    //Query
				String expression = "/applicationConfigurationByPassengerTypeGroup/applicationConfigurationByPassengerType[" + IApplicationConfigurationByPassengerTypeDao.NAME + "='"+ property + "']";		    
				NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(getNodeApplicationConfigurationByPassengerType(), XPathConstants.NODESET);
				
				//None result
				if (nodeList.getLength() == 0){
					return null;
				}
							
	            Node nNode = nodeList.item(0);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element element = (Element) nNode;
	               return getApplicationConfiguration(element);
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
	 * Get application configuration from xml element
	 * 
	 * @return InfantPrice
	 */
	private ApplicationConfigurationByPassengerType getApplicationConfiguration(Element element){
		
		ApplicationConfigurationByPassengerType applicationConfigurationByPassengerType = null;
		
		if (element != null){
			applicationConfigurationByPassengerType = new ApplicationConfigurationByPassengerType(
					element.getElementsByTagName(IApplicationConfigurationByPassengerTypeDao.NAME).item(0).getTextContent(),
					element.getElementsByTagName(IApplicationConfigurationByPassengerTypeDao.DESCRIPTION).item(0).getTextContent(),
					element.getElementsByTagName(IApplicationConfigurationByPassengerTypeDao.STATUS).item(0).getTextContent(),
					element.getElementsByTagName(IApplicationConfigurationByPassengerTypeDao.ADT).item(0).getTextContent(),
					element.getElementsByTagName(IApplicationConfigurationByPassengerTypeDao.CHD).item(0).getTextContent(),
					element.getElementsByTagName(IApplicationConfigurationByPassengerTypeDao.INF).item(0).getTextContent());			
		}		
		
		return applicationConfigurationByPassengerType;
	}

	public Node getNodeApplicationConfigurationByPassengerType() {
		return nodeApplicationConfigurationByPassengerType;
	}

	public void setNodeApplicationConfigurationByPassengerType(Node nodeApplicationConfigurationByPassengerType) {
		this.nodeApplicationConfigurationByPassengerType = nodeApplicationConfigurationByPassengerType;
	}	
	
}
