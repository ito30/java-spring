package com.ito.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.snail.core.beans.DeliveryMap;
import com.snail.core.beans.TAvailability;
import com.snail.core.beans.TFlightSelect;
import com.snail.core.util.SoapHandler;

public class AbacusAvailability  extends TAvailability{
	
	private String validatingCarrier;	
	private boolean markAsRemoved;		
	
	public boolean isMarkAsRemoved() {
		return markAsRemoved;
	}		

	public void setMarkAsRemoved(boolean markAsRemoved) {
		this.markAsRemoved = markAsRemoved;
	}

	public void setValidatingCarrier(String validatingCarrier) {
		this.validatingCarrier = validatingCarrier;
	}	

	public String getValidatingCarrier() {
		return validatingCarrier;
	}

	public void setListSegment(List<AbacusSegment> listSegment) {
		this.listSegment = listSegment;
	}
	
	public void addReturnSegmet(List<AbacusSegment> listSegment)
	{
		this.listSegment.addAll(listSegment);
	}

	private List<AbacusSegment> listSegment;	
	private AbacusFareInfo abacusFareInfo;
	
	public List<AbacusSegment> getListSegment() {
		return listSegment;
	}
	
	public void addListSegments(List<AbacusSegment> segments)
	{
		for (AbacusSegment segment : segments) {
			listSegment.add(segment);
		}
	}
	
	public List<AbacusSegment> getDepartSegments(String arrival)
	{		
		List<AbacusSegment> segments = new ArrayList<AbacusSegment>();
		for (AbacusSegment segment : listSegment) {
			segments.add(segment);
			if( segment.getArrival().equals(arrival) )
				break;
		}
		return segments;
	}
	
	public List<AbacusSegment> getReturnSegments(String arrival)
	{
		boolean segmentFound = false;
		List<AbacusSegment> segments = new ArrayList<AbacusSegment>();
		for (AbacusSegment segment : listSegment) {
			if( segment.getDeparture().equals(arrival) )
				segmentFound = true;
			if(segmentFound)
				segments.add(segment);			
		}
		return segments;
	}
	
	public static AbacusAvailability getDepartAvailability(
			AbacusAvailability aa,
			String arrival,
			double percentage,
			Upselling upSelling)
	{
		AbacusAvailability av = new AbacusAvailability();
		List<AbacusSegment> segments = aa.getDepartSegments(arrival);
		if(segments.size()==0)
			return null;
				
		AbacusFareInfo dividedFareInfo = getDividedFareInfo(aa.getAbacusFareInfo(), percentage,upSelling);				
		av.setAbacusFareInfo(dividedFareInfo);
		av.setServiceClass(segments.get(0).getServiceClass());
		av.setAdultFare(dividedFareInfo.getAdultSpecificFare(""), aa.getAdultFare().getCount());
		av.setChildFare(dividedFareInfo.getChildSpecificFare(""), aa.getChildFare().getCount());
		av.setInfantFare(aa.getInfantFare().getUnitPrice()*percentage, aa.getInfantFare().getCount());
		av.setCurrency(aa.getCurrency());
		av.setValidatingCarrier(aa.getValidatingCarrier());
		av.listSegment = segments;
		av.setInternational(true);
		
		return av;
	}
	
	public static AbacusAvailability getReturnAvailability(
			AbacusAvailability aa,
			String arrival, 
			double percentage,
			Upselling returnUpselling)
	{
		AbacusAvailability av = new AbacusAvailability();
		List<AbacusSegment> segments = aa.getReturnSegments(arrival);
		if(segments.size()==0)
			return null;
		
		AbacusFareInfo dividedFareInfo = getDividedFareInfo(aa.getAbacusFareInfo(), percentage,returnUpselling);	
		av.setAbacusFareInfo(dividedFareInfo);
		av.setServiceClass(segments.get(0).getServiceClass());
		av.setAdultFare(dividedFareInfo.getAdultSpecificFare(""), aa.getAdultFare().getCount());
		av.setChildFare(dividedFareInfo.getChildSpecificFare(""), aa.getChildFare().getCount());
		av.setInfantFare(aa.getInfantFare().getUnitPrice()*percentage, aa.getInfantFare().getCount());
		av.setCurrency(aa.getCurrency());
		av.setValidatingCarrier(aa.getValidatingCarrier());
		av.listSegment = segments;
		av.setInternational(true);
		
		return av;
	}
	
	private static AbacusFareInfo getDividedFareInfo(
			AbacusFareInfo fareInfo, 
			double percentage,
			Upselling upSelling)
	{
		AbacusFareInfo clonedFareInfo = AbacusFareInfo.clone(fareInfo);
		List<AbacusFareInfo> fareInfos = new ArrayList<AbacusFareInfo>();
		fareInfos.add(clonedFareInfo);
		AbacusFareInfo halfedFareInfo = AbacusFareInfo.create(fareInfos, percentage, upSelling);
		return halfedFareInfo;
	}
	
