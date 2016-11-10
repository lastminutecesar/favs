package com.rumbo.favs.data.dao.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.entities.InfantPrice;

public class InfantPriceDaoTest {
	
	private static IInfantPriceDao infantPriceDao;
	
	@BeforeClass
	public static void initialize()
	{
		// GIVEN
		infantPriceDao = new InfantPriceDaoImpl();
	}

	@Test		
	public void testA() {
		
		// WHEN
		InfantPrice infantPrice = infantPriceDao.getDiscountByAirline("IB");
		
		// THEN
		assertThat(infantPrice.getPrice(),is(10f));
	}
	
	@Test		
	public void testB() {
		
		// WHEN
		InfantPrice infantPrice = infantPriceDao.getDiscountByAirline("U2");
		
		// THEN
		assertThat(infantPrice.getPrice(),is(19.90f));
	}
	
	@Test		
	public void testC() {
		
		// WHEN
		InfantPrice infantPrice = infantPriceDao.getDiscountByAirline("I2");
		
		// THEN
		assertNull(infantPrice);
	}
	
}
