package com.rumbo.favs.business.bean.search;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.business.enums.PassengerType;
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
	}
	
	public SearchCriteria(Itinerary itinerary, int daysToDeparture, Map<PassengerType,Integer> passengers) throws SearchCriteriaException{
		this.itinerary = itinerary;
		this.daysToDeparture = daysToDeparture;
		this.passengers = passengers;
	}
	
	public static Builder builder(){
		return new SearchCriteria.Builder();
	}
	
	public static PassengerBuilder<PassengerType, Integer> passengerBuilder(){
		return new SearchCriteria.PassengerBuilder<>();
	}
	
	/**
	 * Validate input params
	 * 
	 * @return String with message exception
	 */
	 public void validate() throws SearchCriteriaException{

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
	
	public Map<PassengerType, Integer> getPassengers() {
		return passengers;
	}

	public Itinerary getItinerary() {
		return itinerary;
	}
	
	public void setAirportDao(IAirportDao airportDao) {
		this.airportDao = airportDao;
	}
	
	public static class Builder {
		
		private Itinerary itinerary = new Itinerary();
		private int daysToDeparture = 0;
		private Map<PassengerType,Integer> passengers = new TreeMap<PassengerType, Integer>();
		private IAirportDao airportDao;
		
		public Builder withDaysToDeparture(int daysToDeparture){
			this.daysToDeparture = daysToDeparture;
			return this;
		}
		
		public Builder withDao(IAirportDao airportDao){
			this.airportDao = airportDao;
			return this;
		}
		
		public Builder withItinerary(Itinerary itinerary){		
			this.itinerary = itinerary;
			return this;
		}
		
		public Builder withPassengers(Map<PassengerType,Integer> passengers){		
			this.passengers = passengers;
			return this;
		}
		
	    public SearchCriteria build() throws SearchCriteriaException{
	    	SearchCriteria searchCriteria = new SearchCriteria(itinerary, daysToDeparture, passengers);
	    	searchCriteria.setAirportDao(airportDao);
	    	searchCriteria.validate();
	        return searchCriteria;
	    }
	}
	
	public static class PassengerBuilder<PassengerType,Integer> {
		
		private Map<PassengerType,Integer> passengers;
		
	    public PassengerBuilder() {
	        this.passengers = new HashMap<PassengerType,Integer>();
	    }
		
	    public PassengerBuilder<PassengerType,Integer> withPassenger(PassengerType k, Integer v) {
	    	passengers.put(k, v);
	        return this;
	    }

	    public Map<PassengerType,Integer> build() {
	        return passengers;
	    }		
	}
	
}
