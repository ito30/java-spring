package com.ito.out;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;

import com.ito.session.Session;
import com.snail.core.request.AbstractHttpRequest;
import com.snail.core.util.SoapUtil;

public class AbstractAbacusRequest extends AbstractHttpRequest {

	public static final String SEC = "sec";
	public static final String MES = "mes";
	public static final String NS = "ns";
	public static final String V3 = "v3";
			
	public static final String SEC_URI = "http://schemas.xmlsoap.org/ws/2002/12/secext";
	public static final String MES_URI = "http://www.ebxml.org/namespaces/messageHeader";
	public static final String NS_URI = "http://www.opentravel.org/OTA/2002/11";
	public static final String BFM_NS_URI = "http://www.opentravel.org/OTA/2003/05";
	public static final String EAB_V3_URI = "http://services.sabre.com/sp/eab/v3_2";
	public static final String PD_V3_URI = "http://services.sabre.com/sp/pd/v3_2";		
	public static final String OTA_AP_NS_URI = "http://webservices.sabre.com/sabreXML/2011/10";
	public static final String SC_NS_URI = "http://webservices.sabre.com/sabreXML/2003/07";
		
	private SOAPMessage soapMessage;
	private String uri;
	private String action;		
	private String conversationId;
	private String messageId;	
	private Session session;
	
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	// CONSTRUCTOR
	public AbstractAbacusRequest(String action){
		this.action = action;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return this.session;
	}
	
	// INIT ENVELOPE REQUEST
	public void initEnvelope(SOAPEnvelope env) throws Exception{
		env.addNamespaceDeclaration(SEC, SEC_URI);
		env.addNamespaceDeclaration(MES, MES_URI);		
	}
	
	// INIT HEADER REQUEST
	public void initHeaderMessage(SOAPElement header) throws Exception{	
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
		formatter.setLenient(false);
		String timeStamp = formatter.format(new Date());
		
		SOAPElement msgHeader = header.addChildElement("MessageHeader", MES);
		msgHeader.addAttribute(new QName( "id"), "1");
		msgHeader.addAttribute(new QName( "version"), "2.0");
		
		SOAPElement from = msgHeader.addChildElement("From", MES);
		from.addChildElement("PartyId", MES).addTextNode("tiket.com");
		from.addAttribute(new QName( "type"), "urn:x12.org:IO5:01");
		
		SOAPElement to = msgHeader.addChildElement("To", MES);
		to.addChildElement("PartyId", MES).addTextNode("webservices.sabre.com");
		to.addAttribute(new QName( "type"), "urn:x12.org:IO5:01");
		
		msgHeader.addChildElement("CPAId", MES).addTextNode("IPCC");
		msgHeader.addChildElement("ConversationId", MES).addTextNode(conversationId);
		msgHeader.addChildElement("Service", MES);
		msgHeader.addChildElement("Action", MES).addTextNode(action);
		
		SOAPElement msgData = msgHeader.addChildElement("MessageData", MES);
		msgData.addChildElement("MessageId", MES).addTextNode(messageId);
		msgData.addChildElement("Timestamp", MES).addTextNode(timeStamp);
		msgData.addChildElement("TimeToLive", MES).addTextNode(timeStamp);
		
		if (session != null) {
			SOAPElement security = header.addChildElement("Security", SEC);		
			security.addChildElement("BinarySecurityToken", SEC).addTextNode(session.getBinarySecurityToken());
		}
	}
	
	// INIT BODY REQUEST
	// Not use, Override, depend on each request
	public void initBodyMessage(SOAPElement body) throws Exception{
	}
	
	public SOAPMessage getMessage() throws Exception {
		if (soapMessage == null) {
			soapMessage = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
			SOAPPart part = soapMessage.getSOAPPart();
			
			// envelope
			SOAPEnvelope env = part.getEnvelope();
			initEnvelope(env);
			
			// header (WEB)
			SOAPElement header = env.getHeader();
			initHeaderMessage(header);
			
			// body (SES & SES1)
			SOAPElement body = env.getBody();
			initBodyMessage(body);
		}
		
		return soapMessage;
	}
	
	public HttpRequestBase generate() throws Exception {
		SOAPMessage soapMessage = getMessage();		
		String xml = SoapUtil.toString(soapMessage);
		StringEntity stringEntity = new StringEntity(xml, "UTF-8");
		stringEntity.setChunked(true);
		
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(stringEntity);
		httpPost.addHeader("Accept-Encoding", "gzip,deflate");
		httpPost.addHeader("Content-Type", "text/xml;charset=UTF-8");
		
		return httpPost;
	}
}
