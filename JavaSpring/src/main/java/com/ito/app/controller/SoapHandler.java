package com.ito.app.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.soap.SOAPElement;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.snail.core.util.DateUtil;
import com.snail.core.util.StringUtil;


public class SoapHandler {

	private SOAPElement soap;

	// CONSTRUCTOR
	public SoapHandler(SOAPElement soap) {
		this.soap = soap;
	}

	// GET TEXT
	public String getText() {
		return soap.getTextContent();	
	}
	
	// GET ATTRIBUTE AS STRING
	public String getAttributeValueAsString(String name) {
		return soap.getAttribute(name);		
	}
	
	// GET ATTRIBUTE AS DATE
	public Date getAttributeValueAsDate(String name, String dateFormat)
		throws ParseException {
	
		String string = soap.getAttribute(name);
		
		if (string != null && !string.equals("")) {
			return DateUtil.toDate(string, dateFormat);
		} else {
			return null;
		}	
	}
	
	// GET ATTRIBUTE AS DOUBLE
	public double getAttributeValueAsDouble(String name) {
		String string = soap.getAttribute(name);
		
		if (string != null && !string.equals("")) {
			return StringUtil.toDouble(string);
		} else {
			return 0;
		}	
	}
	
	// FIRST VALUE
	public SoapHandler find(String name) {
		NodeList nodeList = soap.getElementsByTagName(name);
		if (nodeList.getLength() > 0) {
			return new SoapHandler((SOAPElement) nodeList.item(0));
		} else {
			return null;
		}
	}

	// FIRST VALUE AS STRING
	public String findAsString(String name) {
		NodeList nodeList = soap.getElementsByTagName(name);
		if (nodeList.getLength() > 0) {
			Node node = nodeList.item(0);
			return node.getTextContent().trim();
		} else {
			return null;
		}
	}

	// FIND AS DATE
	public Date findAsDate(String name, String dateFormat)
			throws ParseException {

		String string = findAsString(name);

		if (string != null) {
			return DateUtil.toDate(string, dateFormat);
		} else {
			return null;
		}

	}

	// FIRST VALUE AS DOUBLE
	public double findAsDouble(String name) {
		NodeList nodeList = soap.getElementsByTagName(name);
		if (nodeList.getLength() > 0) {
			Node node = nodeList.item(0);
			return StringUtil.toDouble(node.getTextContent());
		} else {
			return 0;
		}

	}

	// CREATE LIST BY TAG NAME
	public List<SoapHandler> findAsList(String name) {
		NodeList nodeList = soap.getElementsByTagName(name);
		List<SoapHandler> list = new ArrayList<SoapHandler>();
//		
		for (int i = 0; i < nodeList.getLength(); i++) {
			SOAPElement child = (SOAPElement) nodeList.item(i);
			SoapHandler soapHandler = new SoapHandler(child);
			list.add(soapHandler);
		}

		return list;
	}
	
	// GET VALUE SOAP BY INDEX
	public SoapHandler findAtIndex(String name, int index) {
		NodeList nodeList = soap.getElementsByTagName(name);
		
		return new SoapHandler((SOAPElement) nodeList.item(index));
	}

	// CREATE BY NAMES
	public List<SoapHandler> childAsList(String name) {
		List<SoapHandler> list = new ArrayList<SoapHandler>();

		NodeList nodeList = soap.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {

			Node childNode = nodeList.item(i);

			if (childNode instanceof SOAPElement) {
				SOAPElement child = (SOAPElement) childNode;
				String childName = child.getLocalName();
				if (StringUtil.isSame(childName, name)) {
					SoapHandler soapHandler = new SoapHandler(child);
					list.add(soapHandler);
				}
			}
		}

		return list;
	}

	// CHILD
	public SoapHandler child(String name) {
		NodeList nodeList = soap.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			if (childNode instanceof SOAPElement) {
				SOAPElement child = (SOAPElement) childNode;
				String childName = child.getElementName().getLocalName();

				if (StringUtil.isSame(childName, name)) {
					return new SoapHandler(child);
				}
			}
		}

		return null;

	}

	// CHILD AS STRING
	public String childAsString(String name) {
		NodeList nodeList = soap.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			String childName = childNode.getNodeName();

			if (StringUtil.isSame(childName, name)) {
				return childNode.getTextContent();
			}
		}

		return null;
	}
	
	// NUGROHO.H Add [2014-09-15]
	// CREATE LIST BY TAG NAME & TAG TYPE
	public List<SoapHandler> findAsListByTagType(String name, String attrKey, String attrType) {
		NodeList nodeList = soap.getElementsByTagName(name);
		List<SoapHandler> list = new ArrayList<SoapHandler>();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			SOAPElement child = (SOAPElement) nodeList.item(i);
			String attributeType = child.getAttribute(attrKey);
			if(attributeType!=null && attributeType.equals(attrType)){
				SoapHandler soapHandler = new SoapHandler(child);
				list.add(soapHandler);
			}
		}

		return list;
	}
	
	// NUGROHO.H Add [2015-02-10]
	// CREATE LIST BY TAG NAME & TAG TYPE LIKE
	public List<SoapHandler> findAsListByTagTypeLike(String name, String attrKey, String attrType) {
		NodeList nodeList = soap.getElementsByTagName(name);
		List<SoapHandler> list = new ArrayList<SoapHandler>();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			SOAPElement child = (SOAPElement) nodeList.item(i);
			String attributeType = child.getAttribute(attrKey);
			
			if(attributeType!=null && attributeType.contains(attrType)){
				SoapHandler soapHandler = new SoapHandler(child);
				list.add(soapHandler);
			}
		}

		return list;
	}
		
	// CREATE LIST BY TAG NAME
	public List<SoapHandler> findAsListByTagName(String name, String attrKey) {
		NodeList nodeList = soap.getElementsByTagName(name);
		List<SoapHandler> list = new ArrayList<SoapHandler>();
		
		for (int i = 0; i < nodeList.getLength(); i++) {
			SOAPElement child = (SOAPElement) nodeList.item(i);
			String attributeType = child.getAttribute(attrKey);
			
			if(attributeType!=null && !attributeType.equals("")){
				SoapHandler soapHandler = new SoapHandler(child);
				list.add(soapHandler);
			}
		}

		return list;
	}
	
	// NUGROHO.H Add [2014-10-13]
	// FIND AS DATE
	public Date findAsDate(String name, String dateFormat, String timeZone)
			throws ParseException {

		String string = findAsString(name);

		if (string != null) {
			return DateUtil.toDate(string, dateFormat, timeZone);
		} else {
			return null;
		}

	}

}
