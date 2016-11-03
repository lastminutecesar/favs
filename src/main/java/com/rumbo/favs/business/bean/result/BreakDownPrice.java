package com.rumbo.favs.business.bean.result;

/**
 * Breakdown price of a passenger type
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class BreakDownPrice {

	float basePrice;
	float passengerDiscount;
	float dateDiscount;
		
	public BreakDownPrice() {
	}

	public BreakDownPrice(float basePrice, float passengerDiscount, float dateDiscount) {
		this.basePrice = basePrice;
		this.passengerDiscount = passengerDiscount;
		this.dateDiscount = dateDiscount;
	}

	public float getBasePrice() {
		return basePrice;
	}
	
	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}
	
	public float getPassengerDiscount() {
		return passengerDiscount;
	}
	
	public void setPassengerDiscount(float passengerDiscount) {
		this.passengerDiscount = passengerDiscount;
	}
	
	public float getDateDiscount() {
		return dateDiscount;
	}
	
	public void setDateDiscount(float dateDiscount) {
		this.dateDiscount = dateDiscount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(basePrice);
		result = prime * result + Float.floatToIntBits(dateDiscount);
		result = prime * result + Float.floatToIntBits(passengerDiscount);
		return result;
	}

	@Override
	public String toString() {
		return "BreakDownPrice [basePrice=" + basePrice + ", passengerDiscount=" + passengerDiscount + ", dateDiscount=" + dateDiscount + "]";
	}
		
}
