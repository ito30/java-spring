package com.ito.app.beans;

import com.snail.core.beans.DeliveryMap;
import com.snail.core.scenario.delivery.BookingDelivery;

public class AbacusBookingDelivery extends BookingDelivery  {
	
	private String debugPath;
	
	public void setDebugPath(String debugPath) {
		this.debugPath = debugPath;
	}
	
	// DELIVER
	@Override
	public DeliveryMap deliver() {
		DeliveryMap result = super.deliver();
		result.put("log_folder", debugPath);
		return result;
	}
}
