package com.ito.out;

import com.ito.common.SoapHandler;

public class SabreCommandLLSExecution extends AbstractAbacusExecution {

	private String command;
	@Override
	public AbstractAbacusRequest createRequest() throws Exception {		
		return new SabreCommandLLSRequest(command);
	}

	public SabreCommandLLSExecution(String command) {
		super();
		this.command = command;	
	}

	@Override
	public void onRunDone(SoapHandler result) throws Exception {
		
	}
}