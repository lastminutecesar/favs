package com.rumbo.favs.data.dao;

import java.util.List;

import com.rumbo.favs.business.bean.search.Itinerary;
import com.rumbo.favs.data.entities.Flight;

/**
 * Flight DAO interfaz
 * Manage flights
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IFlightDao {
	
	/**
	 * Get FlightGroup by origin and destination
	 * 
	 * @param String origin
	 * @param String destination
	 * @return FlightGroup
	 */
	public List<Flight> getFlightsByItinerary(Itinerary itinerary);
	
}
