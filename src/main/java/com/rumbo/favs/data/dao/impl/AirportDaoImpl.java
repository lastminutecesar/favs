package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.entities.Airport;

/**
 * Airport DAO Impl
 * Manage departure and arrival cities
 * 
 * This implimentation works with csv files
 * and turn the information into dom object
 * to let launch queries
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class AirportDaoImpl implements IAirportDao{
	
	private final String AIRPORTS_FILE = "src/main/resources/files/airports.csv";
	
	private Map<String,String> airports = new HashMap<>();
	
	public AirportDaoImpl(){
		csvToObject(AIRPORTS_FILE);
	}
	
	private void csvToObject(String csvFile){
		
		if (csvFile != null && !csvFile.isEmpty()){			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
			
			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvAirport = line.split(splitBy);
					airports.put(csvAirport[0], csvAirport[1]);
				}
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
