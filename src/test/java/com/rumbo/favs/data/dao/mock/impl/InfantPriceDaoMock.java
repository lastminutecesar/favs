package com.rumbo.favs.data.dao.mock.impl;

import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.entities.InfantPrice;

/**
 * Infant prices DAO
 * Manage infant prices
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class InfantPriceDaoMock implements IInfantPriceDao{
	
	private final String DUMMY_AIRLINE = "IB";
	private final String DUMMY_NAME = "Iberia";
	
	/**
	 * Get infant price by airline
	 * 
	 * @param String airline
	 * @return InfantPrice
	 */
	public InfantPrice getInfantPriceByAirline(String airline) {
		
		switch(airline){
		case "IB":
			return new InfantPrice(DUMMY_AIRLINE,DUMMY_NAME,10);
		case "BA":
			return new InfantPrice(DUMMY_AIRLINE,DUMMY_NAME,15);
		case "LH":
			return new InfantPrice(DUMMY_AIRLINE,DUMMY_NAME,7);
		case "FR":
			return new InfantPrice(DUMMY_AIRLINE,DUMMY_NAME,20);
		case "VY":
			return new InfantPrice(DUMMY_AIRLINE,DUMMY_NAME,10);
		case "TK":
			return new InfantPrice(DUMMY_AIRLINE,DUMMY_NAME,5);
		case "U2":
			return new InfantPrice(DUMMY_AIRLINE,DUMMY_NAME,19.9f);
		default:
			return null;
		}
	}
	
}
