package com.rumbo.favs.business.services.fare.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.business.bean.result.BreakDownPrice;
import com.rumbo.favs.business.bean.result.FlightResult;
import com.rumbo.favs.business.bean.result.TravellerPrice;
import com.rumbo.favs.business.bean.search.SearchCriteria;
import com.rumbo.favs.business.enums.configuration.ApplicationConfigurationType;
import com.rumbo.favs.business.services.fare.IFarePrice;
import com.rumbo.favs.data.dao.IApplicationConfigurationByPassengerTypeDao;
import com.rumbo.favs.data.dao.IDepartureDateDao;
import com.rumbo.favs.data.dao.IPassengerDiscountDao;
import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.entities.ApplicationConfigurationByPassengerType;
import com.rumbo.favs.data.entities.DepartureDate;
import com.rumbo.favs.data.entities.PassengerDiscount;
import com.rumbo.favs.data.entities.Flight;
import com.rumbo.favs.data.entities.FlightGroup;
import com.rumbo.favs.data.entities.InfantPrice;

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
	
	//DAOS to use
	
	private IApplicationConfigurationByPassengerTypeDao appliConfigDao;
	
	private IPassengerDiscountDao discountByPassengerTypeDao;
	
	private IDepartureDateDao daysToDepartureDateDao;
	
	private IInfantPriceDao infantPricesDao;	

	private String active = "1";
	
	public FarePriceImpl(){
		super();
	}
	
	public FarePriceImpl(IApplicationConfigurationByPassengerTypeDao appliConfigDao,
							IDepartureDateDao daysToDepartureDateDao,
							IPassengerDiscountDao discountByPassengerTypeDao,
							IInfantPriceDao infantPricesDao){
		super();
		this.appliConfigDao = appliConfigDao;
		this.discountByPassengerTypeDao = discountByPassengerTypeDao;
		this.daysToDepartureDateDao = daysToDepartureDateDao;
		this.infantPricesDao = infantPricesDao;
	}

	/**
	 * Return price's breakdown
	 * 
	 * @return List<FlightResult> flightResultList
	 * */
	public List<FlightResult> fare(SearchCriteria searchCriteria, FlightGroup flights){
		
		List<FlightResult> flightResultList = new ArrayList<>();
		
		// Iterate all possible flights
		for (Flight flight : flights.getFlightGroup()){			
			FlightResult flightResult = getFlightResult(searchCriteria, flight);
			if (flightResult != null){
				flightResultList.add(flightResult);
			}			
		}
		
		return flightResultList;
	}
	
	/**
	 * Calculate fare result by fight from search criteria
	 * 
	 * @param searchCriteria
	 * @param flight
	 * @return FlightResult
	 */
	private FlightResult getFlightResult(SearchCriteria searchCriteria, Flight flight){
		
		String flightNumber = flight.getAirline();
		String airLine = getAirline(flight);		
		Float basePrice = Float.parseFloat(flight.getBasePrice());	
		Float totalAmount = 0f;
		
		List<TravellerPrice> travellerPriceList = getTravellerPriceList(airLine, basePrice, searchCriteria);
				
		if (travellerPriceList != null && travellerPriceList.size() > 0){
			totalAmount = getTotalAmountByFlight(travellerPriceList);	
		}
		
		return new FlightResult(flightNumber, totalAmount, travellerPriceList);		
	}
	
	/**
	 * Calculate fare amount by flight
	 * 
	 * @param travellerPriceList
	 * @return
	 */
	private float getTotalAmountByFlight(List<TravellerPrice> travellerPriceList){
			
		float totalAmount = 0f;
		
		for (TravellerPrice travellerPrice: travellerPriceList){
			totalAmount += travellerPrice.getTotalAmount();
		}
		
		return totalAmount;
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
	private List<TravellerPrice> getTravellerPriceList(String airline, Float basePrice, SearchCriteria searchCriteria){
		
		List<TravellerPrice> travellerPriceList = new ArrayList<>();
		
		// Iterate for each passenger type
		for (Map.Entry<PassengerType, Integer> entry : searchCriteria.getPassengers().entrySet()) {
			PassengerType passengerType = entry.getKey();
			Integer num = entry.getValue();
			
			// Exist this passenger type
			if(num > 0){
				// Get a base price type from application configuration for a passenger type
				// I am trying a generic solution			
				ApplicationConfigurationByPassengerType applicationConfigurationByPassengerType = 
						appliConfigDao.getApplicationConfigurationByName(ApplicationConfigurationType.INFANTBASEPRICE);
				// If apply a reduce fare set base price new value
				// else airline fare is ok
				if (applicationConfigurationByPassengerType != null){
					if (active.equals(applicationConfigurationByPassengerType.getStatus()) &&
							active.equals(applicationConfigurationByPassengerType.getPropertyValuByPassengerTypepassengerType(passengerType))){
						// Get reduce fare (infant base price)					
						InfantPrice infantPrice = infantPricesDao.getInfantPriceByAirline(airline);
						
						if (infantPrice != null && infantPrice.getPrice() != null && !infantPrice.getPrice().isEmpty()){
							basePrice = Float.parseFloat(infantPrice.getPrice());
						}
					}					
				}
				travellerPriceList.add(
						new TravellerPrice(passengerType, num, getBreakDownPrice(airline, passengerType, basePrice, searchCriteria.getDaysToDeparture())));			
			}
		}	
		
		if (travellerPriceList.size() > 0){
			setTotalAmountByPassengerType(travellerPriceList);
		}
		
		return travellerPriceList;		
	}
	
	/**
	 * Set fare amount by all passengers type in a flight
	 * 
	 * @param travellerPriceList
	 */
	private void setTotalAmountByPassengerType(List<TravellerPrice> travellerPriceList){
				
		for (TravellerPrice travellerPrice: travellerPriceList){
			getTravellerPriceAmount(travellerPrice);
		}
	}
	
	/**
	 * Calculate amount by fare passenger type
	 * 
	 * @param travellerPrice
	 */
	private void getTravellerPriceAmount(TravellerPrice travellerPrice){
		
		if (travellerPrice != null && travellerPrice.getNumber() > 0){
			
			float amount = 0;
			
			//If has date departure discount
			if(travellerPrice.getBreakDownPrice().getDateDiscount() > 0){
				amount = (float) (travellerPrice.getBreakDownPrice().getDateDiscount() * travellerPrice.getBreakDownPrice().getBasePrice()) / 100;
			}else{
				amount = travellerPrice.getBreakDownPrice().getBasePrice();
			}
			
			//If has passenger discount
			if (travellerPrice.getBreakDownPrice().getPassengerDiscount() > 0){
				amount =  (float) ((100 - travellerPrice.getBreakDownPrice().getPassengerDiscount()) * amount) / 100;
			}
			
			travellerPrice.setTotalAmount(amount * travellerPrice.getNumber());
		}
	}
	
	/**
	 * Get a breakdown price for a passenger type
	 * with their possible discounts
	 * 
	 * @return BreakDownPrice breakDownPrice 
	 * */
	private BreakDownPrice getBreakDownPrice(String airline, PassengerType passengerType, Float basePrice, int daysToDeparture){
		
		float passengerDiscount = 0;
		float dateDiscount = 0; 
		
		// Days to departure date discount
		ApplicationConfigurationByPassengerType applicationConfigurationByPassengerType = 
				appliConfigDao.getApplicationConfigurationByName(ApplicationConfigurationType.DAYSTODEPARTUREDATE);	
		
		//Active discount && apply discount for this passenger type
		if (applicationConfigurationByPassengerType != null){
			if (active.equals(applicationConfigurationByPassengerType.getStatus()) &&
					active.equals(applicationConfigurationByPassengerType.getPropertyValuByPassengerTypepassengerType(passengerType))){
				
				// Get info discount
				DepartureDate daysToDepartureDate = daysToDepartureDateDao.getDiscountPercent(daysToDeparture);
				
				if(daysToDepartureDate != null && !daysToDepartureDate.getDiscountPercent().isEmpty()){
					dateDiscount = Float.parseFloat(daysToDepartureDate.getDiscountPercent());
				}				
			}
		}
		
		// Discount by passenger type
		applicationConfigurationByPassengerType = 
				appliConfigDao.getApplicationConfigurationByName(ApplicationConfigurationType.DISCOUNTBYPASSENERTYPE);		
		
		//Active discount && apply discount for this passenger type
		if (applicationConfigurationByPassengerType != null){
			if (active.equals(applicationConfigurationByPassengerType.getStatus()) &&
					active.equals(applicationConfigurationByPassengerType.getPropertyValuByPassengerTypepassengerType(passengerType))){
				
				// Get info discount
				PassengerDiscount discountByPassengerType = discountByPassengerTypeDao.getDiscountPercent(passengerType);
				
				if(discountByPassengerType != null && !discountByPassengerType.getDiscountPercent().isEmpty()){
					passengerDiscount = Float.parseFloat(discountByPassengerType.getDiscountPercent());
				}	
			}
		}
				
		return new BreakDownPrice(basePrice, passengerDiscount, dateDiscount);
	}
		
}
