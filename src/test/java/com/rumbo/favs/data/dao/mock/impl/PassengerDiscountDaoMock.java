package com.rumbo.favs.data.dao.mock.impl;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.data.dao.IPassengerDiscountDao;
import com.rumbo.favs.data.entities.PassengerDiscount;

/**
 * DiscountByPassengerType DAO
 * Manage discount by passenger type
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class PassengerDiscountDaoMock implements IPassengerDiscountDao{
	
	private float adtDiscount = 0;
	private float chdDiscount = 33;
	private float infDiscount = 0;	
		
	/**
	 * Get airport by city iata code
	 * 
	 * @param String iataCode
	 * @return InfantPrice
	 */
	public PassengerDiscount getDiscount(PassengerType passengerType) {
					
		switch(passengerType){
			case ADT:
				return new PassengerDiscount(PassengerType.ADT, adtDiscount);
			case CHD:
				return new PassengerDiscount(PassengerType.CHD, chdDiscount);
			case INF:
				return new PassengerDiscount(PassengerType.INF, infDiscount);
			default:
				return null;
		
		}
	}
	
}
