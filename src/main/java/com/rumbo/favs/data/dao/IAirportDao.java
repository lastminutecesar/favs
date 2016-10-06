package com.rumbo.favs.data.dao;

import com.rumbo.favs.data.entities.Airport;

/**
 * Airport DAO interfaz
 * Manage departure and arrival cities
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IAirportDao {

	public static final String IATACODE = "iataCode";
	public static final String CITY = "city";
	
	/**
	 * Get airport by city iata code
	 * 
	 * @param String iataCode
	 * @return InfantPrice
	 */
	public Airport getAirportByIataCode(String iataCode);
	
}
