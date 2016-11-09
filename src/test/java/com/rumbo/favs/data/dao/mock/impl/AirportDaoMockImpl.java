package com.rumbo.favs.data.dao.mock.impl;

import java.util.Arrays;

import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.entities.Airport;

public class AirportDaoMockImpl implements IAirportDao {
	
	private final String DUMMY_CITY = "BCN";

	@Override
	public Airport getAirportByIataCode(String iataCode) {
		
		String names[] = {"BCN","MAD","LHR","CDH","FRA","IST","AMS","FCO","CPH"};
		
		if (Arrays.asList(names).contains(iataCode)){
			return new Airport(iataCode, DUMMY_CITY);
		}
		
		return null;
	}

}
