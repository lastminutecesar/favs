package com.rumbo.favs.business.bean.search;

import java.util.Map;
import java.util.TreeMap;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.data.dao.IAirportDao;

public class SearchCriteriaBuilder {
	
	private Itinerary itinerary = new Itinerary();
	private int daysToDeparture = 0;
	private Map<PassengerType,Integer> passengers = new TreeMap<PassengerType, Integer>();
	private IAirportDao airportDao;
	
	public SearchCriteriaBuilder withDaysToDeparture(int daysToDeparture){
		this.daysToDeparture = daysToDeparture;
		return this;
	}
	
	public SearchCriteriaBuilder withDao(IAirportDao airportDao){
		this.airportDao = airportDao;
		return this;
	}
	
	public SearchCriteriaBuilder with(ItineraryBuilder itineraryBuilder){		
		this.itinerary = itineraryBuilder.build();
		return this;
	}
	
	public SearchCriteriaBuilder with(PassengerBuilder<PassengerType,Integer> passengers){		
		this.passengers = passengers.build();
		return this;
	}
	
    public SearchCriteria build() throws SearchCriteriaException{
        return new SearchCriteria(itinerary, daysToDeparture, passengers, airportDao);
    }
	
	public static SearchCriteriaBuilder aSearchCriteria(){
		return new SearchCriteriaBuilder();
	}


}
