package com.rumbo.favs.data.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Groups days to departure date xmls tags to work with jaxb and dom
 * 
 * @author  ccabrerizo
 * @version 1.0
 * @since   2016-10-07 
 */
@XmlRootElement(name = "daysToDepartureDateGroup")
@XmlAccessorType (XmlAccessType.FIELD)
public class DaysToDepartureDateGroup {

	@XmlElement(name = "daysToDepartureDate")
	List<DaysToDepartureDate> daysToDepartureDateGroup = new ArrayList<>();
	
	public DaysToDepartureDateGroup() {
		super();
	}

	public DaysToDepartureDateGroup(List<DaysToDepartureDate> daysToDepartureDateGroup) {
		super();
		this.daysToDepartureDateGroup = daysToDepartureDateGroup;
	}

	public List<DaysToDepartureDate> getDaysToDepartureDateGroup() {
		return daysToDepartureDateGroup;
	}

	public void setDaysToDepartureDateGroup(List<DaysToDepartureDate> daysToDepartureDateGroup) {
		this.daysToDepartureDateGroup = daysToDepartureDateGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((daysToDepartureDateGroup == null) ? 0 : daysToDepartureDateGroup.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "DaysToDepartureDateGroup [daysToDepartureDateGroup=" + daysToDepartureDateGroup + "]";
	}
	
}