	public static AbacusAvailability create(
			SoapHandler journeySOAP,
			Map<String, String> paxMap,
			List<Upselling> upSellings) throws Exception {

		List<SoapHandler> listSoapSegment = journeySOAP.findAsList("FlightSegment");
		List<AbacusSegment> listSegment = new ArrayList<AbacusSegment>();								
		String seatCount = journeySOAP.find("SeatsRemaining").getAttributeValueAsString("Number");
		List<SoapHandler> listBaggageInfo = journeySOAP.findAsList("BaggageInformation");	
		String validatingCarrier = journeySOAP.find("ValidatingCarrier").getAttributeValueAsString("Code");
		
		AbacusAvailability av = new AbacusAvailability();				
		
		double adultFare = 0;
		double childFare = 0;
		double infantFare = 0;
		String serviceClass = "";
		String currency = "";
		List<AbacusBaggageInfo> baggageInfoList = mappingBaggageInfo(listBaggageInfo);
		int segmentCount = listSoapSegment.size();
		av.setMarkAsRemoved(false);
		
		for (int i = 0; i < segmentCount; i++)
		{			
			SoapHandler soapSegment = listSoapSegment.get(i);
			AbacusSegment segment = AbacusSegment.create(soapSegment, seatCount);			
			serviceClass = segment.getServiceClass();	
			segment.setBaggageInfo(getBaggageFromList(baggageInfoList, String.valueOf(i)).getInfo());
			if( segment.getAircraftType().equalsIgnoreCase("TRN") || segment.getAircraftType().equalsIgnoreCase("BUS") )
				av.setMarkAsRemoved(true);
			listSegment.add(segment);			
		}	
		
		List<SoapHandler> listSoapAvFare = journeySOAP
		.findAsList("PTC_FareBreakdown");
		List<PaxFare> listAvFare = new ArrayList<PaxFare>();
		
		Upselling upSelling = new Upselling();
		for (Upselling _upSelling : upSellings) {
			if (_upSelling.getAirline().equals(validatingCarrier)) {
				upSelling = _upSelling;
				break;
			}
		}

		for (SoapHandler soapAvFare : listSoapAvFare) {
			
			PaxFare avFare = PaxFare.create(soapAvFare, upSelling);
			listAvFare.add(avFare);	
			currency = soapAvFare.find("EquivFare").getAttributeValueAsString("CurrencyCode");									
		}				
		AbacusFareInfo fareInfo = AbacusFareInfo.create(listAvFare);
		
		SegmentPaxFare segmentAvFare = new SegmentPaxFare();	
		segmentAvFare.setListAvFare(listAvFare);
				
		adultFare = segmentAvFare.getAdultFare();
		childFare = segmentAvFare.getChildFare();
		infantFare = segmentAvFare.getInfantFare();
		
		int adultCount = 0;
		int childCount = 0;
		int infantCount = 0;
		adultCount = Integer.parseInt(paxMap.get("adultCount"));
		childCount = Integer.parseInt(paxMap.get("childCount"));
		infantCount = Integer.parseInt(paxMap.get("infantCount"));			
			
		av.setAbacusFareInfo(fareInfo);
		av.setServiceClass(serviceClass);
		av.setAdultFare(adultFare, adultCount);
		av.setChildFare(childFare, childCount);
		av.setInfantFare(infantFare, infantCount);
		av.setCurrency(currency);
		av.setValidatingCarrier(validatingCarrier);
		av.listSegment = listSegment;
		av.setInternational(true);
		return av;
	}	
	
	public void updateFare(List<PaxFare> listAvFare, Map<String, String> paxMap)
	{
		SegmentPaxFare segmentAvFare = new SegmentPaxFare();	
		segmentAvFare.setListAvFare(listAvFare);
				
		double adultFare = segmentAvFare.getAdultFare();
		double childFare = segmentAvFare.getChildFare();
		double infantFare = segmentAvFare.getInfantFare();
		
		int adultCount = 0;
		int childCount = 0;
		int infantCount = 0;
		adultCount = Integer.parseInt(paxMap.get("adultCount"));
		childCount = Integer.parseInt(paxMap.get("childCount"));
		infantCount = Integer.parseInt(paxMap.get("infantCount"));
		
		this.setAdultFare(adultFare,adultCount );
		this.setChildFare(childFare, childCount);
		this.setInfantFare(infantFare, infantCount);
	}
	
	public AbacusFareInfo getAbacusFareInfo() {
		return abacusFareInfo;
	}

	@Override
	public DeliveryMap deliver() {
		String flightSelect = TFlightSelect
				.createFlightSelectString(listSegment);

		DeliveryMap map = super.deliver();
		map.put("validating_carrier", validatingCarrier);
		map.put("flight_select", flightSelect);
		map.put("flight_info", listSegment);
		map.put("fare_info", abacusFareInfo);		
		return map;
	}
	public void setAbacusFareInfo(AbacusFareInfo AbacusFareInfo) {
		this.abacusFareInfo = AbacusFareInfo;
	}
	
