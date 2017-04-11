package com.snail.core.beans;

import java.util.Date;
import java.util.List;

import com.snail.core.util.DateUtil;

public class TAvailability2 implements Deliverable {

	private String airlineFamily;
	private int international;
	private int promo;
	private String availabilityKey;
	private List<? extends TSegment> listSegment;
	private String departure;
	private String arrival;
	private Date std;
	private Date sta;

	public String getDeparture() {
		return departure;
	}

	public String getArrival() {
		return arrival;
	}

	public String getAirlineFamily() {
		return airlineFamily;
	}

	public String getSta(String dateFormat) {
		return DateUtil.format(sta, dateFormat);
	}

	public String getStd(String dateFormat) {
		return DateUtil.format(std, dateFormat);
	}

	public boolean isConnectingFlight() {
		if (listSegment != null) {
			return listSegment.size() > 1;
		}

		return false;
	}

	public void setKey(String key) {
		this.availabilityKey = key;
	}

	public void setServiceClass(String serviceClass) {
		for (TSegment segment : listSegment) {
			segment.setServiceClass(serviceClass);
		}
	}

	public void setAirlineFamily(String airlineFamily) {
		this.airlineFamily = airlineFamily;
	}

	public void setListSegment(List<? extends TSegment> listSegment) {
		this.listSegment = listSegment;

		StringBuilder keyBuilder = new StringBuilder();
		int listSize = listSegment.size();
		for (int i = 0; i < listSize; i++) {
			TSegment segment = listSegment.get(i);

			if (i == 0) {
				departure = segment.getDeparture();
				std = segment.getSTD();
			}

			if (i == listSize - 1 || listSize == 1) {
				arrival = segment.getArrival();
				sta = segment.getSTA();
			}

			TFlightDesignator designator = segment.getDesignator();

			keyBuilder.append(designator.getCarrierCode());
			keyBuilder.append("~");
			keyBuilder.append(designator.getFlightNumber());
			keyBuilder.append("~~");
		}

		this.availabilityKey = "~" + departure + "~" + arrival + "~~"
				+ DateUtil.format(std, "yyyyMMdd") + "~~"
				+ keyBuilder.toString();
	}

	public String getAvKey() {
		return availabilityKey;
	}

	public List<? extends TSegment> getListSegment() {
		return listSegment;
	}

	public boolean equals(TAvailability2 av) {
		String avKey = av.getAvKey();
		return equals(avKey);
	}

	public boolean equals(String avKey) {
		return availabilityKey.equalsIgnoreCase(avKey);
	}

	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("availability_key", availabilityKey);
		map.put("segments", listSegment);
		map.put("is_international", international);
		map.put("is_promo", promo);
		map.put("airline_family", airlineFamily);

		return map;
	}

}
