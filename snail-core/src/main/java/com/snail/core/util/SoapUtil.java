package com.snail.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;

public class SoapUtil {

	// TO STRING
	public static String toString(SOAPMessage soapMessage) throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		soapMessage.writeTo(out);
		String strMsg = new String(out.toByteArray());
		return strMsg;
	}

	// TO SOAP MESSAGE
	public static SOAPMessage toSoapMessage(String protocol, String xml)
			throws Exception {
		MessageFactory factory = MessageFactory.newInstance(protocol);
		SOAPMessage message = factory
				.createMessage(
						new MimeHeaders(),
						new ByteArrayInputStream(xml.getBytes(Charset
								.forName("UTF-8"))));
		return message;
	}

	public static SOAPMessage toSoapMessage(String xml) throws Exception {
		return toSoapMessage(SOAPConstants.SOAP_1_1_PROTOCOL, xml);
	}
}