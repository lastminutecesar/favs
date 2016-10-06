package com.rumbo.favs.data.dao;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.data.entities.DiscountByPassengerType;

/**
 * DiscountByPassengerType DAO interfaz
 * Manage discount by passenger type
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IDiscountByPassengerTypeDao {

	public static final String PASSENGERTYPE = "passengerType";
	public static final String DISCOUNTPERCENT = "discountPercent";
	
	/**
	 * Get discount passenger type by a passenger type
	 * 
	 * @param PassengerType passengerType
	 * @return DiscountByPassengerType
	 */
	public DiscountByPassengerType getDiscountPercent(PassengerType passengerType);
	
}
