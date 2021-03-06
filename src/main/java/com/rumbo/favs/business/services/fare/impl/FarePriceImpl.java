package com.rumbo.favs.business.services.fare.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rumbo.favs.business.bean.result.BreakDownPrice;
import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.bean.result.TravellerPrice;
import com.rumbo.favs.business.bean.search.SearchCriteria;
import com.rumbo.favs.business.enums.PassengerType;
import com.rumbo.favs.business.services.fare.IFarePrice;
import com.rumbo.favs.data.dao.IDepartureDateDao;
import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.dao.IPassengerDiscountDao;
import com.rumbo.favs.data.entities.DepartureDate;
import com.rumbo.favs.data.entities.Flight;
import com.rumbo.favs.data.entities.InfantPrice;
import com.rumbo.favs.data.entities.PassengerDiscount;

/**
 * Main fare interface
 * 
 * Get fare price from search criteria
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class FarePriceImpl implements IFarePrice{
	
	private IPassengerDiscountDao discountByPassengerTypeDao;	
	private IDepartureDateDao daysToDepartureDateDao;	
	private IInfantPriceDao infantPricesDao;	
		
	public FarePriceImpl() {		
	}
	
	public FarePriceImpl(IPassengerDiscountDao discountByPassengerTypeDao, IDepartureDateDao daysToDepartureDateDao,
			IInfantPriceDao infantPricesDao) {
		this.discountByPassengerTypeDao = discountByPassengerTypeDao;
		this.daysToDepartureDateDao = daysToDepartureDateDao;
		this.infantPricesDao = infantPricesDao;
	}

	/**
	 * Calculate fare result by flight from search criteria
	 * 
	 * @param searchCriteria
	 * @param flight
	 * @return FlightResult
	 */
	public FlightResult getFlightResult(SearchCriteria searchCriteria, Flight flight){
		
		String flightNumber = flight.getAirline();
		String airLine = getAirline(flight);		
		Float basePrice = flight.getBasePrice();	
		Float totalAmount = 0f;
		
		List<TravellerPrice> travellerPriceList = getTravellerPriceList(airLine, basePrice, searchCriteria);				
		totalAmount = getAmountByFlight(travellerPriceList);	
		
		return new FlightResult(flightNumber, totalAmount, travellerPriceList);		
	}
	
	/**
	 * Calculate fare amount by flight
	 * 
	 * @param travellerPriceList
	 * @return
	 */
	private float getAmountByFlight(List<TravellerPrice> travellerPriceList){
			
		float totalAmount = 0f;
		
		if (travellerPriceList != null){
			for (TravellerPrice travellerPrice: travellerPriceList){
				totalAmount += travellerPrice.getTotalAmount();
			}
		}				
		
		return precision(2,totalAmount);
	}
	
	private float precision(int decimalPlace, float d) {

	    BigDecimal bd = new BigDecimal(Float.toString(d));
	    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	    return bd.floatValue();
	}
	
	/**
	 * Get an airline from flight
	 * 
	 * @return String airline 
	 * */
	private String getAirline(Flight flight){
		
		return flight.getAirline().substring(0,2);
	}
	
	/**
	 * Get a list for each passenger type
	 * with their breakdown price
	 * 
	 * @return List<TravellerPrice> travellerPriceList 
	 * */
	private List<TravellerPrice> getTravellerPriceList(String airline, float basePrice, SearchCriteria searchCriteria){
		
		List<TravellerPrice> travellerPriceList = new ArrayList<>();
		float basePriceAux = basePrice;
		
		// Iterate for each passenger type
		for (Map.Entry<PassengerType, Integer> entry : searchCriteria.getPassengers().entrySet()) {

			boolean applyDateDiscount = true;
			PassengerType passengerType = entry.getKey();
			Integer numPassengers = entry.getValue();
			
			// Exist this passenger type
			if(numPassengers > 0){	
				if (passengerType.equals(PassengerType.INF)){
					applyDateDiscount = false;
					InfantPrice infantPrice = infantPricesDao.getDiscountByAirline(airline);
					
					if (infantPrice != null){
						basePriceAux = infantPrice.getPrice();
					}
				}
				
				travellerPriceList.add(
						new TravellerPrice(passengerType, numPassengers, getBreakDownPrice(airline, passengerType, basePriceAux, searchCriteria.getDaysToDeparture(),applyDateDiscount)));
				basePriceAux = basePrice;
			}
		}	
		
		if (travellerPriceList.size() > 0){
			setAmountByPaxType(travellerPriceList);
		}
		
		return travellerPriceList;		
	}
	
	/**
	 * Set fare amount by all passengers type in a flight
	 * 
	 * @param travellerPriceList
	 */
	private void setAmountByPaxType(List<TravellerPrice> travellerPriceList){
				
		for (TravellerPrice travellerPrice: travellerPriceList){
			getTravellerAmount(travellerPrice);
		}
	}
	
	/**
	 * Calculate amount by fare passenger type
	 * 
	 * @param travellerPrice
	 */
	private void getTravellerAmount(TravellerPrice travellerPrice){
		
		if (travellerPrice != null && travellerPrice.getNumber() > 0){
			
			float amount = 0;		
			
			amount = applyDiscount(travellerPrice);			
			travellerPrice.setTotalAmount(amount * travellerPrice.getNumber());
		}
	}
	
	private float applyDiscount(TravellerPrice travellerPrice){
		
		float amount = 0;
		
		if (travellerPrice != null){
			amount = getDepartureDiscount(travellerPrice);			
			amount = getPaxDiscount(travellerPrice,amount);
		}			
		return amount;
	}
	
	private float getDepartureDiscount (TravellerPrice travellerPrice){

		if (travellerPrice != null && travellerPrice.getBreakDownPrice() != null && travellerPrice.getBreakDownPrice().getDateDiscount() > 0){
				return (float) (travellerPrice.getBreakDownPrice().getDateDiscount() * travellerPrice.getBreakDownPrice().getBasePrice()) / 100;
		}else{
			return travellerPrice.getBreakDownPrice().getBasePrice();
		}	
	}
	
	private float getPaxDiscount (TravellerPrice travellerPrice, float amount){

		if (travellerPrice != null && travellerPrice.getBreakDownPrice() != null && travellerPrice.getBreakDownPrice().getPassengerDiscount() > 0){
			return (float) ((100 - travellerPrice.getBreakDownPrice().getPassengerDiscount()) * amount) / 100;
		}
		return amount;
	}
	
	/**
	 * Get a breakdown price for a passenger type
	 * with their possible discounts
	 * 
	 * @return BreakDownPrice breakDownPrice 
	 * */
	private BreakDownPrice getBreakDownPrice(String airline, PassengerType passengerType, Float basePrice, int daysToDeparture, boolean applyDateDiscount){
		
		float passengerTypediscount = 0;
		float dateDiscount = 0; 
			
		dateDiscount = applyDateDiscount ? getDateDiscount(daysToDeparture,passengerType) : 0;
		passengerTypediscount = getPassengerDiscount(passengerType);	
				
		return new BreakDownPrice(basePrice, passengerTypediscount, dateDiscount);
	}
	
	private float getDateDiscount(int daysToDeparture, PassengerType passengerType){
		
		DepartureDate departureDate = daysToDepartureDateDao.getDiscountByDays(daysToDeparture);	
		
		if (departureDate != null){
			return departureDate.getDiscount();
		}
		return 0;
	}
	
	private float getPassengerDiscount(PassengerType passengerType){
		
		PassengerDiscount passengerDiscount = discountByPassengerTypeDao.getDiscountByPaxType(passengerType);	
		
		if (passengerDiscount != null){
			return passengerDiscount.getDiscount();
		}
		return 0;
	}
	
}
