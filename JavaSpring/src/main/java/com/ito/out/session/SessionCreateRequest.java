package com.ito.out.session;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;

import com.ito.out.AbstractAbacusRequest;

public class SessionCreateRequest extends AbstractAbacusRequest {

	private String epr;
	private String password;
	private String ipcc;
	
	public SessionCreateRequest(String epr, String password, String ipcc, String uri) {
		super("SessionCreateRQ");
		this.epr = epr;
		this.password = password;
		this.ipcc = ipcc;
		
	}
	
	@Override
	public void initEnvelope(SOAPEnvelope env) throws Exception {

		super.initEnvelope(env);
		env.addNamespaceDeclaration(NS, NS_URI);
	}
	
	// INIT HEADER
	public void initHeaderMessage(SOAPElement header) throws Exception {
		super.initHeaderMessage(header);
		SOAPElement security = header.addChildElement("Security", SEC);
		
		SOAPElement usernameToken = security.addChildElement("UsernameToken", SEC);
		usernameToken.addChildElement("Username", SEC).addTextNode(epr);
		usernameToken.addChildElement("Password", SEC).addTextNode(password);
		usernameToken.addChildElement("Organization").addTextNode(ipcc);
		usernameToken.addChildElement("Domain").addTextNode("DEFAULT");					
	}
	
	// INIT BODY
	public void initBodyMessage(SOAPElement body) throws Exception{			
										
		body.addChildElement("SessionCreateRQ", NS)
					   .addChildElement("POS", NS)
					   .addChildElement("Source", NS)
					   .addAttribute(new QName( "PseudoCityCode"), ipcc);
	}

}
