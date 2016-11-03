package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.entities.DepartureDate;
import com.rumbo.favs.data.entities.InfantPrice;

/**
 * Infant prices DAO
 * Manage infant prices
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class InfantPriceDaoImpl extends GenericDao implements IInfantPriceDao{
	
	private final String INFANTPRICES_FILE = "src/main/resources/files/infantPrices.csv";
	
	private Map<String,InfantPrice> infantPrices = new HashMap<>();
	
	public InfantPriceDaoImpl(){
		csvToObject(INFANTPRICES_FILE);
	}
	
	protected void processLine(String[] line) {
		
		if (line.length > 2){
			InfantPrice infantPrice = new InfantPrice(line[0],line[1],Float.parseFloat(line[2]));
			infantPrices.put(line[0], infantPrice);		
		}	
	}

	/**
	 * Get infant price by airline
	 * 
	 * @param String airline
	 * @return InfantPrice
	 */
	public InfantPrice getInfantPriceByAirline(String airline) {
		
		if (airline != null && !airline.isEmpty()){		    
		    return infantPrices.get(airline);
		} 		
		return null;
	}
	
}
