package com.rumbo.favs.data.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.entities.Airport;

/**
 * Airport DAO Impl
 * Manage departure and arrival cities
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class AirportDaoImpl extends GenericDao implements IAirportDao{
	
	private final String AIRPORTS_FILE = "src/main/resources/files/airports.csv";
	
	private Map<String,String> airports = new HashMap<>();
	
	public AirportDaoImpl(){
		csvToObject(AIRPORTS_FILE);
	}
	
	protected void processLine(String[] line) {
		
		if (line.length > 1){
			airports.put(line[0], line[1]);				
		}	
	}	
	
	/**
	 * Get airport by city iata code
	 * 
	 * @param String iataCode
	 * @return InfantPrice
	 */
	public Airport getAirportByIataCode(String iataCode) {
					
		if (iataCode != null && !iataCode.isEmpty()){		    
		    String city = airports.get(iataCode);
		    if (city != null){
		    	return new Airport(iataCode,city);
		    }
		} 		
		return null;
	}
		
}
