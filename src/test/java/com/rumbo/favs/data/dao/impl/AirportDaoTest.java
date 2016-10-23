package com.rumbo.favs.data.dao.impl;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.entities.Airport;

public class AirportDaoTest {

	@Test		
	public void testA() {
		
		IAirportDao airportDao = new AirportDaoImpl();
		
		Airport airport = airportDao.getAirportByIataCode("MAD");
		
		assertThat(airport.getCity(),is("Madrid"));
	}
	
	@Test		
	public void testB() {
		
		IAirportDao airportDao = new AirportDaoImpl();
		
		Airport airport = airportDao.getAirportByIataCode("BCN");
		
		assertThat(airport.getCity(),is("Barcelona"));
	}
	
}
