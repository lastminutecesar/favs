package com.rumbo.favs.business.bean.search;

import java.util.Map;
import java.util.TreeMap;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.dao.IFlightDao;
import com.rumbo.favs.data.dao.ServiceFactory;
import com.rumbo.favs.data.entities.Airport;


/**
 * Flight Search Criteria
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class SearchCriteria {

	private String departureCity = null;
	private String arrivalCity = null;
	private int daysToDeparture = 0;
	
	// TreeMap to get an ordered map by key
	private Map<PassengerType,Integer> passengers = new TreeMap<PassengerType, Integer>();
	
	IAirportDao airportDao = null;
	
	public SearchCriteria() {
		super();
	}

	public SearchCriteria(String departureCity, String arrivalCity, int daysToDeparture, int numAdult, int numChild, int numInfant, IAirportDao airportDao) throws SearchCriteriaException{
		super();
		
		if (airportDao != null){
			this.airportDao = airportDao;
			
			String messageType = validate(departureCity, arrivalCity, daysToDeparture, numAdult, numChild, numInfant);
			if (!messageType.isEmpty()){
				throw new SearchCriteriaException(messageType);
			}
			
			this.departureCity = departureCity;
			this.arrivalCity = arrivalCity;
			this.daysToDeparture = daysToDeparture;
			this.passengers.put(PassengerType.ADT, numAdult);
			this.passengers.put(PassengerType.CHD, numChild);
			this.passengers.put(PassengerType.INF, numInfant);
		}else{
			throw new SearchCriteriaException(SearchCriteriaException.ERROR_BBDD_CONNECTION);
		}		

	}
	
	/**
	 * Validate input params
	 * 
	 * @param departureCity
	 * @param arrivalCity
	 * @param daysToDeparture
	 * @param numAdult
	 * @param numChild
	 * @param numInfant
	 * @return String with message exception
	 */
	public String validate(String departureCity, String arrivalCity, int daysToDeparture, int numAdult, int numChild, int numInfant){
		
		if (!existsCity(departureCity)){
			return SearchCriteriaException.ERROR_DEPARTURE_CITY;
		}
		if (!existsCity(arrivalCity)){
			return SearchCriteriaException.ERROR_DESTINATION_CITY;
		}
		if (daysToDeparture < 0){
			return SearchCriteriaException.ERROR_DEPARTURE_DATE;
		}
		if (numAdult < 0 || numChild < 0 || numInfant < 0){
			return SearchCriteriaException.ERROR_NUM_PASSENGERS;
		}
		
		if (numAdult == 0 && numChild == 0 && numInfant == 0){
			return SearchCriteriaException.ERROR_NONE_PASSENGERS;
		}
		
		return "";		
	}	
	
	/**
	 * If exists city in bbdd
	 * 
	 * @param city
	 * @return if city is allowed
	 */
	private boolean existsCity(String city){
		
		if (city != null && !city.isEmpty()){
			
			Airport airport = airportDao.getAirportByIataCode(city);
			
			if (airport != null && airport.getIataCode() != null && !airport.getIataCode().isEmpty()){
				return true;
			}
		}
		
		return false;
	}

	public String getDepartureCity() {
		return departureCity;
	}
	
	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}
	
	public String getArrivalCity() {
		return arrivalCity;
	}
	
	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrivalCity == null) ? 0 : arrivalCity.hashCode());
		result = prime * result + daysToDeparture;
		result = prime * result + ((departureCity == null) ? 0 : departureCity.hashCode());
		result = prime * result + ((passengers == null) ? 0 : passengers.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "SearchCriteria [departureCity=" + departureCity + ", arrivalCity=" + arrivalCity + ", daysToDeparture=" + daysToDeparture + ", passengers="
				+ passengers + "]";
	}
	
}
