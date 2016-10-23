package com.rumbo.favs.data.dao.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.rumbo.favs.business.bean.PassengerType;
import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.dao.IPassengerDiscountDao;
import com.rumbo.favs.data.entities.InfantPrice;
import com.rumbo.favs.data.entities.PassengerDiscount;

public class PassengerTypeDaoTest {

	@Test		
	public void testA() {
		
		IPassengerDiscountDao passengerDiscountDao = new PassengerDiscountDaoImpl();
		
		PassengerDiscount passengerDiscount = passengerDiscountDao.getDiscount(PassengerType.ADT);
		
		assertThat(passengerDiscount.getDiscount(),is(0f));
	}
	
	@Test		
	public void testB() {
		
		IPassengerDiscountDao passengerDiscountDao = new PassengerDiscountDaoImpl();
		
		PassengerDiscount passengerDiscount = passengerDiscountDao.getDiscount(PassengerType.CHD);
		
		assertThat(passengerDiscount.getDiscount(),is(33f));
	}
	
	@Test		
	public void testC() {
		
		IPassengerDiscountDao passengerDiscountDao = new PassengerDiscountDaoImpl();
		
		PassengerDiscount passengerDiscount = passengerDiscountDao.getDiscount(PassengerType.INF);
		
		assertThat(passengerDiscount.getDiscount(),is(0f));
	}
	
}
