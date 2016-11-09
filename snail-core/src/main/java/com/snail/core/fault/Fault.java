package com.snail.core.fault;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;

public final class Fault extends Exception implements Deliverable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int counter;
	private FaultDetail detail;
	private String source;

	// CONSTRUCTOR
	protected Fault(FaultDetail detail) {
		super(detail.getMessage());
		this.detail = detail;
	}

	protected Fault(FaultDetail detail, Object object) {
		this(detail);

		this.source = object.getClass().getName();
	}

	// GET DETAIL
	public FaultDetail getDetail() {
		return detail;
	}

	// GET STRING
	public String getStackTraceString() {
		StringWriter stringWriter = null;
		PrintWriter printWriter = null;

		try {
			stringWriter = new StringWriter();
			printWriter = new PrintWriter(stringWriter);
			printStackTrace(printWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			return "problem when get stack trace";
		} finally {
			if (stringWriter != null) {
				try {
					stringWriter.close();
				} catch (IOException e) {
				}

			}

			if (printWriter != null) {
				printWriter.close();
			}
		}

	}
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	// DELIVER
	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = detail.deliver();
		map.put("err_source", source);
		map.put("err_counter", counter);
		return map;
	}

}

