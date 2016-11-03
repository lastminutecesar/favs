package com.rumbo.favs.business.services.fare.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.bean.search.Itinerary;
import com.rumbo.favs.business.bean.search.SearchCriteria;
import com.rumbo.favs.business.services.fare.IFarePrice;
import com.rumbo.favs.data.dao.impl.AirportDaoImpl;
import com.rumbo.favs.data.dao.impl.DepartureDateDaoImpl;
import com.rumbo.favs.data.dao.impl.InfantPriceDaoImpl;
import com.rumbo.favs.data.dao.impl.PassengerDiscountDaoImpl;
import com.rumbo.favs.data.entities.Flight;

public class FarePriceImplTest {
	
	private final String DUMMY_AIRPORT = "MAD";
	private final int DUMMY_DAYS = 5;
	private final int DUMMY_NUM_PASSENGERS = 1;

	@Rule
    public ExpectedException thrown = ExpectedException.none();	
		
	@Test		
	public void testA() {			    
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
				.withDepartureAirport("FCO")
				.withArrivalAirport("AMS")
				.build();
		
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(300)
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 2)
									.withPassenger(PassengerType.CHD, 2)
									.withPassenger(PassengerType.INF, 2)
									.build())
				.withDao(new AirportDaoImpl())
				.build();
		
		Flight flight = Flight.builder()
				.withItinerary(itinerary)
				.withAirline("VY")
				.withBasePrice(260)
				.build();
		
		IFarePrice farePrice = new FarePriceImpl();
		farePrice.setDaysToDepartureDateDao(new DepartureDateDaoImpl());
		farePrice.setDiscountByPassengerTypeDao(new PassengerDiscountDaoImpl());
		farePrice.setInfantPricesDao(new InfantPriceDaoImpl());
		
		// WHEN
		FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
		
		// THEN
		assertNotNull(flightResult);
		assertThat(flightResult.getTotalAmount(),is(714.72f));
		assertEquals(searchCriteria.getPassengers().size(), 3);		
	}
	
	@Test		
	public void testB() {			    
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
				.withDepartureAirport("MAD")
				.withArrivalAirport("FRA")
				.build();
		
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(300)				
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 3)
									.withPassenger(PassengerType.CHD, 3)
									.withPassenger(PassengerType.INF, 3)
									.build())
				.withDao(new AirportDaoImpl())
				.build();
		
		Flight flight = Flight.builder()
				.withItinerary(itinerary)
				.withAirline("BA")
				.withBasePrice(171)
				.build();
		
		IFarePrice farePrice = new FarePriceImpl();
		farePrice.setDaysToDepartureDateDao(new DepartureDateDaoImpl());
		farePrice.setDiscountByPassengerTypeDao(new PassengerDiscountDaoImpl());
		farePrice.setInfantPricesDao(new InfantPriceDaoImpl());
		
		// WHEN
		FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
		
		// THEN
		assertNotNull(flightResult);
		assertThat(flightResult.getTotalAmount(),is(730.37f));
		assertEquals(searchCriteria.getPassengers().size(), 3);		
	}
	
	@Test		
	public void testC() {			    
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
				.withDepartureAirport("LHR")
				.withArrivalAirport("IST")
				.build();
		
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(15)
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 2)
									.withPassenger(PassengerType.CHD, 1)
									.withPassenger(PassengerType.INF, 1)
									.build())
				.withDao(new AirportDaoImpl())
				.build();
		
		Flight flight = Flight.builder()
				.withItinerary(itinerary)
				.withAirline("TK")
				.withBasePrice(250)
				.build();
		
		IFarePrice farePrice = new FarePriceImpl();
		farePrice.setDaysToDepartureDateDao(new DepartureDateDaoImpl());
		farePrice.setDiscountByPassengerTypeDao(new PassengerDiscountDaoImpl());
		farePrice.setInfantPricesDao(new InfantPriceDaoImpl());
		
		// WHEN
		FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
		
		// THEN
		assertNotNull(flightResult);
		assertThat(flightResult.getTotalAmount(),is(806f));
		assertEquals(searchCriteria.getPassengers().size(), 3);		
	}
	
	@Test		
	public void testD() {			    
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
				.withDepartureAirport("LHR")
				.withArrivalAirport("IST")
				.build();
				
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(15)
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 2)
									.withPassenger(PassengerType.CHD, 1)
									.withPassenger(PassengerType.INF, 1)
									.build())
				.withDao(new AirportDaoImpl())
				.build();
		
		Flight flight = Flight.builder()
				.withItinerary(itinerary)
				.withAirline("LH")
				.withBasePrice(148)
				.build();
		
		IFarePrice farePrice = new FarePriceImpl();
		farePrice.setDaysToDepartureDateDao(new DepartureDateDaoImpl());
		farePrice.setDiscountByPassengerTypeDao(new PassengerDiscountDaoImpl());
		farePrice.setInfantPricesDao(new InfantPriceDaoImpl());
		
		// WHEN
		FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
		
		// THEN
		assertNotNull(flightResult);
		assertThat(flightResult.getTotalAmount(),is(481.19f));
		assertEquals(searchCriteria.getPassengers().size(), 3);		
	}
	
	@Test		
	public void testF() {			    
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
				.withDepartureAirport("LHR")
				.withArrivalAirport("IST")
				.build();
		
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(15)
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 2)
									.withPassenger(PassengerType.CHD, 1)
									.withPassenger(PassengerType.INF, 1)
									.build())
				.withDao(new AirportDaoImpl())
				.build();
		
		Flight flight = Flight.builder()
				.withItinerary(itinerary)
				.withAirline("LH")
				.withBasePrice(148)
				.build();
		
		IFarePrice farePrice = new FarePriceImpl();
		farePrice.setDaysToDepartureDateDao(new DepartureDateDaoImpl());
		farePrice.setDiscountByPassengerTypeDao(new PassengerDiscountDaoImpl());
		farePrice.setInfantPricesDao(new InfantPriceDaoImpl());
		
		// WHEN
		FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
		
		// THEN
		assertNotNull(flightResult);
		assertThat(flightResult.getTotalAmount(),is(481.19f));
		assertEquals(searchCriteria.getPassengers().size(), 3);		
	}
	
	@Test		
	public void testG() {			    
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
				.withDepartureAirport("AMS")
				.withArrivalAirport("FRA")
				.build();
		
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(31)
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 1)
									.withPassenger(PassengerType.CHD, 0)
									.withPassenger(PassengerType.INF, 0)
									.build())

				.withDao(new AirportDaoImpl())
				.build();
		
		Flight flight = Flight.builder()
							.withItinerary(itinerary)
							.withAirline("TK")
							.withBasePrice(197)
							.build();
		
		IFarePrice farePrice = new FarePriceImpl();
		farePrice.setDaysToDepartureDateDao(new DepartureDateDaoImpl());
		farePrice.setDiscountByPassengerTypeDao(new PassengerDiscountDaoImpl());
		farePrice.setInfantPricesDao(new InfantPriceDaoImpl());
		
		// WHEN
		FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
		
		// THEN
		assertNotNull(flightResult);
		assertThat(flightResult.getTotalAmount(),is(157.6f));
		assertEquals(searchCriteria.getPassengers().size(), 3);		
	}
	
	@Test		
	public void testH() {			    
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
								.withDepartureAirport("AMS")
								.withArrivalAirport("FRA")
								.build();
		
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(31)
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 1)
									.withPassenger(PassengerType.CHD, 0)
									.withPassenger(PassengerType.INF, 0)
									.build())

				.withDao(new AirportDaoImpl())
				.build();
		
		Flight flight = Flight.builder()
							.withItinerary(itinerary)
							.withAirline("TK")
							.withBasePrice(248)
							.build();
		
		IFarePrice farePrice = new FarePriceImpl();
		farePrice.setDaysToDepartureDateDao(new DepartureDateDaoImpl());
		farePrice.setDiscountByPassengerTypeDao(new PassengerDiscountDaoImpl());
		farePrice.setInfantPricesDao(new InfantPriceDaoImpl());
		
		// WHEN
		FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
		
		// THEN
		assertNotNull(flightResult);
		assertThat(flightResult.getTotalAmount(),is(198.4f));
		assertEquals(searchCriteria.getPassengers().size(), 3);		
	}
	
	@Test		
	public void testI() {			    
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
								.withDepartureAirport("AMS")
								.withArrivalAirport("FRA")
								.build();
		
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(31)
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 1)
									.withPassenger(PassengerType.CHD, 0)
									.withPassenger(PassengerType.INF, 0)
									.build())

				.withDao(new AirportDaoImpl())
				.build();
		
		Flight flight = Flight.builder()
							.withItinerary(itinerary)
							.withAirline("LH")
							.withBasePrice(113)
							.build();
		
		IFarePrice farePrice = new FarePriceImpl();
		farePrice.setDaysToDepartureDateDao(new DepartureDateDaoImpl());
		farePrice.setDiscountByPassengerTypeDao(new PassengerDiscountDaoImpl());
		farePrice.setInfantPricesDao(new InfantPriceDaoImpl());
		
		// WHEN
		FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
		
		// THEN
		assertNotNull(flightResult);
		assertThat(flightResult.getTotalAmount(),is(90.4f));
		assertEquals(searchCriteria.getPassengers().size(), 3);		
	}
}
