package com.ito.out.session;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.util.EntityUtils;

import com.ito.bean.Session;
import com.ito.common.HttpManager;
import com.ito.common.SoapHandler;
import com.ito.config.SOAPConfig;
import com.snail.core.util.HttpResponseUtil;
import com.snail.core.util.SoapUtil;

public class SessionCreateExecution {
	
	private LinkedBlockingDeque<Session> sessions;
	private SOAPConfig config;
	
	public SessionCreateExecution() {
		config = new SOAPConfig();
		
		sessions = new LinkedBlockingDeque<Session>();
		config.setEpr("666");
		config.setIpcc("4ZB8");
		config.setPassword("COKLAT1");
		config.setUri("https://sws-tls.cert.sabre.com");
		config.setConversationId(UUID.randomUUID().toString());
	}
	
	public Session createNewSession() throws Exception {
		String binarySecurityToken = getBinarySecurityToken();
		Session _session = new Session(binarySecurityToken, config.getUri(), config.getIpcc()); 
		
		System.out.println("Bin :" + binarySecurityToken);
		
		return _session;
	}
	
	public SOAPConfig getConfig() {
		return config;
	}
	
	public Session getSession() throws Exception{
		//retrieve session is available
		Session session;
		
		while (!sessions.isEmpty()){
			session = sessions.pop();
			
			if(session.isValid()){
				return session;
			}
		}
		
		session = createNewSession();
		
		// create session
		return session;
	}
	
	// SAVE SESSION
	public boolean saveSession(Session session){
		if(session != null){
			sessions.push(session);
			
			return true;
		}
		return false;
	}
	
	public String getBinarySecurityToken() throws Exception {
		CloseableHttpResponse response = null;
		HttpRequestBase httpRequest = null;
		try {
			
			SessionCreateRequest request = new SessionCreateRequest(
					config.getEpr(), config.getPassword(), config.getIpcc(), config.getUri());
			String mid = UUID.randomUUID().toString();
			String cid = UUID.randomUUID().toString();
			request.setConversationId(cid);
			request.setMessageId(mid);
			request.setUri(config.getUri());						
			
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
