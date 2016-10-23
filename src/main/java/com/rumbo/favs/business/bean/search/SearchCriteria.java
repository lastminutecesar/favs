package com.rumbo.favs.business.bean.search;

import java.util.Map;
import java.util.TreeMap;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.entities.Airport;


/**
 * Flight Search Criteria
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class SearchCriteria {

	private Itinerary itinerary = new Itinerary();
	private int daysToDeparture = 0;
	
	// TreeMap to get an ordered map by key
	private Map<PassengerType,Integer> passengers = new TreeMap<PassengerType, Integer>();
	
	IAirportDao airportDao = null;
	
	public static final int MIN_DAYSTODEPARTURE = 0;
	public static final int MAX_DAYSTODEPARTURE = 365;
	public static final int MIN_PASSENGERS = 0;
	public static final int MAX_PASSENGERS = 9;
	
	public SearchCriteria() {
		super();
	}
	
	public SearchCriteria(Itinerary itinerary, int daysToDeparture, Map<PassengerType,Integer> passengers, IAirportDao airportDao) throws SearchCriteriaException{
		this.itinerary = itinerary;
		this.daysToDeparture = daysToDeparture;
		this.passengers = passengers;
		this.airportDao = airportDao;
		
		validate();
	}
	
	public SearchCriteria(Itinerary itinerary, int daysToDeparture, Map<PassengerType,Integer> passengers) throws SearchCriteriaException{
		this.itinerary = itinerary;
		this.daysToDeparture = daysToDeparture;
		this.passengers = passengers;
		
		validate();
	}
	
	/**
	 * Validate input params
	 * 
	 * @return String with message exception
	 */
	 private void validate() throws SearchCriteriaException{

		validateItinerary(getItinerary());
		validateDays(getDaysToDeparture());
		validatePassengers(getPassengers());	
	}	
	 
	 private void validateDays(int daysToDeparture){
		 
		 if (daysToDeparture < MIN_DAYSTODEPARTURE || daysToDeparture > MAX_DAYSTODEPARTURE){
				throw new SearchCriteriaException(SearchCriteriaException.ERROR_DEPARTURE_DATE);
			}
	 }
	
	 private void validatePassengers(Map<PassengerType,Integer> passengers) throws SearchCriteriaException{
		
		if(passengers != null && passengers.size() > 0){
			boolean existSomeone = false;
			for(Integer numPassengers: passengers.values()){
				if (numPassengers < MIN_PASSENGERS || numPassengers > MAX_PASSENGERS){
					throw new SearchCriteriaException(SearchCriteriaException.ERROR_NUM_PASSENGERS);
				}
				// At least one passenger
				if (numPassengers > MIN_PASSENGERS){
					existSomeone = true;
				}
			}
			if (!existSomeone){
				throw new SearchCriteriaException(SearchCriteriaException.ERROR_NONE_PASSENGERS);	
			}
			
		}else{
			throw new SearchCriteriaException(SearchCriteriaException.ERROR_FATAL_PASSENGERS);
		}
	}
	
	private void validateItinerary(Itinerary itinerary){
		
		if (itinerary != null){
			
			String departureAirport = itinerary.getDepartureAirport();
			String arrivalAirport = itinerary.getArrivalAirport();
			
			if(!validateAirport(departureAirport)){
				throw new SearchCriteriaException(SearchCriteriaException.ERROR_DEPARTURE_CITY);
			}
			
			if(!validateAirport(arrivalAirport)){
				throw new SearchCriteriaException(SearchCriteriaException.ERROR_DESTINATION_CITY);
			}
		}
	}
	
	private boolean validateAirport(String airportCode){
		
		if (airportCode != null && !airportCode.isEmpty()){
			
			Airport airport = airportDao.getAirportByIataCode(airportCode);
			
			if (airport != null && airport.getIataCode() != null && !airport.getIataCode().isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	public int getDaysToDeparture() {
		return daysToDeparture;
	}
	
	public void setDaysToDeparture(int daysToDeparture) {
		this.daysToDeparture = daysToDeparture;
	}
	
	public Map<PassengerType, Integer> getPassengers() {
		return passengers;
	}
	
	public void setPassengers(Map<PassengerType, Integer> passengers) {
		this.passengers = passengers;
	}

	public Itinerary getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary itinerary) {
		this.itinerary = itinerary;
	}

	public void setAirportDao(IAirportDao airportDao) {
		this.airportDao = airportDao;
	}
	
}
