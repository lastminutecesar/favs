package com.rumbo.favs.business.services.core;

import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.business.bean.result.AvailabilityResult;

/**
 * Main business interface
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface ISearchEngine {

	/**
	 * @return the search result
	 * */
	public AvailabilityResult search(String origin, String destination, int daysToDeparture, int numAdult, int numChild, int numInfant)  throws SearchCriteriaException;
	
}
