package com.snail.core.fault;

public class ValidationFault {

	public static Fault create(String message) {
		FaultDetail detail = new FaultDetail(403);
		detail.setErrorCode("#validation");
		detail.setMessage(message);

		Fault fault = new Fault(detail);
		return fault;
	}

}

