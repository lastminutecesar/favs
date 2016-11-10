package com.rumbo.favs.data.dao.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rumbo.favs.business.enums.PassengerType;
import com.rumbo.favs.data.dao.IPassengerDiscountDao;
import com.rumbo.favs.data.entities.PassengerDiscount;

public class PassengerTypeDaoTest {
	
	private static IPassengerDiscountDao passengerDiscountDao;
	
	@BeforeClass
	public static void initialize()
	{
		// GIVEN
		passengerDiscountDao = new PassengerDiscountDaoImpl();
	}

	@Test		
	public void testA() {
		
		// WHEN
		PassengerDiscount passengerDiscount = passengerDiscountDao.getDiscountByPaxType(PassengerType.ADT);
		
		// THEN
		assertThat(passengerDiscount.getDiscount(),is(0f));
	}
	
	@Test		
	public void testB() {
		
		// WHEN
		PassengerDiscount passengerDiscount = passengerDiscountDao.getDiscountByPaxType(PassengerType.CHD);
		
		// THEN
		assertThat(passengerDiscount.getDiscount(),is(33f));
	}
	
	@Test		
	public void testC() {
		
		// WHEN
		PassengerDiscount passengerDiscount = passengerDiscountDao.getDiscountByPaxType(PassengerType.INF);
		
		// THEN
		assertThat(passengerDiscount.getDiscount(),is(0f));
	}
	
}
