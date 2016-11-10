package com.rumbo.favs.business.bean.result;

import java.util.List;

import com.rumbo.favs.business.enums.ResultType;
import com.rumbo.favs.utilities.MyMath;

/**
 * Represents search result
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class AvailabilityResult {

	private ResultType result;
	private String description;
	private List<FlightResult> flightResultList;

	public ResultType getResult() {
		return result;
	}	

	/**
	 * Represents search result like this:
	 * TK8891, 806 EUR (2 * (120% of 250) + 67% of (120% of 250) + 5)
	 * 
	 */
	@Override
	public String toString() {
		
		String newLine = "\n";
		String comma = ",";
		String space = " ";
		String euro = " EUR ";
		
		if (result.equals(ResultType.KO)){
			return description;
		}else{
			StringBuffer response = new StringBuffer();
			
			// Iterate searched flights
			for(FlightResult flightResult : flightResultList){
				
				response.append(flightResult.getFlight()).append(comma).append(space).
					append(MyMath.getRoundedFloat(flightResult.getTotalAmount())).append(euro);
				response.append(getTPListText(flightResult.getTravellerPriceList()));
				response.append(newLine);
			}
			
			return response.toString();
		}
	}

	/**
	 * Get text of breakdown price of all passenger type in a flight
	 * 
	 * @param travellerPriceList
	 * @return StringBuffer text of breakdown price of all passenger type in a flight
	 */
	private StringBuffer getTPListText(List<TravellerPrice> travellerPriceList){
		
		StringBuffer response = new StringBuffer();
		StringBuffer responseAux = new StringBuffer();	
		String parenthesisOpen = "(";
		String parenthesisClose = ")";
		String plus = " + ";		
		boolean firstTime = true;		
		
		responseAux.append(parenthesisOpen);
		
		for (TravellerPrice travellerPrice : travellerPriceList){
			
			//Add plus symbol
			if (firstTime){
				firstTime = false;;
			}else{
				responseAux.append(plus);
			}
			responseAux.append(getTPText(travellerPrice));
		}
		
		responseAux.append(parenthesisClose);		
		response.append(responseAux);
		
		return response;
		
	}
	
	/**
	 * Get text of breakdown price of a passenger type
	 * 
	 * @param travellerPrice
	 * @return String text of breakdown price of a passenger type
	 */
	private String getTPText(TravellerPrice travellerPrice){
		
		if (travellerPrice != null && travellerPrice.getNumber() > 0){
			StringBuffer response = new StringBuffer();
			String parenthesisOpen = "(";
			String parenthesisClose = ")";
			String space = " ";
			String percent = "%";
			String multiply = "*";
			String of = "of";
			float hundred = 100;
			
			// More than 1 passenger of this passenger type
			if (travellerPrice.getNumber() > 1){
				response.append(travellerPrice.getNumber()).append(space).append(multiply).append(space);
				if (hasDiscount(travellerPrice.getBreakDownPrice())){
					response.append(parenthesisOpen);
				}
			}
			
			// If has passenger discount
			if(travellerPrice.getBreakDownPrice().getPassengerDiscount() > 0){
				response.append(MyMath.getRoundedFloat(hundred - travellerPrice.getBreakDownPrice().getPassengerDiscount())).
				append(percent).append(space).append(of).append(space).append(parenthesisOpen);
			}
			
			//If has date departure discount
			if(travellerPrice.getBreakDownPrice().getDateDiscount() > 0){
				response.append(MyMath.getRoundedFloat(travellerPrice.getBreakDownPrice().getDateDiscount())).
				append(percent).append(space).append(of).append(space).append(MyMath.getRoundedFloat(travellerPrice.getBreakDownPrice().getBasePrice()));
			}else{
				response.append(MyMath.getRoundedFloat(travellerPrice.getBreakDownPrice().getBasePrice()));
			}
			
			// More than 1 passenger of this passenger type
			// and has discount
			if(travellerPrice.getNumber() > 1 && hasDiscount(travellerPrice.getBreakDownPrice())){
				response.append(parenthesisClose);
			}
			
			// If has passenger discount
			if(travellerPrice.getBreakDownPrice().getPassengerDiscount() > 0){
				response.append(parenthesisClose);
			}
			
			return response.toString();
		}		
		
		return ""; 				
	}	

	/**
	 * If breakDownPrice has date departure 
	 * 
	 * @param breakDownPrice
	 * @return boolean If breakDownPrice has date departure 
	 */
	private boolean hasDiscount(BreakDownPrice breakDownPrice){		
		return breakDownPrice.getDateDiscount() > 0 || breakDownPrice.getPassengerDiscount() > 0;		
	}	
	
	public void setResult(ResultType result) {
		this.result = result;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<FlightResult> getFlightResultList() {
		return flightResultList;
	}
	
	public void setFlightResultList(List<FlightResult> flightResultList) {
		this.flightResultList = flightResultList;
	}	
	
}
