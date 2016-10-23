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

import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.business.bean.result.AvailabilityResult;
import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.enums.result.ResultType;
import com.rumbo.favs.business.services.core.ISearchEngine;
import com.rumbo.favs.business.services.core.impl.SearchEngineImpl;
import com.rumbo.favs.data.utilities.MyMath;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SearchTest {
	
	private static ISearchEngine searchEngine;
	
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
	
	@BeforeClass
	public static void initialize()
	{
		searchEngine = new SearchEngineImpl();
	}
		
	@Rule
    public ExpectedException thrown = ExpectedException.none();	
		
	@Test		
	public void testH() {
		System.out.println("TEST 8");
		System.out.println("[1 adult, 1 child, 1 infant, 45 days to departure date, flying CDG -> FRA]");
		AvailabilityResult availabilityResult = searchEngine.search("CDG", "FRA", 45, 1, 1, 1);
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
			AvailabilityResult availabilityResult = searchEngine.search("AMS", "FRA", 31, 1, 0, 0);		
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
			AvailabilityResult availabilityResult = searchEngine.search("LHR", "IST", 15, 2, 1, 1);		
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
			AvailabilityResult availabilityResult = searchEngine.search("BCN", "MAD", 2, 1, 2, 0);		
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
			AvailabilityResult availabilityResult = searchEngine.search("CPH", "FCO", 0, 1, 0, 0);
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
			AvailabilityResult availabilityResult = searchEngine.search("CDG", "CPH", 17, 3, 2, 2);
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
			AvailabilityResult availabilityResult = searchEngine.search("FRA", "LHR", 60, 1, 1, 1);
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
			AvailabilityResult availabilityResult = searchEngine.search("FCO", "AMS", 300, 2, 2, 2);
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
			AvailabilityResult availabilityResult = searchEngine.search("MAD", "FRA", 20, 3, 3, 3);
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
	
}
