package com.ito.app.model;

import java.util.Date;
import java.util.List;

import com.ito.app.beans.Upselling;
import com.snail.core.util.DateUtil;

public class Search {
	
	private String departure;
	private String arrival;
	private Date date;
	private int adult;
	private int child;
	private int infant;	
	private List<Upselling> upSellings;
	private boolean _debug;
	
	public Search() {}
	
	public Search(
			String departure, 
			String arrival, 
			Date date, 
			int adult, 
			int child, 
			int infant, 
			boolean _debug) {
		this.departure = departure;
		this.arrival = arrival;
		this.date = date;
		this.adult = adult;
		this.child = child;
		this.infant = infant;
		this._debug = _debug;
	}

	public String getDeparture() {
		return departure;
	}

	public String getArrival() {
		return arrival;
	}

	public Date getDate() {
		return date;
	}

	public int getAdult() {
		return adult;
	}

	public int getChild() {
		return child;
	}

	public int getInfant() {
		return infant;
	}

	public boolean is_debug() {
		return _debug;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAdult(int adult) {
		this.adult = adult;
	}

	public void setChild(int child) {
		this.child = child;
	}

	public void setInfant(int infant) {
		this.infant = infant;
	}

	public void set_debug(boolean _debug) {
		this._debug = _debug;
	}

	public boolean hasInfant() {
		
		return infant > 0;
	}

	public boolean hasChild() {
		return child > 0;
	}

	public boolean hasAdult() {
		return adult > 0;
	}

	public int getPaxCount() {
		
		return adult + child;
	}
	public String getDate(String dateFormat) {
		return DateUtil.format(date, dateFormat);
	}
	public List<Upselling> getUpsellings() {
		return upSellings;
	}
}
