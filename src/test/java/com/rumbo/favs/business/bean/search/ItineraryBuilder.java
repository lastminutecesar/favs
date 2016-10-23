package com.rumbo.favs.business.bean.search;

public class ItineraryBuilder {
	
	private String departureAirport;
	private String arrivalAirport;
	
	public ItineraryBuilder() {		
		this.departureAirport = "MAD";
		this.arrivalAirport = "BCN";
	}
	
	public ItineraryBuilder withDepartureAirport(String departureAriport){		
		this.departureAirport = departureAriport;
		return this;
	}
	
	public ItineraryBuilder withArrivalAirport(String arrivalAriport){		
		this.arrivalAirport = arrivalAriport;
		return this;
	}
	
    public Itinerary build(){
        return new Itinerary(departureAirport, arrivalAirport);
    }

	public static ItineraryBuilder anItinerary(){
		return new ItineraryBuilder();
	}

}
