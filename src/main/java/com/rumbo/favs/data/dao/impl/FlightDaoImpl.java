package com.rumbo.favs.data.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.rumbo.favs.business.bean.search.Itinerary;
import com.rumbo.favs.data.dao.IFlightDao;
import com.rumbo.favs.data.entities.Flight;

/**
 * Flight DAO 
 * Manage flights
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class FlightDaoImpl extends GenericDao implements IFlightDao {
	
	private final String FLIGHTS_FILE = "src/main/resources/files/flights.csv";
	
	private List<Flight> flights;
	
	public FlightDaoImpl(){
		flights = new ArrayList<>();
		csvToObject(FLIGHTS_FILE);
	}
	
	protected void processLine(String[] line) {
		
		if (line.length > 3){
			Flight flight = new Flight(new Itinerary(line[0],line[1]),line[2],Float.parseFloat(line[3]));
			flights.add(flight);	
		}			
	}	

	/**
	 * Get FlightGroup by origin and destination
	 * 
	 * @param String origin
	 * @param String destination
	 * @return FlightGroup
	 */
	public List<Flight> getFlightsByItinerary(Itinerary itinerary) {
		
		List<Flight> flightsReturn = new ArrayList<>();
		
		if (itinerary != null){			
			Flight flightSearch = new Flight(itinerary);
			
			for(Flight flight : flights){
				if (flight.equals(flightSearch)){
					flightsReturn.add(flight);
				}
			}			
		}
		return flightsReturn;
	}

}
