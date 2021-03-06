package com.rumbo.favs.business.bean.exceptions.search;

/**
 * Exception search errors
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class SearchCriteriaException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	// Different messages allowed in the exception
	
	public static final String ERROR_DEPARTURE_CITY = "Departure city not exists";
	public static final String ERROR_DESTINATION_CITY = "Arrival city not exists";
	public static final String ERROR_DEPARTURE_DATE = "Departure city has to be greater than 0 and less than 366";
	public static final String ERROR_NUM_PASSENGERS = "Passengers have to be greater than 0 and less than 10";
	public static final String ERROR_NONE_PASSENGERS = "At least one passenger must flight";
	public static final String ERROR_FATAL_PASSENGERS = "Fatal Error in passengers";
	public static final String ERROR_BBDD_CONNECTION = "BBDD error connection";
			   
    public SearchCriteriaException(String errorType) {
        super(errorType);
    }    
    
	public String toString() {
    	
        return "Search Criteria Error: " + getMessage();
    }

}
