package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.rumbo.favs.data.entities.Flight;

/**
 * Flight DAO 
 * Manage flights
 * 
 * This implimentation works with csv files
 * and turn the information into dom object
 * to let launch queries
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class FlightDaoImpl implements IFlightDao {
	
	private final String FLIGHTS_FILE = "src/main/resources/files/flights.csv";
	
	private List<Flight> flights;
	
	public FlightDaoImpl(){
		flights = new ArrayList<>();
		csvToObject(FLIGHTS_FILE);
	}

	/**
	 * From csvFile turn the information into dom object
	 * 
	 * @param csvFile
	 */
	private void csvToObject(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvFlight = line.split(splitBy);
					Flight flight = new Flight(csvFlight[0],csvFlight[1],csvFlight[2],Float.parseFloat(csvFlight[3]));
					flights.add(flight);
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
		}
	}

	/**
	 * Get FlightGroup by origin and destination
	 * 
	 * @param String origin
	 * @param String destination
	 * @return FlightGroup
	 */
	public List<Flight> getFlightsByItinerary(String origin, String destination) {
		
		List<Flight> flightsReturn = new ArrayList<>();
		
		if (origin != null && !origin.isEmpty()
				&& destination != null && !destination.isEmpty()){
			
			Flight flightSearch = new Flight(origin, destination);
			
			for(Flight flight : flights){
				if (flight.equals(flightSearch)){
					flightsReturn.add(flight);
				}
			}			
		}
		return flightsReturn;
	}
		
}
