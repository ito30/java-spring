package com.snail.core.scenario.delivery;

import java.util.Date;

import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.beans.TAvailability;
import com.snail.core.beans.TFareInfo;
import com.snail.core.beans.TTrip;



/**
 * 
 * @author iman
 * 
 */
public class BookingDelivery implements Deliverable {

	private String bookingCode;
	private String invoiceNo;
	private Date expired;
	private String currency;
	private BookingSummary summary;
	private TAvailability av;
	private String log;	
	private TAvailability dep;
	private TAvailability ret;
	private TFareInfo fareInfo; 
	private TTrip tripSummary;
	
	// SET LOG	
	public void setLog(String log) {
		this.log = log;
	}

	// SET BOOKING CODE
	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}

	// SET INVOICE NO
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	// SET CURRENCY
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	// SET AV
	public void setAv(TAvailability av) {
		this.av = av;
	}
	
	// SET AV
	public void setDep(TAvailability dep) {
		this.dep = dep;
	}
	
	// SET AV
	public void setRet(TAvailability ret) {
		this.ret = ret;
	}

	// SET EXPIRED
	public void setExpired(Date expired) {
		this.expired = expired;
	}

	// SET SUMMARY
	public void setSummary(BookingSummary summary) {
		this.summary = summary;
	}
	
	// SET FAREINFO
	public void setFareInfo(TFareInfo fareInfo) {
		this.fareInfo = fareInfo;
	}
	
	// SET TRIP SUMMARY
	public void setTrip(TTrip tripSummary) {
		this.tripSummary = tripSummary;
	}
	
	// DELIVER
	@Override
	public DeliveryMap deliver() {

		DeliveryMap map = new DeliveryMap();
		map.put("booking_no", bookingCode);
		map.put("invoice_no", invoiceNo);
		map.put("booking_expired", expired, "yyyy-MM-dd HH:mm:ss");
		map.put("currency", currency);
		map.put("booking_summary", summary);		
		map.put("trip", tripSummary);
		map.put("availability", av);
		map.put("log_folder", log);		map.put("depart", dep);
		map.put("return", ret);
		map.put("summary_fare_info", fareInfo);		return map;
	}

}
