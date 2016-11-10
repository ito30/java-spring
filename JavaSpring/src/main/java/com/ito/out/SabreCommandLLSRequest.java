package com.ito.out;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;

public class SabreCommandLLSRequest extends AbstractAbacusRequest {
	
private String command;
private static final String VERSION = "1.6.1";	

// CONSTRUCTOR
public SabreCommandLLSRequest( String command) {
	super("SabreCommandLLSRQ");
	this.command = command;
}

@Override
public void initEnvelope(SOAPEnvelope env) throws Exception {
	super.initEnvelope(env);
	env.addNamespaceDeclaration(NS, SC_NS_URI);
}

// INIT BODY
@Override
public void initBodyMessage(SOAPElement body) throws Exception {			
	
	body.addChildElement("SabreCommandLLSRQ", NS)
	.addAttribute(new QName( "Version"), VERSION)		
	.addChildElement("Request", NS)
	.addAttribute(new QName( "Output"), "SCREEN")	
	.addAttribute(new QName( "CDATA"), "true")	
	.addAttribute(new QName( "ReturnHostCommand"), "true")
	.addChildElement("HostCommand", NS)
	.addTextNode(command);											
}

}