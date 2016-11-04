package com.rumbo.favs.business;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.business.bean.result.AvailabilityResult;
import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.bean.search.Itinerary;
import com.rumbo.favs.business.bean.search.SearchCriteria;
import com.rumbo.favs.business.enums.result.ResultType;
import com.rumbo.favs.business.services.core.ISearchEngine;
import com.rumbo.favs.business.services.core.impl.SearchEngineImpl;
import com.rumbo.favs.business.services.fare.IFarePrice;
import com.rumbo.favs.business.services.fare.impl.FarePriceImpl;
import com.rumbo.favs.data.dao.impl.AirportDaoImpl;
import com.rumbo.favs.data.dao.impl.DepartureDateDaoImpl;
import com.rumbo.favs.data.dao.impl.FlightDaoImpl;
import com.rumbo.favs.data.dao.impl.InfantPriceDaoImpl;
import com.rumbo.favs.data.dao.impl.PassengerDiscountDaoImpl;
import com.rumbo.favs.data.utilities.MyMath;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchTest {
	
	private static ISearchEngine searchEngine;
	
	@BeforeClass
	public static void initialize()
	{
		IFarePrice farePrice = new FarePriceImpl(new PassengerDiscountDaoImpl(),
													new DepartureDateDaoImpl(),
													new InfantPriceDaoImpl());
		
		searchEngine = new SearchEngineImpl(new FlightDaoImpl(),farePrice);
	}
		
	@Rule
    public ExpectedException thrown = ExpectedException.none();	
		
	@Test		
	public void testH() {
		System.out.println("TEST 8");
		System.out.println("[1 adult, 1 child, 1 infant, 45 days to departure date, flying CDG -> FRA]");
		
		// GIVEN
		Itinerary itinerary = Itinerary.builder()
				.withDepartureAirport("CDG")
				.withArrivalAirport("FRA")
				.build();
		
		SearchCriteria searchCriteria = SearchCriteria.builder()
				.withDaysToDeparture(45)
				.withItinerary(itinerary)
				.withPassengers(SearchCriteria.passengerBuilder()
									.withPassenger(PassengerType.ADT, 1)
									.withPassenger(PassengerType.CHD, 1)
									.withPassenger(PassengerType.INF, 1)
									.build())
				.withDao(new AirportDaoImpl())
				.build();
				
		// WHEN
		AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);
		
		// THEN
		assertNotNull(availabilityResult);
		assertThat(availabilityResult.getResult(),is(ResultType.KO));
		printResult(availabilityResult);
		System.out.println();
	}
	
	@Test		
	public void testI() {
		try{
			System.out.println("TEST 9");
			System.out.println("[1 adult, 31 days to departure date, flying AMS -> FRA]");
			
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
			
			// WHEN
			AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);		
			
			// THEN
			assertNotNull(availabilityResult);
			assertThat(availabilityResult.getResult(),is(ResultType.OK));
			assertThat(getFlights(availabilityResult), arrayContainingInAnyOrder("TK2372","TK2659","LH5909"));
			assertThat(getTotalAmountByFlight(availabilityResult), arrayContainingInAnyOrder("157,60","198,40","90,40"));
			printResult(availabilityResult);	
		}catch (SearchCriteriaException e){
			e.printStackTrace();
		}
	}

	@Test		
	public void testJ() {
		try{
			System.out.println("TEST 10");
			System.out.println("[2 adult, 1 child, 1 infant, 15 days to departure date, flying LHR -> IST]");
			
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

			// WHEN
			AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);		
			
			// THEN
			assertNotNull(availabilityResult);
			assertThat(availabilityResult.getResult(),is(ResultType.OK));
			assertThat(getFlights(availabilityResult), arrayContainingInAnyOrder("TK8891","LH1085"));
			assertThat(getTotalAmountByFlight(availabilityResult), arrayContainingInAnyOrder("806","481,19"));
			printResult(availabilityResult);	
		}catch (SearchCriteriaException e){
			e.printStackTrace();
		}
	}
	
	@Test		
	public void testK() {
		try{
			System.out.println("TEST 11");
			System.out.println("[2 adult, 1 child, 2 infant, 2 days to departure date, flying BCN -> MAD]");
			
			// GIVEN
			Itinerary itinerary = Itinerary.builder()
					.withDepartureAirport("BCN")
					.withArrivalAirport("MAD")
					.build();
			
			SearchCriteria searchCriteria = SearchCriteria.builder()
					.withDaysToDeparture(2)
					.withItinerary(itinerary)
					.withPassengers(SearchCriteria.passengerBuilder()
										.withPassenger(PassengerType.ADT, 1)
										.withPassenger(PassengerType.CHD, 2)
										.withPassenger(PassengerType.INF, 0)
										.build())
					.withDao(new AirportDaoImpl())
					.build();
			
			// WHEN
			AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);	
			
			// THEN
			assertNotNull(availabilityResult);
			assertThat(availabilityResult.getResult(),is(ResultType.OK));
			assertThat(getFlights(availabilityResult), arrayContainingInAnyOrder("IB2171","LH5496"));
			assertThat(getTotalAmountByFlight(availabilityResult), arrayContainingInAnyOrder("909,09","1.028,43"));
			printResult(availabilityResult);		
		}catch (SearchCriteriaException e){
			e.printStackTrace();
		}
	}
	
	@Test		
	public void testL() {
		try{
			System.out.println("TEST 12");
			System.out.println("[1 adult, 0 days to departure date, flying CPH -> FCO]");
			
			// GIVEN
			Itinerary itinerary = Itinerary.builder()
					.withDepartureAirport("CPH")
					.withArrivalAirport("FCO")
					.build();
			
			SearchCriteria searchCriteria = SearchCriteria.builder()
					.withDaysToDeparture(0)
					.withItinerary(itinerary)
					.withPassengers(SearchCriteria.passengerBuilder()
										.withPassenger(PassengerType.ADT, 1)
										.withPassenger(PassengerType.CHD, 0)
										.withPassenger(PassengerType.INF, 0)
										.build())
					.withDao(new AirportDaoImpl())
					.build();
			
			// WHEN
			AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);	
			
			// THEN
			assertNotNull(availabilityResult);
			assertThat(availabilityResult.getResult(),is(ResultType.OK));
			assertThat(getFlights(availabilityResult), arrayContainingInAnyOrder("TK4667","U24631"));
			assertThat(getTotalAmountByFlight(availabilityResult), arrayContainingInAnyOrder("205,50","402"));
			printResult(availabilityResult);	
		}catch (SearchCriteriaException e){
			e.printStackTrace();
		}
	}	
	
	@Test		
	public void testM() {
		try{
			System.out.println("TEST 13");
			System.out.println("[3 adult, 2 child, 2 infant, 17 days to departure date, flying CDG -> CPH]");
			
			// GIVEN
			Itinerary itinerary = Itinerary.builder()
					.withDepartureAirport("CDG")
					.withArrivalAirport("CPH")
					.build();
			
			SearchCriteria searchCriteria = SearchCriteria.builder()
					.withDaysToDeparture(17)
					.withItinerary(itinerary)
					.withPassengers(SearchCriteria.passengerBuilder()
										.withPassenger(PassengerType.ADT, 3)
										.withPassenger(PassengerType.CHD, 2)
										.withPassenger(PassengerType.INF, 2)
										.build())
					.withDao(new AirportDaoImpl())
					.build();
			
			// WHEN
			AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);	
			
			// THEN
			assertNotNull(availabilityResult);
			assertThat(availabilityResult.getResult(),is(ResultType.OK));
			assertThat(getFlights(availabilityResult), arrayContainingInAnyOrder("LH5778","IB5257","TK2044"));
			assertThat(getTotalAmountByFlight(availabilityResult), arrayContainingInAnyOrder("1.155,42","1.317,66","817,24"));
			printResult(availabilityResult);	
		}catch (SearchCriteriaException e){
			e.printStackTrace();
		}
	}
	
	@Test		
	public void testN() {
		try{
			System.out.println("TEST 14");
			System.out.println("[1 adult, 1 child, 1 infant, 60 days to departure date, flying FRA -> LHR]");
			
			// GIVEN
			Itinerary itinerary = Itinerary.builder()
					.withDepartureAirport("FRA")
					.withArrivalAirport("LHR")
					.build();
			
			SearchCriteria searchCriteria = SearchCriteria.builder()
					.withDaysToDeparture(60)
					.withItinerary(itinerary)
					.withPassengers(SearchCriteria.passengerBuilder()
										.withPassenger(PassengerType.ADT, 1)
										.withPassenger(PassengerType.CHD, 1)
										.withPassenger(PassengerType.INF, 1)
										.build())
					.withDao(new AirportDaoImpl())
					.build();
			
			// WHEN
			AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);	
			
			// THEN
			assertNotNull(availabilityResult);
			assertThat(availabilityResult.getResult(),is(ResultType.OK));
			assertThat(getFlights(availabilityResult), arrayContainingInAnyOrder("BA8162","IB9443","TK3167"));
			assertThat(getTotalAmountByFlight(availabilityResult), arrayContainingInAnyOrder("288,88","349,34","162,65"));
			printResult(availabilityResult);	
		}catch (SearchCriteriaException e){
			e.printStackTrace();
		}
	}	
	
	@Test		
	public void testO() {
		try{
			System.out.println("TEST 15");
			System.out.println("[2 adult, 2 child, 2 infant, 300 days to departure date, flying FCO -> AMS]");
			
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
			
			// WHEN
			AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);	
			
			// THEN
			assertNotNull(availabilityResult);
			assertThat(availabilityResult.getResult(),is(ResultType.OK));
			assertThat(getFlights(availabilityResult), arrayContainingInAnyOrder("VY4840"));
			assertThat(getTotalAmountByFlight(availabilityResult), arrayContainingInAnyOrder("714,72"));
			printResult(availabilityResult);	
		}catch (SearchCriteriaException e){
			e.printStackTrace();
		}
	}	
	
	@Test		
	public void testP() {
		try{
			System.out.println("TEST 16");
			System.out.println("[3 adult, 3 child, 3 infant, 20 days to departure date, flying MAD -> FRA]");
			
			// GIVEN
			Itinerary itinerary = Itinerary.builder()
					.withDepartureAirport("MAD")
					.withArrivalAirport("FRA")
					.build();
			
			SearchCriteria searchCriteria = SearchCriteria.builder()
					.withDaysToDeparture(20)
					.withItinerary(itinerary)
					.withPassengers(SearchCriteria.passengerBuilder()
										.withPassenger(PassengerType.ADT, 3)
										.withPassenger(PassengerType.CHD, 3)
										.withPassenger(PassengerType.INF, 3)
										.build())
					.withDao(new AirportDaoImpl())
					.build();
			
			// WHEN
			AvailabilityResult availabilityResult = searchEngine.search(searchCriteria);	
			
			// THEN
			assertNotNull(availabilityResult);
			assertThat(availabilityResult.getResult(),is(ResultType.OK));
			assertThat(getFlights(availabilityResult), arrayContainingInAnyOrder("BA8982"));
			assertThat(getTotalAmountByFlight(availabilityResult), arrayContainingInAnyOrder("901,71"));
			printResult(availabilityResult);	
		}catch (SearchCriteriaException e){
			e.printStackTrace();
		}
	}
	
	private void printResult(AvailabilityResult availabilityResult){		
		if (availabilityResult != null){
			System.out.println(availabilityResult.toString());
		}		
	}
	
	private String[] getFlights(AvailabilityResult availabilityResult){
				
		String[] flights = new String[0];
		int count = 0;
		
		if (availabilityResult != null && availabilityResult.getFlightResultList() != null){
			flights = new String[availabilityResult.getFlightResultList().size()];
			for(FlightResult flightResult : availabilityResult.getFlightResultList()){
				flights[count] = flightResult.getFlight();
				count++;
			}
		}
		
		return flights;
	}
	
	private String[] getTotalAmountByFlight(AvailabilityResult availabilityResult){
		
		String[] totalAmount = new String[0];
		int count = 0;
		
		if (availabilityResult != null && availabilityResult.getFlightResultList() != null){
			totalAmount = new String[availabilityResult.getFlightResultList().size()];
			for(FlightResult flightResult : availabilityResult.getFlightResultList()){
				totalAmount[count] = MyMath.getRoundedFloat(flightResult.getTotalAmount());
				count++;
			}
		}
		
		return totalAmount;
	}
	
}
