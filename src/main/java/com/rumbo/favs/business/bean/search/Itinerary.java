package com.rumbo.favs.business.bean.search;

public class Itinerary {

	private String departureAirport;
	private String arrivalAirport;
		
	public Itinerary() {
		super();
	}

	public Itinerary(String departureAirport, String arrivalAirport) {
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
	}
	
	public String getDepartureAirport() {
		return departureAirport;
	}
	
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}
	
	public String getArrivalAirport() {
		return arrivalAirport;
	}
	
	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}	
	
}
