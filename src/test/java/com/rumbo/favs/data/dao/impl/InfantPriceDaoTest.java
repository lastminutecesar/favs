package com.rumbo.favs.data.dao.impl;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.rumbo.favs.data.dao.IInfantPriceDao;
import com.rumbo.favs.data.entities.InfantPrice;

public class InfantPriceDaoTest {

	@Test		
	public void testA() {
		
		IInfantPriceDao infantPriceDao = new InfantPriceDaoImpl();
		
		InfantPrice infantPrice = infantPriceDao.getInfantPriceByAirline("IB");
		
		assertThat(infantPrice.getPrice(),is(10f));
	}
	
	@Test		
	public void testB() {
		
		IInfantPriceDao infantPriceDao = new InfantPriceDaoImpl();
		
		InfantPrice infantPrice = infantPriceDao.getInfantPriceByAirline("U2");
		
		assertThat(infantPrice.getPrice(),is(19.90f));
	}
	
}
