package com.snail.core.fault;

public class FaultManager {

	// CONSTRUCTOR
	protected FaultManager() {

	}

	protected Fault createFault(FaultDetail detail, Object source) {
		return new Fault(detail, source);
	}

}