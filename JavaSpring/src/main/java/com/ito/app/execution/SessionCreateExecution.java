package com.ito.app.execution;

import java.io.IOException;
import java.util.UUID;

import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.util.EntityUtils;

import com.ito.app.controller.ServiceController;
import com.ito.app.request.SessionCreateRequest;
import com.snail.core.util.HttpManager;
import com.snail.core.util.HttpResponseUtil;
import com.snail.core.util.SoapHandler;
import com.snail.core.util.SoapUtil;

public class SessionCreateExecution {

	public static final int TIMEOUT = 150 * 1000;	
	private ServiceController controller;
	
	public SessionCreateExecution(ServiceController controller)
	{
		this.controller = controller;
	}
	
	public String getBinarySecurityToken() throws Exception {
		CloseableHttpResponse response = null;
		HttpRequestBase httpRequest = null;
		try {
			
			String epr = controller.getEpr();
			String password = controller.getPassword();
			String ipcc = controller.getIpcc();
	
			String uri = controller.getUri();
			
			SessionCreateRequest request = new SessionCreateRequest(epr, password, ipcc, uri);
			String mid = UUID.randomUUID().toString();
			String cid = UUID.randomUUID().toString();
			request.setConversationId(cid);
			request.setMessageId(mid);
			request.setUri(uri);						
			
			httpRequest = request.generate();						
			
			response = HttpManager.getInstance().run(
					httpRequest, new BasicCookieStore());
			
			String resultString = HttpResponseUtil.getString(response);
			SOAPMessage result = SoapUtil.toSoapMessage(
					SOAPConstants.SOAP_1_1_PROTOCOL, resultString);
						 
						
			SOAPHeader soapHeader = result.getSOAPHeader();
			
			SoapHandler header = new SoapHandler(soapHeader);
										
			return header.findAsString("wsse:BinarySecurityToken");
		} finally {
			
			onCleaning(httpRequest, response);
		}
	}
		
	public void onCleaning(HttpRequestBase httpRequest,
			CloseableHttpResponse response) {
		// http request
		if (httpRequest != null) {
			httpRequest.releaseConnection();
		}
		
		// response
		if (response != null) {
			try {			
				EntityUtils.consume(response.getEntity());
				response.close();
			} catch (IOException e) {
				// do nothing
			}
		}
	}
	

}
