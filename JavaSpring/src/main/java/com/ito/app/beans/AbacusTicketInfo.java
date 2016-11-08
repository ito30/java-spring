package com.ito.app.beans;

import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;

public class AbacusTicketInfo implements Deliverable {

	private String ticketNumber;
	private String paxName;
	private String title;
	
	public AbacusTicketInfo(String ticketNumber, String paxName, String title) {
		super();
		this.ticketNumber = ticketNumber;
		this.paxName = paxName;
		this.title = title;
	}
	
	public AbacusTicketInfo(String paxName, String title) {
		super();
		this.paxName = paxName;
		this.title = title;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getPaxName() {
		return paxName;
	}

	public void setPaxName(String paxName) {
		this.paxName = paxName;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("name", title + paxName);
		map.put("ticket_number", ticketNumber);		
		return map;
	}
}
