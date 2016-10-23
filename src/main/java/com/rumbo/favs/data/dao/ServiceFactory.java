package com.rumbo.favs.data.dao;

import com.rumbo.favs.data.dao.impl.AirportDaoImpl;
import com.rumbo.favs.data.dao.impl.ApplicationConfigurationByPassengerTypeDaoImpl;
import com.rumbo.favs.data.dao.impl.DepartureDateDaoImpl;
import com.rumbo.favs.data.dao.impl.PassengerDiscountDaoImpl;
import com.rumbo.favs.data.dao.impl.FlightDaoImpl;
import com.rumbo.favs.data.dao.impl.InfantPriceDaoImpl;

/**
 * Auxiliar class to math functions
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public class ServiceFactory {

	public static IFlightDao getFlightDaoFactory() {
		return new FlightDaoImpl();
	}
	
	public static IInfantPriceDao getInfantPricesDaoFactory() {
		return new InfantPriceDaoImpl();
	}
	
	public static IApplicationConfigurationByPassengerTypeDao getApplicationConfigurationByPassengerTypeDaoFactory() {
		return new ApplicationConfigurationByPassengerTypeDaoImpl();
	}
	
	public static IDepartureDateDao getDaysToDepartureDateDaoFactory() {
		return new DepartureDateDaoImpl();
	}
	
	public static IPassengerDiscountDao getDiscountByPassengerTypeDaoFactory() {
		return new PassengerDiscountDaoImpl();
	}
	
	public static IAirportDao getAirportDaoFactory() {
		return new AirportDaoImpl();
	}
	
}
