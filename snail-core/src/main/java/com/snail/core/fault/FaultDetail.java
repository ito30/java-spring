package com.snail.core.fault;

import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;

public class FaultDetail implements Deliverable {

	private int httpCode;
	private String errorCode;
	private String message;
	private String info;
	private String log;
	private String logID;
	
	// CONSTRUCTOR
	public FaultDetail(int httpCode) {
		this.httpCode = httpCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getLog() {
		return log;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setLogID(String logID){
		this.logID = logID;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

	public String getInfo() {
		return info;
	}
	
	public String getLogID(){
		return getLogID();
	}

	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("err_code", errorCode);		
		map.put("err_message", message);
		map.put("err_info", info);
		map.put("err_log", log);
		map.put("log_id", logID);
		return map;
	}

	// **
}
