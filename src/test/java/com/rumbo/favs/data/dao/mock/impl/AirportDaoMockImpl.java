package com.rumbo.favs.data.dao.mock.impl;

import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.entities.Airport;

public class AirportDaoMockImpl implements IAirportDao {
	
	private final String DUMMY_CITY = "BCN";

	@Override
	public Airport getAirportByIataCode(String iataCode) {
		
		if (iataCode.equals("MAD") ||  iataCode.equals("BCN")){
			return new Airport(iataCode, DUMMY_CITY);
		}
		return null;
	}

}
