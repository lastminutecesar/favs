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
public class DepartureDateDaoImpl extends GenericDao implements IDepartureDateDao{
	
	private final String DEPARTUREDATE_FILE = "src/main/resources/files/departureDate.csv";
	
	private List<DepartureDate> departureDates = new ArrayList<>();
	
	public DepartureDateDaoImpl(){
		csvToObject(DEPARTUREDATE_FILE);
	}
		
	protected void processLine(String[] line) {
		
		if (line.length > 2){
			DepartureDate departureDate = 
					new DepartureDate(Integer.parseInt(line[0]),Integer.parseInt(line[1]),Float.parseFloat(line[2]));
			departureDates.add(departureDate);			
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
