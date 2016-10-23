package com.rumbo.favs.data.entities;


/**
 * Infant Price by Airline class to work with jaxb and dom
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */

public class InfantPrice {

	private String iataCode;
	private String name;
	private float price;
	
	public InfantPrice() {
		super();
	}

	public InfantPrice(String iataCode, String name, float price) {
		super();
		this.iataCode = iataCode;
		this.name = name;
		this.price = price;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iataCode == null) ? 0 : iataCode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(price);
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
		InfantPrice other = (InfantPrice) obj;
		if (iataCode == null) {
			if (other.iataCode != null)
				return false;
		} else if (!iataCode.equals(other.iataCode))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InfantPrice [iataCode=" + iataCode + ", name=" + name + ", price=" + price + "]";
	}	
	
}
