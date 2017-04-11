package com.snail.core.scenario.delivery;

import java.util.Date;

import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.beans.TJourney;

public class BookingDelivery2 implements Deliverable {

	private String bookingCode;
	private String invoiceNo;
	private Date expired;

	private String currency;

	private BookingSummary summary;
	private TJourney journey;

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

	// SET JOURNEY
	public void setJourney(TJourney journey) {
		this.journey = journey;
	}

	// SET EXPIRED
	public void setExpired(Date expired) {
		this.expired = expired;
	}

	// SET SUMMARY
	public void setSummary(BookingSummary summary) {
		this.summary = summary;
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
		map.put("journey", journey);

		return map;
	}

	// INSTRUCTION
	// public static Instruction instruction() {
	// Instruction doc = new Instruction();
	// doc.put("booking_no");
	// doc.put("invoice_no", "if any");
	// doc.putDate("booking_expired", "yyyy-MM-dd HH:mm:ss");
	// doc.put("currency");
	// doc.put("booking_summary", BookingSummary.instruction());
	// doc.put("booking_ssr", BookingSSR.instruction());
	// doc.put("booking_journey", BookingJourney.documentation());
	//
	// return doc;
	//
	// }

}
