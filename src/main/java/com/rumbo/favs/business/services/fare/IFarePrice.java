package com.rumbo.favs.business.services.fare;

import java.util.List;

import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.bean.search.SearchCriteria;
import com.rumbo.favs.data.entities.FlightGroup;

/**
 * Main fare interface
 * 
 * Get fare from search criteria
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IFarePrice {

	/**
	 * @return price's breakdown
	 * 
	 * */
	public List<FlightResult> fare(SearchCriteria searchCriteria, FlightGroup flights);
	
}
