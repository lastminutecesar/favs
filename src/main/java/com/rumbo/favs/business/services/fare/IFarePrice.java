package com.rumbo.favs.business.services.fare;

import java.util.List;

import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.bean.search.SearchCriteria;
import com.rumbo.favs.data.dao.IDepartureDateDao;
import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.dao.IPassengerDiscountDao;
import com.rumbo.favs.data.entities.Flight;

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
	public FlightResult getFlightResult(SearchCriteria searchCriteria, Flight flights);	

	public void setDiscountByPassengerTypeDao(IPassengerDiscountDao discountByPassengerTypeDao);

	public void setDaysToDepartureDateDao(IDepartureDateDao daysToDepartureDateDao);

	public void setInfantPricesDao(IInfantPriceDao infantPricesDao);
	
}
