package com.rumbo.favs.data.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rumbo.favs.data.dao.IDepartureDateDao;
import com.rumbo.favs.data.entities.DepartureDate;

/**
 * DaysToDepartureDate DAO
 * Manage days to departure date discount
 * 
 * This implimentation works with csv files
 * and turn the information into dom object
 * to let launch queries
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class DepartureDateDaoImpl implements IDepartureDateDao{
	
	private final String DEPARTUREDATE_FILE = "src/main/resources/files/departureDate.csv";
	
	private List<DepartureDate> departureDates = new ArrayList<>();
	
	public DepartureDateDaoImpl(){
		csvToObject(DEPARTUREDATE_FILE);
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
					String[] csvDaysToDepartureDate = line.split(splitBy);
					DepartureDate departureDate = 
							new DepartureDate(Integer.parseInt(csvDaysToDepartureDate[0]),Integer.parseInt(csvDaysToDepartureDate[1]),Float.parseFloat(csvDaysToDepartureDate[2]));
					departureDates.add(departureDate);
					
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
	 * Get days to departure date 
	 * by a given day
	 * 
	 * @param int days
	 * @return DaysToDepartureDate
	 */
	public DepartureDate getDiscount(int days) {
	
		for(DepartureDate departureDate : departureDates){
			if(departureDate.getMin() <= days && departureDate.getMax() >= days){
				return departureDate;
			}
		}
		return null;
	}
}
