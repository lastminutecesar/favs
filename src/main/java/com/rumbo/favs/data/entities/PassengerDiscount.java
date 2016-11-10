package com.rumbo.favs.data.entities;

import com.rumbo.favs.business.enums.PassengerType;


/**
 * Discount by passenger type class 
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */

public class PassengerDiscount {

	private PassengerType passengerType;
	private float discount;
			
	public PassengerDiscount() {
	}	

	public PassengerDiscount(PassengerType passengerType, float discountPercent) {
		this.passengerType = passengerType;
		this.discount = discountPercent;
	}

	public PassengerType getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(PassengerType passengerType) {
		this.passengerType = passengerType;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PassengerDiscount other = (PassengerDiscount) obj;
		if (Float.floatToIntBits(discount) != Float.floatToIntBits(other.discount))
			return false;
		if (passengerType != other.passengerType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DiscountByPassengerType [passengerType=" + passengerType + ", discount=" + discount + "]";
	}
	
}
