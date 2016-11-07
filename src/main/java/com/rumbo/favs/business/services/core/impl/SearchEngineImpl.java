package com.rumbo.favs.business.services.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.business.bean.result.AvailabilityResult;
import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.bean.search.SearchCriteria;
import com.rumbo.favs.business.enums.result.ResultType;
import com.rumbo.favs.business.services.core.ISearchEngine;
import com.rumbo.favs.business.services.fare.IFarePrice;
import com.rumbo.favs.data.dao.IFlightDao;
import com.rumbo.favs.data.entities.Flight;
import com.rumbo.favs.utilities.Message;

/**
 * Main business class
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class SearchEngineImpl implements ISearchEngine{
	
	private IFlightDao flightDao;	
	private IFarePrice farePrice;	
	private Message message;
	
	public SearchEngineImpl(){
		message = new Message();
	}

	public SearchEngineImpl(IFlightDao flightDao, IFarePrice farePrice) {
		message = new Message();
		this.flightDao = flightDao;
		this.farePrice = farePrice;
	}

	/**
	 * Get a search response from search criteria
	 * 
	 * @return AvailabilityResult from search criteria
	 * @throws SearchCriteriaException if any problem in search criteria
	 */
	public AvailabilityResult search(SearchCriteria searchCriteria) throws SearchCriteriaException{
		
		try{
			List<Flight> flights = flightDao.getFlightsByItinerary(searchCriteria.getItinerary());
			List<FlightResult> flightResultList = new ArrayList<>();
			
			for(Flight flight : flights){
				FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
				if(flightResult != null){
					flightResultList.add(flightResult);
				}
			}					
			
			return getResult(flightResultList);
			
		}catch(SearchCriteriaException e){
			throw e;
		}		
	}
		
	/**
	 * Create a response from a search result
	 * 
	 * @param flightResultList
	 * @return AvailabilityResult search result
	 */
	private AvailabilityResult getResult(List<FlightResult> flightResultList){
		
		AvailabilityResult availabilityResult = new AvailabilityResult();
		ResultType resultType = ResultType.OK;
		
		if (flightResultList == null || flightResultList.isEmpty()){
			resultType = ResultType.KO;
		}
		
		availabilityResult.setResult(resultType);
		availabilityResult.setDescription(message.getMessageProperty(resultType.toString()));
		availabilityResult.setFlightResultList(flightResultList);
		
		return availabilityResult;
	}
	
}
