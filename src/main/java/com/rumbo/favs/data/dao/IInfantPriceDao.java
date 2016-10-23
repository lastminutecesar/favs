package com.rumbo.favs.data.dao;

import com.rumbo.favs.data.entities.InfantPrice;

/**
 * Infant prices DAO interfaz
 * Manage infant prices
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IInfantPriceDao {

	public static final String IATACODE = "iataCode";
	public static final String NAME = "name";
	public static final String PRICE = "price";
	
	/**
	 * Get infant price by airline
	 * 
	 * @param String airline
	 * @return InfantPrice
	 */
	public InfantPrice getInfantPriceByAirline(String airline);
	
}
