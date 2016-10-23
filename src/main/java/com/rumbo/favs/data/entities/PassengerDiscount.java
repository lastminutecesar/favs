package com.rumbo.favs.data.entities;

import com.rumbo.favs.business.bean.PassengerType;


/**
 * Discount by passenger type class 
 * to work with jaxb and dom
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */

public class PassengerDiscount {

	private PassengerType passengerType;
	private float discount;
			
	public PassengerDiscount() {
		super();
	}	

	public PassengerDiscount(PassengerType passengerType, float discountPercent) {
		super();
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(discount);
		result = prime * result + ((passengerType == null) ? 0 : passengerType.hashCode());
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
