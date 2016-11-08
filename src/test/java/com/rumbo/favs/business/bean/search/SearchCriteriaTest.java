package com.rumbo.favs.business.bean.search;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.dao.mock.impl.AirportDaoMockImpl;

public class SearchCriteriaTest {
	
	private static IAirportDao airportDao;
	private final String DUMMY_AIRPORT = "MAD";
	private final int DUMMY_DAYS = 5;
	private final int DUMMY_NUM_PASSENGERS = 1;
	private final Itinerary DUMMY_ITINERARY = Itinerary.builder()
									.withDepartureAirport(DUMMY_AIRPORT)
									.withArrivalAirport(DUMMY_AIRPORT)
									.build();

	@Rule
    public ExpectedException thrown = ExpectedException.none();	
	
	@BeforeClass
	public static void initialize()
	{
		// GIVEN
		airportDao = new AirportDaoMockImpl();
	}
	
	@Test		
	public void testA() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_NONE_PASSENGERS);
	    
	    // GIVEN
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(DUMMY_DAYS)
				.withItinerary(DUMMY_ITINERARY)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 0)
									.withPassenger(PassengerType.CHD, 0)
									.withPassenger(PassengerType.INF, 0)
									.build())
				.withDao(airportDao)
				
				// WHEN
				.build();
	}
	
	@Test		
	public void testB() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_NUM_PASSENGERS);
	    
	    // GIVEN
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(DUMMY_DAYS)
				.withItinerary(DUMMY_ITINERARY)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, -1)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS)
									.build())
				.withDao(airportDao)
				
				// WHEN
				.build();
	}
	
	@Test		
	public void testC() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_NUM_PASSENGERS);
	    
	    // GIVEN
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(DUMMY_DAYS)
				.withItinerary(DUMMY_ITINERARY)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, -1)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS)
									.build())
				.withDao(airportDao)
				
				// WHEN
				.build();
	}
	
	@Test		
	public void testD() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_NUM_PASSENGERS);
	    
	    // GIVEN
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(DUMMY_DAYS)
				.withItinerary(DUMMY_ITINERARY)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, -1)
									.build())
				.withDao(airportDao)
				
				// WHEN
				.build();
	}
	
	@Test		
	public void testF() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_DEPARTURE_CITY);
	    
	    // GIVEN
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(DUMMY_DAYS)
				.withItinerary(Itinerary.builder()
						.withDepartureAirport("PAR")
						.withArrivalAirport(DUMMY_AIRPORT)
						.build())
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS)
									.build())
				.withDao(airportDao)
				
				// WHEN
				.build();
	}
	
	@Test		
	public void testG() {
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_DESTINATION_CITY);
	    
	    // GIVEN
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(DUMMY_DAYS)
				.withItinerary(Itinerary.builder()
								.withDepartureAirport(DUMMY_AIRPORT)
								.withArrivalAirport("PAR")
								.build())
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS)
									.build())
				.withDao(airportDao)
				
				// WHEN
				.build();
	}
	
	@Test		
	public void testE() {		
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_DEPARTURE_DATE);
	    
		// GIVEN
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(-1)
				.withItinerary(DUMMY_ITINERARY)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS)
									.build())
				.withDao(airportDao)
				
				// WHEN
				.build();
	}
	
	@Test		
	public void testH() {		
		thrown.expect(SearchCriteriaException.class);
	    thrown.expectMessage(SearchCriteriaException.ERROR_DEPARTURE_DATE);
	    
		// GIVEN
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(366)
				.withItinerary(DUMMY_ITINERARY)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.CHD, DUMMY_NUM_PASSENGERS)
									.withPassenger(PassengerType.INF, DUMMY_NUM_PASSENGERS)
									.build())
				.withDao(airportDao)
				
				// WHEN
				.build();
	}
}
