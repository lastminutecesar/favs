package com.rumbo.favs.data.dao;

import com.rumbo.favs.data.entities.FlightGroup;

/**
 * Flight DAO interfaz
 * Manage flights
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IFlightDao {
	
	public static final String ORIGIN = "origin";
	public static final String DESTINATION = "destination";
	public static final String AIRLINE = "airline";
	public static final String BASEPRICE = "basePrice";
	
	/**
	 * Get FlightGroup by origin and destination
	 * 
	 * @param String origin
	 * @param String destination
	 * @return FlightGroup
	 */
	public FlightGroup getFlightsByItinerary(String origin, String destination);
	
}
