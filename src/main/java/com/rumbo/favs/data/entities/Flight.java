package com.rumbo.favs.data.entities;

import com.rumbo.favs.business.bean.search.Itinerary;

/**
 * Itinerary class to work with jaxb and dom
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class Flight {

	private Itinerary itinerary;
	private String airline;
	private float basePrice;
			
	public Flight() {
	}

	public Flight(Itinerary itinerary) {
		this.itinerary = itinerary;
	}
	
	public Flight(Itinerary itinerary, String airline, float basePrice) {
		this.itinerary = itinerary;
		this.airline = airline;
		this.basePrice = basePrice;
	}
	
	public static Builder builder(){
		return new Flight.Builder();
	}
	
	public Itinerary getItinerary() {
		return itinerary;
	}
	
	public String getAirline() {
		return airline;
	}
		
	public float getBasePrice() {
		return basePrice;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline == null) ? 0 : airline.hashCode());
		result = prime * result + Float.floatToIntBits(basePrice);
		result = prime * result + ((itinerary == null) ? 0 : itinerary.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		Flight flight = (Flight) obj;

		if (flight != null){
			return flight.itinerary.equals(itinerary);			
		}else{
			return false;
		}		
	}

	@Override
	public String toString() {
		return "Flight [itinerary=" + itinerary + ", airline=" + airline + ", basePrice=" + basePrice + "]";
	}

	public static class Builder{
		
		private Itinerary itinerary;
		private String airline;
		private float basePrice;
		
		public Builder withItinerary(Itinerary itinerary){
			this.itinerary = itinerary;
			return this;
		}
		
		public Builder withBasePrice(float basePrice){
			this.basePrice = basePrice;
			return this;
		}
		
		public Builder withAirline(String airline){
			this.airline = airline;
			return this;
		}
		
		public Flight build(){
			return new Flight (itinerary, airline, basePrice);
		}
	}
	
}
