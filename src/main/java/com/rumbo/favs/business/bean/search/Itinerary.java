package com.rumbo.favs.business.bean.search;

public class Itinerary {

	private String departureAirport;
	private String arrivalAirport;
		
	public Itinerary() {
	}

	public Itinerary(String departureAirport, String arrivalAirport) {
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
	}
	
	public static Builder builder(){
		return new Itinerary.Builder();
	}
	
	public String getDepartureAirport() {
		return departureAirport;
	}
	
	public String getArrivalAirport() {
		return arrivalAirport;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arrivalAirport == null) ? 0 : arrivalAirport.hashCode());
		result = prime * result + ((departureAirport == null) ? 0 : departureAirport.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Itinerary other = (Itinerary) obj;
		if (arrivalAirport == null) {
			if (other.arrivalAirport != null)
				return false;
		} else if (!arrivalAirport.equals(other.arrivalAirport))
			return false;
		if (departureAirport == null) {
			if (other.departureAirport != null)
				return false;
		} else if (!departureAirport.equals(other.departureAirport))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Itinerary [departureAirport=" + departureAirport + ", arrivalAirport=" + arrivalAirport + "]";
	}

	public static class Builder{
		private String departureAirport;
		private String arrivalAirport;
		
		public Builder withDepartureAirport(String departureAirport){
			this.departureAirport = departureAirport;
			return this;
		}
		
		public Builder withArrivalAirport(String arrivalAirport){
			this.arrivalAirport = arrivalAirport;
			return this;
		}
		
		public Itinerary build(){
			return new Itinerary(departureAirport,arrivalAirport);
		}
		
	}
	
}
