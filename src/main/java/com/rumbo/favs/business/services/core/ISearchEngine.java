package com.rumbo.favs.business.services.core;

import com.rumbo.favs.business.bean.exceptions.search.SearchCriteriaException;
import com.rumbo.favs.business.bean.result.AvailabilityResult;
import com.rumbo.favs.business.bean.search.SearchCriteria;

/**
 * Main business interface
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface ISearchEngine {

	/**
	 * 
	 * @param origin
	 * @param destination
	 * @param daysToDeparture
	 * @param numAdult
	 * @param numChild
	 * @param numInfant
	 * @return search result
	 * @throws SearchCriteriaException
	 */
	public AvailabilityResult search(SearchCriteria searchCriteria)  throws SearchCriteriaException;
	
}
