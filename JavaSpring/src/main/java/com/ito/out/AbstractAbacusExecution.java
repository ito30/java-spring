package com.ito.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.util.EntityUtils;

import com.ito.App;
import com.ito.out.session.SessionCreateExecution;
import com.snail.core.fault.Fault;
import com.snail.core.util.FileUtil;
import com.snail.core.util.SoapDebugUtil;

import com.ito.app.beans.AbacusSession;
import com.ito.app.controller.ServiceController;
import com.ito.app.fault.AbacusFaultManager;
import com.ito.app.request.AbstractAbacusRequest;
import com.snail.core.util.HttpManager;
import com.snail.core.util.SoapHandler;
import com.snail.core.util.SoapUtil;

public abstract class AbstractAbacusExecution {

	protected ServiceController controller;
	protected String debugPath;
	
	public void run(String debugPath, SessionCreateExecution _session) throws Fault {
		CloseableHttpResponse response = null;
		HttpRequestBase httpRequest = null;
		try {
			Session session = _session.getSession();
			String uri = App.config.getEnv_target().getEnv().getUrl();			
			
			// soap request
			AbstractAbacusRequest soapRequest = createRequest();
			String mid = UUID.randomUUID().toString();			
			soapRequest.setConversationId(UUID.randomUUID().toString());
			soapRequest.setMessageId(mid);
			soapRequest.setUri(uri);			
			soapRequest.setSession(session);
			
			httpRequest = soapRequest.generate();
			
			if(_session.isDebug())
				printResult(debugPath,soapRequest.getMessage(), "_request.xml");
			
			response = HttpManager.getInstance().run(
					httpRequest, new BasicCookieStore());

			HttpEntity entity = response.getEntity();
			
			String string = IOUtils.toString(entity.getContent());			

			SOAPMessage soapResult = SoapUtil.toSoapMessage(
					SOAPConstants.SOAP_1_1_PROTOCOL, string);

			if(_session.isDebug())
				printResult(debugPath,soapResult, "_response.xml");
			
			// check fault
			SOAPBody body = soapResult.getSOAPBody();
			SOAPFault fault = body.getFault();
			
			if (fault == null) {
				SoapHandler bodyHandler = new SoapHandler(body);
				SoapHandler exceptionStatusSOAP = bodyHandler
						.find("ExceptionStatus");

				if (exceptionStatusSOAP == null) {
					// check abacus error
					List<SoapHandler> errors = bodyHandler.findAsList("Error");
					if( errors == null || errors.size() == 0 )
					{
						List<SoapHandler> errors2 = bodyHandler.findAsList("stl:Error");
						List<SoapHandler> errors3 = bodyHandler.findAsList("ns2:Error");
						errors2.addAll(errors3);
																
						if( errors2 == null || errors2.size() == 0 )
						{								
							onRunDone(bodyHandler);
						} else {
							List<String> errorMsgs = new ArrayList<String>();
							for( SoapHandler error2 : errors2 )
							{
								List<SoapHandler> messages = error2.findAsList("stl:Message");
								List<SoapHandler> messages2 = error2.findAsList("ns2:Message");
								messages.addAll(messages2);
								
								for( SoapHandler message : messages )
								{
									String msg = message.getText();
									if( !isExists(msg, errorMsgs) )
										errorMsgs.add(msg.trim());
								}
							}
							String errormsg = toErrorString(errorMsgs);							
							if ( errormsg.contains("Specified HaltOnStatus Received - Processing Aborted") )															
								onRunDone(bodyHandler);								
							else
								throw AbacusFaultManager.getInstance().soapPenetration(errormsg,this, "");								
						}
					}
					else
					{
						List<String> errorMsgs = new ArrayList<String>();
						for( SoapHandler error : errors )
						{																																
							if( error.getAttributeValueAsString("Code").equals("MSG") ||
									error.getAttributeValueAsString("Code").equals("IS") ||
									error.getAttributeValueAsString("Code").equals("ERR") ||
									error.getAttributeValueAsString("Code").equals("MIP") )
							{
								String errorMessage2 = error.getAttributeValueAsString("ShortText");
								if( !isExists(errorMessage2, errorMsgs) )
									errorMsgs.add(errorMessage2.trim());
							}
															
							List<SoapHandler> messages = bodyHandler.findAsList("Message");
																																														
							if( messages != null && messages.size() > 0 )
							{
								for( SoapHandler message : messages )
								{
									String msg = message.getText();
									if( !isExists(msg, errorMsgs) )
										errorMsgs.add(msg.trim());
								}
							}	
							else
							{
								String errorMessage3 = error.getText();
								
								if( !isExists(errorMessage3, errorMsgs) )
									errorMsgs.add(errorMessage3.trim());
							}
						}
						String errormsg = toErrorString(errorMsgs);							
						if ( errormsg.contains("Specified HaltOnStatus Received - Processing Aborted") )							
							onRunDone(bodyHandler);							
						else
							throw AbacusFaultManager.getInstance().soapPenetration(errormsg,this, "");
					}
					
				} else {
					String message = exceptionStatusSOAP
							.findAsString("MessageText");
					throw AbacusFaultManager.getInstance().soapPenetration(message,
							this, "");
				}
			} else {
				String message = fault.getFaultString();
				
				// Detect invalid session 
				if ( message.contains("Invalid or Expired binary security token") ) {
					session.notValid();
					session.stopTimer();
				}
				throw AbacusFaultManager.getInstance().soapPenetration(message, this, "");
			}
			_session.saveSession(session);
										
		} catch (Exception e) {
			throw AbacusFaultManager.getInstance().exception(e, this, debugPath);
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
	
	public void printResult(String path,SOAPMessage soapMessage, String status){
		
		FileUtil.createDirectory(path);
		SoapDebugUtil.writeToFile(soapMessage, path+"/"+System.currentTimeMillis()+"_"+this.getClass().getSimpleName() + status);
	}
	
	private boolean isExists(String input, List<String> list)
	{
		if( input.trim().equals("") )
			return true;
		for(String l : list)
		{
			if( l.equals(input) )
				return true;
		}
		return false;
	}
	
	private String toErrorString(List<String> list)
	{
		String result = "";
		for(String l : list)
		{
			result += l + ". ";
		}
		return result.trim();
	}	

	public abstract AbstractAbacusRequest createRequest() throws Exception;

	public abstract void onRunDone(SoapHandler result) throws Exception;
}
