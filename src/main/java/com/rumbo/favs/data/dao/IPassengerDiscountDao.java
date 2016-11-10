package com.rumbo.favs.data.dao;

import com.rumbo.favs.business.enums.PassengerType;
import com.rumbo.favs.data.entities.PassengerDiscount;

/**
 * DiscountByPassengerType DAO interfaz
 * Manage discount by passenger type
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IPassengerDiscountDao {
	
	/**
	 * Get discount passenger type by a passenger type
	 * 
	 * @param PassengerType passengerType
	 * @return DiscountByPassengerType
	 */
	public PassengerDiscount getDiscountByPaxType(PassengerType passengerType);
	
}
