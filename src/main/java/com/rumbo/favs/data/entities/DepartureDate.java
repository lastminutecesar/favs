package com.rumbo.favs.data.entities;

/**
 * Days to departure date class 
 * to work with jaxb and dom
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class DepartureDate {

	private int minDay;
	private int maxDay;
	private float discount;
			
	public DepartureDate() {
		super();
	}

	public DepartureDate(int min, int max, float discount) {
		super();
		this.minDay = min;
		this.maxDay = max;
		this.discount = discount;
	}

	public int getMin() {
		return minDay;
	}

	public void setMin(int min) {
		this.minDay = min;
	}

	public int getMax() {
		return maxDay;
	}

	public void setMax(int max) {
		this.maxDay = max;
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
		result = prime * result + maxDay;
		result = prime * result + minDay;
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
		DepartureDate other = (DepartureDate) obj;
		if (Float.floatToIntBits(discount) != Float.floatToIntBits(other.discount))
			return false;
		if (maxDay != other.maxDay)
			return false;
		if (minDay != other.minDay)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DepartureDate [min=" + minDay + ", max=" + maxDay + ", discount=" + discount + "]";
	}
	
}
