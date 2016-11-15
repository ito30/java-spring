package com.ito.app.dto.search;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ito.bean.Upselling;
import com.snail.core.util.DateUtil;

public class Search {
	
	private String departure;
	private String arrival;
	private Date date;
	@JsonProperty ("return_date")
	private Date returnDate;
	private int adult;
	private int child;
	private int infant;	
	private String currency;
	@JsonProperty ("upselling")
	private List<Upselling> upSellings;
	@JsonProperty ("return_upselling")
	private List<Upselling> returnUpsellings;
	private boolean _debug;
	
	public Search() {}

	public Search(String departure, 
			String arrival, 
			Date date, 
			Date returnDate, 
			int adult, 
			int child, 
			int infant, 
			String currency,
			List<Upselling> upSellings, 
			List<Upselling> returnUpsellings, 
			boolean _debug) {
		super();
		this.departure = departure;
		this.arrival = arrival;
		this.date = date;
		this.returnDate = returnDate;
		this.adult = adult;
		this.child = child;
		this.infant = infant;
		this.currency = currency;
		this.upSellings = upSellings;
		this.returnUpsellings = returnUpsellings;
		this._debug = _debug;
	}
	
	public Date getReturnDate() {
		return returnDate;
	}
	
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public List<Upselling> getUpSellings() {
		return upSellings;
	}

	public void setUpSellings(List<Upselling> upSellings) {
		this.upSellings = upSellings;
	}

	public List<Upselling> getReturnUpsellings() {
		return returnUpsellings;
	}

	public void setReturnUpsellings(List<Upselling> returnUpsellings) {
		this.returnUpsellings = returnUpsellings;
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
	
	public boolean isRoundTrip() {
		return returnDate != null;
	}
	
	public String getDate(String dateFormat) {
		return DateUtil.format(date, dateFormat);
	}
	public List<Upselling> getUpsellings() {
		return upSellings;
	}
}
