package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.entities.InfantPrice;

/**
 * Infant prices DAO
 * Manage infant prices
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class InfantPriceDaoImpl implements IInfantPriceDao{
	
	private final String INFANTPRICES_FILE = "src/main/resources/files/infantPrices.csv";
	
	private Map<String,InfantPrice> infantPrices = new HashMap<>();
	
	public InfantPriceDaoImpl(){
		csvToObject(INFANTPRICES_FILE);
	}

	
	/**
	 * From csvFile turn the information into dom object
	 * 
	 * @param csvFile
	 */
	private void csvToObject(String csvFile) {
		
		if (csvFile != null && !csvFile.isEmpty()){
			
			BufferedReader br = null;
			String line = "";
			String splitBy = ",";

			// Read csv file
			try {				
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					String[] csvInfantPrice = line.split(splitBy);
					InfantPrice infantPrice = new InfantPrice(csvInfantPrice[0],csvInfantPrice[1],Float.parseFloat(csvInfantPrice[2]));
					infantPrices.put(csvInfantPrice[0], infantPrice);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
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
