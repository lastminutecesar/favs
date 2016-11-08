package com.rumbo.favs.business.bean.result;

import java.util.List;

/**
 * Flight result
 * 
 * Flight and price breakdown list
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class FlightResult {

	private String flight;
	private float totalAmount = 0f;	
	private List<TravellerPrice> travellerPriceList;
		
	public FlightResult() {
	}

	public FlightResult(String flight, float totalAmount, List<TravellerPrice> travellerPriceList) {
		this.flight = flight;
		this.totalAmount = totalAmount;
		this.travellerPriceList = travellerPriceList;
	}

	public String getFlight() {
		return flight;
	}
	
	public void setFlight(String flight) {
		this.flight = flight;
	}
	
	public List<TravellerPrice> getTravellerPriceList() {
		return travellerPriceList;
	}
	
	public void setTravellerPriceList(List<TravellerPrice> travellerPriceList) {
		this.travellerPriceList = travellerPriceList;
	}
	
	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Override
	public String toString() {
		return "FlightResult [flight=" + flight + ", totalAmount=" + totalAmount + ", travellerPriceList=" + travellerPriceList + "]";
	}
		
}
