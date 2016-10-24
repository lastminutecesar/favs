package com.rumbo.favs.data.entities;

/**
 * Itinerary class to work with jaxb and dom
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class Flight {

	private String origin;
	private String destination;
	private String airline;
	private float basePrice;
			
	public Flight() {
	}

	public Flight(String origin, String destination) {
		this.origin = origin;
		this.destination = destination;
	}
	
	public Flight(String origin, String destination, String airline, float basePrice) {
		this.origin = origin;
		this.destination = destination;
		this.airline = airline;
		this.basePrice = basePrice;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getAirline() {
		return airline;
	}
	
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	public float getBasePrice() {
		return basePrice;
	}
	
	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airline == null) ? 0 : airline.hashCode());
		result = prime * result + Float.floatToIntBits(basePrice);
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		Flight flight = (Flight) obj;

		if (flight != null){
			if (origin != null && destination != null
					&& flight.getOrigin() != null && flight.getDestination() != null){
				return origin.equals(flight.getOrigin()) && destination.equals(flight.getDestination());
			}else{
				return false;
			}
			
		}else{
			return false;
		}
		
	}

	@Override
	public String toString() {
		return "Flight [origin=" + origin + ", destination=" + destination + ", airline=" + airline + ", basePrice=" + basePrice + "]";
	}
	
}
