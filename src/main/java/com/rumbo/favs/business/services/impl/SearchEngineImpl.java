package com.rumbo.favs.business.services.impl;

import java.util.List;

import org.w3c.dom.Node;

import com.rumbo.favs.business.bean.AvailabilityResult;
import com.rumbo.favs.business.bean.FlightResult;
import com.rumbo.favs.business.bean.ResultType;
import com.rumbo.favs.business.bean.SearchCriteria;
import com.rumbo.favs.business.bean.exceptions.SearchCriteriaException;
import com.rumbo.favs.business.services.IFarePrice;
import com.rumbo.favs.business.services.ISearchEngine;
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
 */
public class SearchEngineImpl implements ISearchEngine{
	
	private ManageProperties manageProperties = new ManageProperties();
	
	private IAirportDao airportDao = ServiceFactory.getAirportDaoFactory();
	
	private IFlightDao flightDao = ServiceFactory.getFlightDaoFactory();
	/*
	private IApplicationConfigurationByPassengerTypeDao appliConfigDao = ServiceFactory.getApplicationConfigurationByPassengerTypeDaoFactory();
	
	private IDaysToDepartureDateDao daysToDepartureDao = ServiceFactory.getDaysToDepartureDateDaoFactory();
	
	private IDiscountByPassengerTypeDao discountByPassengerDao = ServiceFactory.getDiscountByPassengerTypeDaoFactory();
	
	private IInfantPricesDao infantPricesDao = ServiceFactory.getInfantPricesDaoFactory();
	
*/
	
	private Node nodeFlight = null;

	public SearchEngineImpl(){
	}
	
	public AvailabilityResult search(String origin, String destination, int daysToDeparture, int numAdult, int numChild, int numInfant) throws SearchCriteriaException{
		
		try{
			SearchCriteria searchCriteria = new SearchCriteria(origin, destination, daysToDeparture, numAdult, numChild, numInfant, airportDao);
			
			{
				// Search if exist flight combination
				FlightGroup flights = flightDao.getFlightsByItinerary(searchCriteria.getDepartureCity(), searchCriteria.getArrivalCity());
				
				// Exist flight combination
				if (flights != null && flights.getFlightGroup().size() > 0){
					IFarePrice farePrice = new FarePriceImpl();
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
