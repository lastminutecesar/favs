package com.rumbo.favs.business.bean.result;

import com.rumbo.favs.business.bean.PassengerType;

/**
 * Price info of a passenger type
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class TravellerPrice{

	private PassengerType type;
	private int number;
	private float totalAmount;
	private BreakDownPrice breakDownPrice;
		
	public TravellerPrice() {
	}
	
	public TravellerPrice(PassengerType type, int number, BreakDownPrice breakDownPrice) {
		this.type = type;
		this.number = number;
		this.breakDownPrice = breakDownPrice;
	}
	
	public PassengerType getType() {
		return type;
	}
	
	public void setType(PassengerType type) {
		this.type = type;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public BreakDownPrice getBreakDownPrice() {
		return breakDownPrice;
	}
	
	public void setBreakDownPrice(BreakDownPrice breakDownPrice) {
		this.breakDownPrice = breakDownPrice;
	}	
	
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "TravellerPrice [type=" + type + ", number=" + number + ", totalAmount=" + totalAmount + ", breakDownPrice=" + breakDownPrice + "]";
	}
		
}
