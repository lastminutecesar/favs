package com.rumbo.favs.business.bean.search;

import java.util.HashMap;
import java.util.Map;

public class PassengerBuilder<PassengerType,Integer> {
	
	private Map<PassengerType,Integer> passengers;
	
    public PassengerBuilder() {
        this.passengers = new HashMap<PassengerType,Integer>();
    }
	
    public PassengerBuilder<PassengerType,Integer> withPassenger(PassengerType k, Integer v) {
    	passengers.put(k, v);
        return this;
    }

    public Map<PassengerType,Integer> build() {
        return passengers;
    }

	public static PassengerBuilder aPassengers(){
		return new PassengerBuilder();
	}
	
}
