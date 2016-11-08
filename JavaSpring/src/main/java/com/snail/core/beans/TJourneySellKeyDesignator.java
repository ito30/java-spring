package com.snail.core.beans;

import java.text.ParseException;
import java.util.Date;

import com.snail.core.util.DateUtil;

public class TJourneySellKeyDesignator {

	private String departure;
	private String arrival;
	private Date std;
	private Date sta;
	private TFlightDesignator flightDesignator;

	public static TJourneySellKeyDesignator create(String journeySellKey) throws ParseException {

		String[] journeySellKeyArr = journeySellKey.split("~");
		TJourneySellKeyDesignator designator = new TJourneySellKeyDesignator();
		TFlightDesignator flightDesignator = new TFlightDesignator();
		flightDesignator.setCarrierCode(journeySellKeyArr[0].trim());
		flightDesignator.setFlightNumber(journeySellKeyArr[1].trim());
		designator.setFlightDesignator(flightDesignator);
		designator.setDeparture(journeySellKeyArr[4]);
		designator.setSTD(DateUtil.toDate(journeySellKeyArr[5], "MM/dd/yyyy HH:mm"));
		designator.setArrival(journeySellKeyArr[6]);
		designator.setSTA(DateUtil.toDate(journeySellKeyArr[7], "MM/dd/yyyy HH:mm"));

		return designator;
	}

	public String getDeparture() {
		return departure;
	}


	public void setDeparture(String departure) {
		this.departure = departure;
	}


	public String getArrival() {
		return arrival;
	}


	public void setArrival(String arrival) {
		this.arrival = arrival;
	}


	public Date getSTD() {
		return std;
	}


	public void setSTD(Date std) {
		this.std = std;
	}


	public Date getSTA() {
		return sta;
	}


	public void setSTA(Date sta) {
		this.sta = sta;
	}
	
	// GET DEPARTURE DATE
	public String getSTD(String dateFormat) {
		return DateUtil.format(std, dateFormat);
	}
	
	// GET ARRIVAL DATE
	public String getSTA(String dateFormat) {
		return DateUtil.format(sta, dateFormat);
	}


	public TFlightDesignator getFlightDesignator() {
		return flightDesignator;
	}


	public void setFlightDesignator(TFlightDesignator flightDesignator) {
		this.flightDesignator = flightDesignator;
	}
	
	
}
