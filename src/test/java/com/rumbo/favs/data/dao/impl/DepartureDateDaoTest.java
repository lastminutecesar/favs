package com.rumbo.favs.data.dao.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rumbo.favs.data.dao.IDepartureDateDao;
import com.rumbo.favs.data.entities.DepartureDate;

public class DepartureDateDaoTest {
	
	private static IDepartureDateDao departureDateDao;
	
	@BeforeClass
	public static void initialize()
	{
		// GIVEN
		departureDateDao = new DepartureDateDaoImpl();
	}

	@Test		
	public void testA() {
		
		// WHEN
		DepartureDate departureDate = departureDateDao.getDiscount(0);
		
		// THEN
		assertThat(departureDate.getDiscount(),is(150f));
	}
	
	@Test		
	public void testB() {
		
		// WHEN
		DepartureDate departureDate = departureDateDao.getDiscount(7);
		
		// THEN
		assertThat(departureDate.getDiscount(),is(120f));
	}
	
	@Test		
	public void testC() {
		
		// WHEN
		DepartureDate departureDate = departureDateDao.getDiscount(25);
		
		// THEN
		assertThat(departureDate.getDiscount(),is(100f));
	}
	
	@Test		
	public void testD() {
		
		// WHEN
		DepartureDate departureDate = departureDateDao.getDiscount(100);
		
		// THEN
		assertThat(departureDate.getDiscount(),is(80f));
	}
	
	@Test		
	public void testE() {
		
		// WHEN
		DepartureDate departureDate = departureDateDao.getDiscount(366);
		
		// THEN
		assertNull(departureDate);
	}
	
}
