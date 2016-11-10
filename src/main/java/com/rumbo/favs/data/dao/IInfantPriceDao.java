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
	
	/**
	 * Get infant price by airline
	 * 
	 * @param String airline
	 * @return InfantPrice
	 */
	public InfantPrice getDiscountByAirline(String airline);
	
}
