package com.rumbo.favs.data.dao;

import com.rumbo.favs.data.entities.DepartureDate;

/**
 * DaysToDepartureDate DAO interfaz
 * Manage days to departure date discount
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IDepartureDateDao {

	public static final String MIN = "min";
	public static final String MAX = "max";
	public static final String DISCOUNTPERCENT = "discountPercent";
	
	/**
	 * Get days to departure date 
	 * by a given day
	 * 
	 * @param int days
	 * @return DaysToDepartureDate
	 */
	public DepartureDate getDiscount(int days);
	
}
