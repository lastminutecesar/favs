package com.rumbo.favs.data.dao.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.rumbo.favs.data.dao.IDepartureDateDao;
import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.entities.DepartureDate;
import com.rumbo.favs.data.entities.InfantPrice;

public class DepartureDateDaoTest {

	@Test		
	public void testA() {
		
		IDepartureDateDao departureDateDao = new DepartureDateDaoImpl();
		
		DepartureDate departureDate = departureDateDao.getDiscount(0);
		
		assertThat(departureDate.getDiscount(),is(150f));
	}
	
	@Test		
	public void testB() {
		
		IDepartureDateDao departureDateDao = new DepartureDateDaoImpl();
		
		DepartureDate departureDate = departureDateDao.getDiscount(7);
		
		assertThat(departureDate.getDiscount(),is(120f));
	}
	
	@Test		
	public void testC() {
		
		IDepartureDateDao departureDateDao = new DepartureDateDaoImpl();
		
		DepartureDate departureDate = departureDateDao.getDiscount(25);
		
		assertThat(departureDate.getDiscount(),is(100f));
	}
	
	@Test		
	public void testD() {
		
		IDepartureDateDao departureDateDao = new DepartureDateDaoImpl();
		
		DepartureDate departureDate = departureDateDao.getDiscount(100);
		
		assertThat(departureDate.getDiscount(),is(80f));
	}
	
}
