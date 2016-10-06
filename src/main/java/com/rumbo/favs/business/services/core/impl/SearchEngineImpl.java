package com.rumbo.favs.business.services.core.impl;

import java.util.List;

import org.w3c.dom.Node;

import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.business.bean.result.AvailabilityResult;
import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.bean.search.SearchCriteria;
import com.rumbo.favs.business.enums.result.ResultType;
import com.rumbo.favs.business.services.core.ISearchEngine;
import com.rumbo.favs.business.services.fare.IFarePrice;
import com.rumbo.favs.business.services.fare.impl.FarePriceImpl;
import com.rumbo.favs.data.dao.IAirportDao;
import com.rumbo.favs.data.dao.IApplicationConfigurationByPassengerTypeDao;
import com.rumbo.favs.data.dao.IDaysToDepartureDateDao;
import com.rumbo.favs.data.dao.IDiscountByPassengerTypeDao;
import com.rumbo.favs.data.dao.IFlightDao;
import com.rumbo.favs.data.dao.IInfantPricesDao;
import com.rumbo.favs.data.dao.ServiceFactory;
import com.rumbo.favs.data.entities.FlightGroup;
import com.rumbo.favs.data.utilities.ManageProperties;

/**
 * Main business class
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class SearchEngineImpl implements ISearchEngine{
	
	private ManageProperties manageProperties = new ManageProperties();
	
	private IAirportDao airportDao;
	
	private IFlightDao flightDao;
	
	private IApplicationConfigurationByPassengerTypeDao appliConfigDao;
	
	private IDaysToDepartureDateDao daysToDepartureDao;
	
	private IDiscountByPassengerTypeDao discountByPassengerDao;
	
	private IInfantPricesDao infantPricesDao;
		
	private Node nodeFlight = null;

	public SearchEngineImpl(){
		
		//Instance daos for business only once
		
		airportDao = ServiceFactory.getAirportDaoFactory();
		
		flightDao = ServiceFactory.getFlightDaoFactory();
		
		appliConfigDao = ServiceFactory.getApplicationConfigurationByPassengerTypeDaoFactory();
		
		daysToDepartureDao = ServiceFactory.getDaysToDepartureDateDaoFactory();
		
		discountByPassengerDao = ServiceFactory.getDiscountByPassengerTypeDaoFactory();
		
		infantPricesDao = ServiceFactory.getInfantPricesDaoFactory();
	}

	/**
	 * Get a search response from search criteria
	 * 
	 * @return AvailabilityResult from search criteria
	 * @throws SearchCriteriaException if any problem in search criteria
	 */
	public AvailabilityResult search(String origin, String destination, int daysToDeparture, int numAdult, int numChild, int numInfant) throws SearchCriteriaException{
		
		try{
			SearchCriteria searchCriteria = new SearchCriteria(origin, destination, daysToDeparture, numAdult, numChild, numInfant, airportDao);
			
			{
				// Search if exist flight combination
				FlightGroup flights = flightDao.getFlightsByItinerary(searchCriteria.getDepartureCity(), searchCriteria.getArrivalCity());
				
				// Exist flight combination
				if (flights != null && flights.getFlightGroup().size() > 0){
					IFarePrice farePrice = new FarePriceImpl(appliConfigDao, daysToDepartureDao, discountByPassengerDao, infantPricesDao);
					List<FlightResult> flightResultList = farePrice.fare(searchCriteria, flights);
					
					if (flightResultList != null && !flightResultList.isEmpty()){
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
		availabilityResult.setDescription(manageProperties.getMessageProperty(resultType.toString()));
		availabilityResult.setFlightResultList(flightResultList);
		
		return availabilityResult;
	}

	public Node getNodeFlight() {
		return nodeFlight;
	}

	public void setNodeFlight(Node nodeFlight) {
		this.nodeFlight = nodeFlight;
	}	
	
}
