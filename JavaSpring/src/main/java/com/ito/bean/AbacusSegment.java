package com.ito.bean;

import java.util.Date;

import com.ito.common.SoapHandler;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.beans.TFlightDesignator;
import com.snail.core.beans.TSegment;
import com.snail.core.util.StringUtil;

public class AbacusSegment extends TSegment{
	
	private String baggageInfo;	
	private String marriageGroup;
	private String depTerminal;
	private String arrTerminal;
	private TFlightDesignator operatingDesignator;

	public static AbacusSegment create(SoapHandler segmentSOAP, String seatCount)
	throws Exception {
		
		SoapHandler depAirport = segmentSOAP.find("DepartureAirport");
		SoapHandler arrAirport = segmentSOAP.find("ArrivalAirport");
		
		String departure = depAirport.getAttributeValueAsString("LocationCode");
		String arrival = arrAirport.getAttributeValueAsString("LocationCode");		
		
		String depTerminal = depAirport.getAttributeValueAsString("TerminalID");
		String arrTerminal = arrAirport.getAttributeValueAsString("TerminalID");
		
		// flight designator
		SoapHandler operatingAirline = segmentSOAP.find("OperatingAirline");
		String operatingCode = operatingAirline.getAttributeValueAsString("Code");
		String operatingFlightNumber = operatingAirline.getAttributeValueAsString("FlightNumber");
		TFlightDesignator operatingDesignator = TFlightDesignator.create(operatingCode,
				operatingFlightNumber);
		
		SoapHandler marketingAirline = segmentSOAP.find("MarketingAirline");
		String carrierCode = marketingAirline.getAttributeValueAsString("Code");
		String flightNumber = segmentSOAP.getAttributeValueAsString("FlightNumber");
		TFlightDesignator designator = TFlightDesignator.create(carrierCode,
				flightNumber);
		Date sta = segmentSOAP.getAttributeValueAsDate("ArrivalDateTime", "yyyy-MM-dd'T'HH:mm:ss");
		Date std = segmentSOAP.getAttributeValueAsDate("DepartureDateTime", "yyyy-MM-dd'T'HH:mm:ss");
		
		String classOfService = segmentSOAP.getAttributeValueAsString("ResBookDesigCode");		
		String airEquipmentType = segmentSOAP.find("Equipment").getAttributeValueAsString("AirEquipType");
		String marriageGroup = segmentSOAP.findAsString("MarriageGrp");
				
		AbacusSegment segment = new AbacusSegment();
		// SET TSegment
		segment.setArrival(arrival);
		segment.setDepTerminal(depTerminal);
		segment.setDeparture(departure);
		segment.setArrTerminal(arrTerminal);
		segment.setSTA(sta);
		segment.setSTD(std);
		segment.setDesignator(designator);
		segment.setOperatingDesignator(operatingDesignator);
		segment.setServiceClass(classOfService);
		segment.setAircraftType(airEquipmentType);
				
		if(seatCount!=null && !seatCount.equals("")){
			segment.setSeatCount(StringUtil.toInt(seatCount));
		}
				
		segment.setMarriageGroup(marriageGroup);
		
		return segment;
	}
		
	public void setDepTerminal(String depTerminal) {
		this.depTerminal = depTerminal;
	}

	public void setArrTerminal(String arrTerminal) {
		this.arrTerminal = arrTerminal;
	}

	public String getMarriageGroup() {
		return marriageGroup;
	}

	public void setMarriageGroup(String marriageGroup) {
		this.marriageGroup = marriageGroup;
	}

	public String getBaggageInfo() {
		return baggageInfo;
	}

	public void setBaggageInfo(String baggageInfo) {
		this.baggageInfo = baggageInfo;
	}
	
	public TFlightDesignator getOperatingDesignator() {
		return operatingDesignator;
	}

	public void setOperatingDesignator(TFlightDesignator operatingDesignator) {
		this.operatingDesignator = operatingDesignator;
	}

	@Override
	public DeliveryMap deliver() {
		// TODO Auto-generated method stub
		DeliveryMap map =  super.deliver();
		map.put("baggage_info", baggageInfo);
		if(operatingDesignator!=null)map.put("operating_carrier", operatingDesignator.toString().trim());
		map.put("marriage_group", marriageGroup);
		map.put("departure_terminal", depTerminal);
		map.put("arrival_terminal", arrTerminal);
		return map;
	}
}
