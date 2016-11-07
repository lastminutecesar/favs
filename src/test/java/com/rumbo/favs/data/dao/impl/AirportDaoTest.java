package com.rumbo.favs.data.dao.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.entities.Airport;

public class AirportDaoTest {
	
	private static IAirportDao airportDao;
	
	@BeforeClass
	public static void initialize()
	{
		airportDao = new AirportDaoImpl();
	}

	@Test		
	public void testA() {
		
		// WHEN
		Airport airport = airportDao.getAirportByIataCode("MAD");
		
		// THEN
		assertThat(airport.getCity(),is("Madrid"));
	}
	
	@Test		
	public void testB() {
		
		// WHEN
		Airport airport = airportDao.getAirportByIataCode("BCN");
		
		// THEN
		assertThat(airport.getCity(),is("Barcelona"));
	}
	
	@Test		
	public void testC() {
		
		// WHEN
		Airport airport = airportDao.getAirportByIataCode("PAR");
		
		// THEN
		assertNull(airport);
	}
	
}
