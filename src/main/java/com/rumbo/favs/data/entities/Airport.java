package com.rumbo.favs.data.entities;


/**
 * Airport class
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class Airport {

	private String iataCode;
	private String city;
	
	public Airport() {
	}
	
	public Airport(String iataCode, String city) {
		this.iataCode = iataCode;
		this.city = city;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (iataCode == null) {
			if (other.iataCode != null)
				return false;
		} else if (!iataCode.equals(other.iataCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Airport [iataCode=" + iataCode + ", city=" + city + "]";
	}
		
}