	public void compareAbacusFareInfo(AbacusFareInfo input) 
	{
		Map<String, Object> adult = this.abacusFareInfo.getAdultFareDetail();
		Map<String, Object> child = this.abacusFareInfo.getChildFareDetail();
		Map<String, Object> infant = this.abacusFareInfo.getInfantFareDetail();
		Map<String, Object> others = this.abacusFareInfo.getOthersFareDetail();
		
		Map<String, Object> adultSegment = input.getAdultFareDetail();
		Map<String, Object> childSegment = input.getChildFareDetail();
		Map<String, Object> infantSegment = input.getInfantFareDetail();
		Map<String, Object> othersSegment = input.getOthersFareDetail();
		
		//ADULT
		if (adult != null)
		for (Map.Entry<String, Object> fareDetail : adultSegment.entrySet()){
			
			double offset = 0.0;
			String key = fareDetail.getKey();
			Object value = fareDetail.getValue();
			
			if(key != "thru" && key != "booking_fee" 
					         && adult.get(key) != "" 
					         && adult.get(key) != null) {	
				if (value != null) offset = Double.valueOf(value.toString());
				
				double fare = Double.parseDouble(adult.get(key).toString());								
				double diff = fare - offset;
				
				if( diff == 0 )
					adult.remove(key);
				else if( diff > 0 )
					adult.put(key, diff);
			}			
		}
		
		//CHILD
		if (child != null)
		for (Map.Entry<String, Object> fareDetail : childSegment.entrySet()){
			double offset = 0.0;
			String key = fareDetail.getKey();
			Object value = fareDetail.getValue();
			
			if(key != "thru" && key != "booking_fee" 
					         && child.get(key) != "" 
					         && child.get(key) != null) {	
				if (value != null) offset = Double.valueOf(value.toString());
				
				double fare = Double.parseDouble(child.get(key).toString());
				double diff = fare - offset;
				
				if( diff == 0 )
					child.remove(key);
				else if( diff > 0 )
					child.put(key, diff);
			}			
		}
		
		//INFANT
		if (infant != null)
		for (Map.Entry<String, Object> fareDetail : infantSegment.entrySet()){
			double offset = 0.0;
			String key = fareDetail.getKey();
			Object value = fareDetail.getValue();
			
			if(key != "thru" && key != "booking_fee" 
					         && infant.get(key) != "" 
					         && infant.get(key) != null) {	
				if (value != null) offset = Double.valueOf(value.toString());
				
				double fare = Double.parseDouble(infant.get(key).toString());
				double diff = fare - offset;
				if( diff == 0 )
					infant.remove(key);
				else if( diff > 0 )
					infant.put(key, diff);
			}
		}
		
		//OTHERS
		if (others != null)
		for (Map.Entry<String, Object> fareDetail : othersSegment.entrySet()){
			double offset = 0.0;
			String key = fareDetail.getKey();
			Object value = fareDetail.getValue();
			
			if(key != "thru" && key != "booking_fee" 
					         && others.get(key) != "" 
					         && others.get(key) != null) {	
				if (value != null) offset = Double.valueOf(value.toString());
				
				double fare = Double.parseDouble(others.get(key).toString());
				double diff = fare - offset;
				if( diff == 0 )
					others.remove(key);
				else if( diff > 0 )
					others.put(key, diff);
			}			
		}
	}
	
	private static List<AbacusBaggageInfo> mappingBaggageInfo(List<SoapHandler> listBaggageInfo)
	{
		List<AbacusBaggageInfo> result = new ArrayList<AbacusBaggageInfo>();		
		for (SoapHandler bagInfo : listBaggageInfo) 
		{
			List<SoapHandler> listSegmentID = bagInfo.findAsList("Segment");
			for (SoapHandler soapSegment : listSegmentID) 
			{
				String id = soapSegment.getAttributeValueAsString("Id");
				AbacusBaggageInfo info = new AbacusBaggageInfo(id, processBaggageInfo( bagInfo.find("Allowance")));
				result.add(info);
			}		
		}
		
		return result;
	}
	
	private static AbacusBaggageInfo getBaggageFromList(List<AbacusBaggageInfo> bags, String id)
	{
		for (AbacusBaggageInfo bag : bags) {
			if( bag.getId().equals(id) )
				return bag;
		}
		return new AbacusBaggageInfo("", "no baggage");
	}
	
	private static String processBaggageInfo(SoapHandler bagInfo)
	{
		String result = "";
		String piece = bagInfo.getAttributeValueAsString("Pieces");
		if( piece == null || piece.equals("") )
		{
			String weight = bagInfo.getAttributeValueAsString("Weight");
			String unit = bagInfo.getAttributeValueAsString("Unit");
			
			result = weight + " " + unit;
		}
		else
			result = piece + " pc";
		
		return result;
	}
}