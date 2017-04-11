package com.snail.core.scenario.inquiry;

import java.util.Calendar;
import java.util.Date;

import com.snail.core.fault.Fault;
import com.snail.core.fault.ValidationFault;
import com.snail.core.scenario.Inquirable;
import com.snail.core.util.DateUtil;
import com.snail.core.util.JsonHandler;
import com.snail.core.util.Validation;

public class SearchInquiry implements Inquirable {

	private String departure;
	private String arrival;
	private Date date;
	private Date returnDate;
	private int child;
	private int adult;
	private int infant;
	private String classSelect;

	// INIT
	@Override
	public void inquire(JsonHandler param) throws Fault {
		departure = param.getAsString("departure");
		arrival = param.getAsString("arrival");

		// Validation Class Select 
		classSelect = param.has("class_select") ? param.getAsString("class_select") : "";

		if (!departure.equalsIgnoreCase(arrival)) {
			date = Validation.date(param, "date", 700);
			adult = Validation.defaultInt(param, "adult", 0);
			child = Validation.defaultInt(param, "child", 0);
			infant = Validation.defaultInt(param, "infant", 0);

			if (infant > adult) {
				String message = "Infant must be same or less than adult";
				throw ValidationFault.create(message);
			}
			
			if(param.has("return_date")){
				returnDate = Validation.date(param, "return_date", 800);
			}
		} else {
			String message = "Departure can't be same with arrival";
			throw ValidationFault.create(message);
		}
	}
	
	// GET CLASS SELECT
	public String getClassSelect() {
		return classSelect;
	}


	public String getArrival() {
		return arrival;
	}

	public String getDeparture() {
		return departure;
	}

	// GET DATE
	public String getDate(String dateFormat) {
		return DateUtil.format(date, dateFormat);
	}
	
	// GET DATE
	public Date getDate() {
		return date;
	}

	// GET DATE AS CALENDAR
	public Calendar getDateAsCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	// GET AS CALENDAR RETURN DATE
	public Calendar getReturnDateAsCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(returnDate);
		return cal;
	}
	
	// GET RETURN DATE
	public Date getReturnDate() {
		return returnDate;
	}
	
	// GET RETURN DATE BY FORMAT
	public String getReturnDate(String dateFormat) {
		return DateUtil.format(returnDate, dateFormat);
	}

	public int getChild() {
		return child;
	}

	public int getAdult() {
		return adult;
	}

	public int getInfant() {
		return infant;
	}

	public boolean hasAdult() {
		return adult > 0;
	}

	public boolean hasChild() {
		return child > 0;
	}

	public boolean hasInfant() {
		return infant > 0;
	}
	
	public boolean isRoundTrip() {
		return returnDate != null;
	}
	
	public int getPaxCount() {
		return adult + child;
	}
}
