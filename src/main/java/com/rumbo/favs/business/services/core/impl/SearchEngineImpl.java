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
import com.rumbo.favs.data.utilities.Message;

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

	/**
	 * Get a search response from search criteria
	 * 
	 * @return AvailabilityResult from search criteria
	 * @throws SearchCriteriaException if any problem in search criteria
	 */
	public AvailabilityResult search(SearchCriteria searchCriteria) throws SearchCriteriaException{
		
		try{
			{
				List<Flight> flights = flightDao.getFlightsByItinerary(searchCriteria.getItinerary());
				List<FlightResult> flightResultList = new ArrayList<>();
				
				// Exist flight combination
				if (flights != null){
					for(Flight flight : flights){
						FlightResult flightResult = farePrice.getFlightResult(searchCriteria, flight);
						if(flightResult != null){
							flightResultList.add(flightResult);
						}
					}					
					
					if (!flightResultList.isEmpty()){
						return createAvailabilityResult(ResultType.OK, flightResultList);
					}else{
						return createAvailabilityResult(ResultType.KO, null);
					}
				}else{
					// No exist flight combination
					return createAvailabilityResult(ResultType.KO, null);
				}
			}
		}catch(SearchCriteriaException sce){
			throw sce;
		}		
	}
	
	/**
	 * Create a response from a search result
	 * 
	 * @param resultType
	 * @param flightResultList
	 * @return AvailabilityResult search result
	 */
	private AvailabilityResult createAvailabilityResult(ResultType resultType, List<FlightResult> flightResultList){
		
		AvailabilityResult availabilityResult = new AvailabilityResult();
		
		availabilityResult.setResult(resultType);
		availabilityResult.setDescription(message.getMessageProperty(resultType.toString()));
		availabilityResult.setFlightResultList(flightResultList);
		
		return availabilityResult;
	}

	public void setFlightDao(IFlightDao flightDao) {
		this.flightDao = flightDao;
	}

	public void setFarePrice(IFarePrice farePrice) {
		this.farePrice = farePrice;
	}
	
}
