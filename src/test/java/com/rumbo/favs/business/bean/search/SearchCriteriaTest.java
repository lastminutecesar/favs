package com.rumbo.favs.business.bean.search;

import static com.rumbo.favs.business.bean.search.ItineraryBuilder.anItinerary;
import static com.rumbo.favs.business.bean.search.PassengerBuilder.aPassengers;
import static com.rumbo.favs.business.bean.search.SearchCriteriaBuilder.aSearchCriteria;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;

public class SearchCriteriaTest {
	
	private final String DUMMY_AIRPORT = "MAD";
	private final int DUMMY_DAYS = 5;
	private final int DUMMY_NUM_PASSENGERS = 1;

	@Rule
    public ExpectedException thrown = ExpectedException.none();	
	
	@Test		
	public void testA() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_NONE_PASSENGERS);
	    
		aSearchCriteria()
				.withDaysToDeparture(DUMMY_DAYS)
				.withDao(new AirportDaoMockImpl())
				.with(
					anItinerary().withDepartureAirport(DUMMY_AIRPORT).withArrivalAirport(DUMMY_AIRPORT))
				.with(
					aPassengers().withPassenger(PassengerType.ADT, 0)
									.withPassenger(PassengerType.CHD, 0)
									.withPassenger(PassengerType.INF, 0))
				.build();

	}
	
	@Test		
	public void testB() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_NUM_PASSENGERS);
	    
		aSearchCriteria()
				.withDaysToDeparture(DUMMY_DAYS)
				.withDao(new AirportDaoMockImpl())
				.with(
					anItinerary().withDepartureAirport(DUMMY_AIRPORT).withArrivalAirport(DUMMY_AIRPORT))
				.with(
					aPassengers().withPassenger(PassengerType.ADT, -1)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS))
				.build();

	}
	
	@Test		
	public void testC() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_NUM_PASSENGERS);
	    
		aSearchCriteria()
				.withDaysToDeparture(DUMMY_DAYS)
				.withDao(new AirportDaoMockImpl())
				.with(
					anItinerary().withDepartureAirport(DUMMY_AIRPORT).withArrivalAirport(DUMMY_AIRPORT))
				.with(
					aPassengers().withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, -1)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS))
				.build();

	}
	
	@Test		
	public void testD() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_NUM_PASSENGERS);
	    
		aSearchCriteria()
				.withDaysToDeparture(DUMMY_DAYS)
				.withDao(new AirportDaoMockImpl())
				.with(
					anItinerary().withDepartureAirport(DUMMY_AIRPORT).withArrivalAirport(DUMMY_AIRPORT))
				.with(
					aPassengers().withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, -1))
				.build();

	}
	
	@Test		
	public void testF() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_DEPARTURE_CITY);
	    
		aSearchCriteria()
				.withDaysToDeparture(DUMMY_DAYS)
				.withDao(new AirportDaoMockImpl())
				.with(
					anItinerary().withDepartureAirport("PAR").withArrivalAirport(DUMMY_AIRPORT))
				.with(
					aPassengers().withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS))
				.build();

	}
	
	@Test		
	public void testG() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_DESTINATION_CITY);
	    
		aSearchCriteria()
				.withDaysToDeparture(DUMMY_DAYS)
				.withDao(new AirportDaoMockImpl())
				.with(
					anItinerary().withDepartureAirport(DUMMY_AIRPORT).withArrivalAirport("PAR"))
				.with(
					aPassengers().withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS))
				.build();

	}
	
	@Test		
	public void testE() {		
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_DEPARTURE_DATE);
	    
		// GIVEN-WHEN
		SearchCriteria searchCriteria = aSearchCriteria()
				.withDaysToDeparture(-1)
				.withDao(new AirportDaoMockImpl())
				.with(
					anItinerary().withDepartureAirport(DUMMY_AIRPORT).withArrivalAirport(DUMMY_AIRPORT))
				.with(
					aPassengers().withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS))
				.build();				
	}
	
	@Test		
	public void testH() {		
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_DEPARTURE_DATE);
	    
		// GIVEN-WHEN
		SearchCriteria searchCriteria = aSearchCriteria()
				.withDaysToDeparture(366)
				.withDao(new AirportDaoMockImpl())
				.with(
					anItinerary().withDepartureAirport(DUMMY_AIRPORT).withArrivalAirport(DUMMY_AIRPORT))
				.with(
					aPassengers().withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS))
				.build();
		
		// THEN
		assertEquals(searchCriteria.getPassengers().size(), 3);
	}
}
