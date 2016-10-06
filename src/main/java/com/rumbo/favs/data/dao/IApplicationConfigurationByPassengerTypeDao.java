package com.rumbo.favs.data.dao;

import com.rumbo.favs.business.enums.configuration.ApplicationConfigurationType;
import com.rumbo.favs.data.entities.ApplicationConfigurationByPassengerType;

/**
 * ApplicationConfigurationByPassengerType DAO interfaz
 * Manage discounts allowed by passenger type
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
public interface IApplicationConfigurationByPassengerTypeDao {

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String STATUS = "status";
	public static final String ADT = "adt";
	public static final String CHD = "chd";
	public static final String INF = "inf";
	
	/**
	 * Get ApplicationConfigurationByPassengerType by 
	 * an applicationConfigurationType property
	 * 
	 * @param ApplicationConfigurationType property
	 * @return ApplicationConfigurationByPassengerType
	 */
	public ApplicationConfigurationByPassengerType getApplicationConfigurationByName(ApplicationConfigurationType property);
	
}
