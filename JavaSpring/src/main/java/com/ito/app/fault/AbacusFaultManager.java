package com.ito.app.fault;

import com.snail.core.fault.Fault;
import com.snail.core.fault.FaultDetail;
import com.snail.core.fault.FaultManager;

public class AbacusFaultManager extends FaultManager {
	
	// EXCEPTION
	public Fault exception(Exception e, Object source, String log){
		if(e instanceof Fault){
			return (Fault) e;
		} else {
			String message = e.getMessage();
			String info = e.getClass().getName();
			StackTraceElement[] stackTrace = e.getStackTrace();
			
			FaultDetail detail = new FaultDetail(200);
			detail.setErrorCode("#exception");
			detail.setMessage(message);
			detail.setInfo(info);
			if (log != null) {
				detail.setLog(log);
			}
			
			Fault fault = createFault(detail, source);
			fault.setStackTrace(stackTrace);
			return fault;
		}
	}
	
	// ISSUED
	public Fault issued(String bookingNo, Object source, String log){
		String message = bookingNo + " already issued";
		
		FaultDetail detail = new FaultDetail(200);
		detail.setErrorCode("#issued");
		detail.setMessage(message);
		if (log != null) {
			detail.setLog(log);
		}
		
		Fault fault = createFault(detail, source);
		return fault;
	}
	
	// NO TICKET
	public Fault noTicket (String select, Object source, String log){
		String message = select.toString() + " : ticket is not available, please try again later";
		
		FaultDetail detail = new FaultDetail(200);
		detail.setErrorCode("#no_ticket");
		detail.setMessage(message);
		if (log != null) {
			detail.setLog(log);
		}
		
		Fault fault = createFault(detail, source);
		return fault;
	}
	
	public Fault soapPenetration(String message, Object source, String log){
		FaultDetail detail = new FaultDetail(200);
		detail.setErrorCode("#penetration");
		detail.setMessage(message);
		detail.setInfo("soap");
		if (log != null) {
			detail.setLog(log);
		}
		
		Fault fault = createFault(detail, source);
		return fault;
	}
	
	public static class SingletonHolder {
		public static AbacusFaultManager INSTANCE = new AbacusFaultManager();
	}
	
	public static AbacusFaultManager getInstance(){
		return SingletonHolder.INSTANCE;
	}
	
	// SOAP
	public Fault unexpected(String message, Object source) {

		FaultDetail detail = new FaultDetail(200);
		detail.setErrorCode("#unexpected");
		detail.setMessage(message);

		Fault fault = createFault(detail, source);
		return fault;
	}
	
	// SOAP
	public Fault canceled(String message, Object source) {

		FaultDetail detail = new FaultDetail(200);
		detail.setErrorCode("#canceled");
		detail.setMessage(message);

		Fault fault = createFault(detail, source);
		return fault;
	}
	
	public Fault broken(String message, Object source) {

		FaultDetail detail = new FaultDetail(200);
		detail.setErrorCode("#broken");
		detail.setMessage(message);

		Fault fault = createFault(detail, source);
		return fault;
	}
	
	public Fault forbiddenAction(String message, Object source) {

		FaultDetail detail = new FaultDetail(200);
		detail.setErrorCode("#forbidden");
		detail.setMessage(message);

		Fault fault = createFault(detail, source);
		return fault;
	}
}
