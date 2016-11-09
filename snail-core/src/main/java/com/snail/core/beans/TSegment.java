package com.snail.core.beans;

import java.util.Calendar;
import java.util.Date;

import com.snail.core.util.DateUtil;

public class TSegment implements Deliverable {

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private TFlightDesignator designator;
	private String departure;
	private String arrival;
	private Date std;
	private Date sta;
	private String airline;
	private String serviceClass;
	private int promo;
	private int seatCount;
	private String aircraftType;

	// CONSTRUCTOR
	public TSegment() {
		this.promo = 0;
		this.seatCount = 0;

	}
	
	// SET FLIGHT CLASS
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	// SET FLIGHT DESIGNATOR
	public void setDesignator(TFlightDesignator designator) {
		this.designator = designator;
	}

	// SET DEPARTURE
	public void setDeparture(String departure) {
		this.departure = departure;
	}

	// SET AIRLINE
	public void setAirline(String airline) {
		this.airline = airline;
	}

	// SET ARRIVAL
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	// GET SERVICE CLASS
	public String getServiceClass() {
		return serviceClass;
	}
	
	// GET AIRCRAFT TYPE
	public String getAircraftType() {
		return aircraftType;
	}

	// SET STD
	public void setAircraftType(String aircraftType) {
		this.aircraftType = aircraftType;
	}
	
	// SET STD
	public void setSTD(Date departureDate) {
		this.std = departureDate;
	}

	// SET STD
	public void setSTD(Calendar std) {
		setSTD(std.getTime());
	}

	// SET ARRIVAL TIME
	public void setSTA(Date sta) {
		this.sta = sta;
	}

	// SET STA
	public void setSTA(Calendar sta) {
		setSTA(sta.getTime());
	}

	// GET DEPARTURE
	public String getDeparture() {
		return departure;
	}

	// GET ORIGIN
	public String getOrigin() {
		return departure;
	}

	// GET DESTINATION
	public String getDestination() {
		return arrival;
	}

	// GET ARRIVAL
	public String getArrival() {
		return arrival;
	}

	// GET FLIGHT CLASS
	public String getFlightClass() {
		return serviceClass;
	}

	// GET ARRIVAL TIME
	public Date getSTA() {
		return sta;
	}

	// GET STA CALENDAR
	public Calendar getSTACalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(sta);
		return cal;
	}

	// GET ARRIVAL DATE
	public String getSTA(String dateFormat) {
		return DateUtil.format(sta, dateFormat);
	}

	// GET DEPARTURE TIME
	public Date getSTD() {
		return std;
	}

	// GET STD CALENDAR
	public Calendar getSTDCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(std);
		return cal;
	}

	// GET DEPARTURE DATE
	public String getSTD(String dateFormat) {
		return DateUtil.format(std, dateFormat);
	}

	// GET FLIGHT DESIGNATOR
	public TFlightDesignator getDesignator() {
		return designator;
	}

	// GET FLIGHT DESIGNATOR AS STRING
	public String getFlightDesignatorAsString() {
		return designator.toString();
	}

	// IS SAME FLIGHT
	public boolean isSameFlight(String string) {
		return designator.equals(TFlightDesignator.create(string));
	}

	// IS SAME FLIGHT
	public boolean isSameFlight(TFlightDesignator flight) {
		return designator.equals(flight);
	}
	
	// NUGROHO.H Add [2014-12-17]
	// SET SEAT COUNT
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}	

	public int getSeatCount() {
		return seatCount;
	}
	
	// NUGROHO.H Add [2015-01-28]
	// GET FLIGHT DESIGNATOR AS STRING
	public String getFlightDesignatorAsCleanString() {
		return designator.toCleanString();
	}

	// CONVERT
	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("flight_no", designator.toString());
		map.put("dep_airport", departure);
		map.put("arr_airport", arrival);
		map.put("dep_time_full", std, DATE_FORMAT);
		map.put("arr_time_full", sta, DATE_FORMAT);
		map.put("class", serviceClass);
		map.put("airline", airline);
		// NUGROHO.H Add [2014-12-02]
		map.put("is_promo", promo);
		
		// NUGROHO.H Add -1 means A:seat available [2014-12-18]
		if(seatCount > 0){
			map.put("seat_count", seatCount);
		}
		//MARTINA Set aircraft type [2015-02-25]
		map.put("aircraft_type", aircraftType);
		return map;
	}

	public void setPromo(int promo) {
		this.promo = promo;
	}
	
	public int getPromo() {
		return this.promo;
	}
	

}