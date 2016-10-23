package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.data.dao.IPassengerDiscountDao;
import com.rumbo.favs.data.entities.Airport;
import com.rumbo.favs.data.entities.PassengerDiscount;

/**
 * DiscountByPassengerType DAO
 * Manage discount by passenger type
 * 
 * This implimentation works with csv files
 * and turn the information into dom object
 * to let launch queries
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class PassengerDiscountDaoImpl implements IPassengerDiscountDao{
	
	private final String PASSENGERDISCOUNT_FILE = "src/main/resources/files/passengerDiscounts.csv";
	
	private Map<PassengerType,PassengerDiscount> passengerDiscounts = new HashMap<>();
	
	public PassengerDiscountDaoImpl(){
		csvToObject(PASSENGERDISCOUNT_FILE);
	}
	
	private void csvToObject(String csvFile){
		
		if (csvFile != null && !csvFile.isEmpty()){			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";
			
			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvPassengerDiscount = line.split(splitBy);
					PassengerDiscount passengerDiscount = new PassengerDiscount(PassengerType.valueOf(csvPassengerDiscount[0]), Float.parseFloat(csvPassengerDiscount[1]));
					passengerDiscounts.put(PassengerType.valueOf(csvPassengerDiscount[0]), passengerDiscount);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Get airport by city iata code
	 * 
	 * @param String iataCode
	 * @return InfantPrice
	 */
	public PassengerDiscount getDiscount(PassengerType passengerType) {
					
		if (passengerType != null){		    
			return passengerDiscounts.get(passengerType);
		} 		
		return null;
	}
	
}
