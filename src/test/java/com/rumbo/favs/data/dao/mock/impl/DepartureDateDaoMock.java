package com.rumbo.favs.data.dao.mock.impl;

import com.rumbo.favs.data.dao.IDepartureDateDao;
import com.rumbo.favs.data.entities.DepartureDate;

/**
 * DaysToDepartureDate DAO
 * Manage days to departure date discount
 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class DepartureDateDaoMock implements IDepartureDateDao{
	
	private final int DUMMY_DAY = 0;
	
	/**
	 * Get days to departure date 
	 * by a given day
	 * 
	 * @param int days
	 * @return DaysToDepartureDate
	 */
	public DepartureDate getDiscount(int days) {
		
		if (0 <= days && 2 >= days){
			return new DepartureDate(DUMMY_DAY,DUMMY_DAY,150);
		}else if (3 <= days && 15 >= days){
			return new DepartureDate(DUMMY_DAY,DUMMY_DAY,120);
		}else if (16 <= days && 30 >= days){
			return new DepartureDate(DUMMY_DAY,DUMMY_DAY,100);
		}else if (31 <= days && 365 >= days){
			return new DepartureDate(DUMMY_DAY,DUMMY_DAY,80);
		}else {
			return null;
		}
	}
}
