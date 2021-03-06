package com.rumbo.favs.data.dao.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rumbo.favs.business.bean.search.Itinerary;
import com.rumbo.favs.business.services.core.impl.SearchEngineImpl;
import com.rumbo.favs.data.dao.IFlightDao;
import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.entities.Flight;
import com.rumbo.favs.data.entities.InfantPrice;

public class FlightDaoTest {
	
	private static IFlightDao flightDao;
	
	@BeforeClass
	public static void initialize()
	{
		// GIVEN
		flightDao = new FlightDaoImpl();
	}

	@Test		
	public void testA() {
		
		// WHEN
		List<Flight> flights = flightDao.getFlightsByItinerary(Itinerary.builder()
																.withDepartureAirport("AMS")
																.withArrivalAirport("FRA")
																.build());
		
		// THEN
		assertThat(flights.size(),is(3));
	}
	
	@Test		
	public void testB() {
		
		// WHEN
		List<Flight> flights = flightDao.getFlightsByItinerary(Itinerary.builder()
																.withDepartureAirport("LHR")
																.withArrivalAirport("IST")
																.build());
		
		// THEN
		assertThat(flights.size(),is(2));
	}
	
	@Test		
	public void testC() {
		
		// WHEN
		List<Flight> flights = flightDao.getFlightsByItinerary(Itinerary.builder()
																.withDepartureAirport("AMS")
																.withArrivalAirport("MAD")
																.build());
			
		// THEN
		assertThat(flights.size(),is(0));
	}
	
}
