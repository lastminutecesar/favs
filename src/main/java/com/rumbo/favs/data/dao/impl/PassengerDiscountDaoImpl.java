package com.rumbo.favs.data.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.rumbo.favs.business.enums.PassengerType;
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
public class PassengerDiscountDaoImpl extends GenericDao implements IPassengerDiscountDao{
	
	private final String PASSENGERDISCOUNT_FILE = "passengerDiscounts.csv";	
	private Map<PassengerType,PassengerDiscount> passengerDiscounts = new HashMap<>();
	
	public PassengerDiscountDaoImpl(){
		csvToObject(PASSENGERDISCOUNT_FILE);
	}	
	
	protected void processLine(String[] line) {
		
		if (line.length > 1){
			PassengerDiscount passengerDiscount = new PassengerDiscount(PassengerType.valueOf(line[0]), Float.parseFloat(line[1]));
			passengerDiscounts.put(PassengerType.valueOf(line[0]), passengerDiscount);	
		}	
	}
	
	/**
	 * Get airport by city iata code
	 * 
	 * @param String iataCode
	 * @return InfantPrice
	 */
	public PassengerDiscount getDiscountByPaxType(PassengerType passengerType) {
					
		if (passengerType != null){		    
			return passengerDiscounts.get(passengerType);
		} 		
		return null;
	}
	
}
