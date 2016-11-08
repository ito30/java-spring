package com.ito.app.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snail.core.beans.TFareInfo;

public class AbacusFareInfo extends TFareInfo {

	public static AbacusFareInfo clone(AbacusFareInfo fareInfo) {
		AbacusFareInfo result = new AbacusFareInfo();
		
		if (fareInfo.getAdultFareDetail() != null) {
			Map<String, Object> adultFareDetail = new HashMap<String, Object>();
			adultFareDetail.putAll(fareInfo.getAdultFareDetail());
			result.setAdultFareDetail(adultFareDetail);
		}
		
		if (fareInfo.getChildFareDetail() != null) {
			Map<String, Object> childFareDetail = new HashMap<String, Object>();
			childFareDetail.putAll(fareInfo.getChildFareDetail());
			result.setChildFareDetail(childFareDetail);
		}
		
		if (fareInfo.getInfantFareDetail() != null) {
			Map<String, Object> infantFareDetail = new HashMap<String, Object>();
			infantFareDetail.putAll(fareInfo.getInfantFareDetail());
			result.setInfantFareDetail(infantFareDetail);
		}
		
		if (fareInfo.getOthersFareDetail() != null) {
			Map<String, Object> othersFareDetail = new HashMap<String, Object>();
			othersFareDetail.putAll(fareInfo.getOthersFareDetail());
			result.setOthersFareDetail(othersFareDetail);
		}
		
		return result;
	}
	
	public void add(AbacusFareInfo avSegmentFareInfo){
		if (avSegmentFareInfo == null) return;
		
		Map<String, Object> adult = this.getAdultFareDetail();
		Map<String, Object> child = this.getChildFareDetail();
		Map<String, Object> infant = this.getInfantFareDetail();
		Map<String, Object> others = this.getOthersFareDetail();
		
		Map<String, Object> adultSegment = avSegmentFareInfo.getAdultFareDetail();
		Map<String, Object> childSegment = avSegmentFareInfo.getChildFareDetail();
		Map<String, Object> infantSegment = avSegmentFareInfo.getInfantFareDetail();
		Map<String, Object> othersSegment = avSegmentFareInfo.getOthersFareDetail();
		
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
				adult.put(key, fare + offset);
			}
			else {
				adult.put(key, value);
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
				child.put(key, fare + offset);
			}
			else {
				child.put(key, value);
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
				infant.put(key, fare + offset);
			}
			else {
				infant.put(key, value);
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
				others.put(key, fare + offset);
			}
			else {
				others.put(key, value);
			}
		}
	}
	
	public void setFromExisting( AbacusFareInfo fareInfo )
	{
		Map<String, Object> adultSegment = fareInfo.getAdultFareDetail();
		Map<String, Object> childSegment = fareInfo.getChildFareDetail();
		Map<String, Object> infantSegment = fareInfo.getInfantFareDetail();
		Map<String, Object> othersSegment = fareInfo.getOthersFareDetail();
		
		if (adultSegment != null)
			this.setAdultFareDetail( adultSegment );
		if (childSegment != null)
			this.setChildFareDetail( childSegment );
		if (infantSegment != null)
			this.setInfantFareDetail( infantSegment );
		if (othersSegment != null)
			this.setOthersFareDetail( othersSegment );
	}
	
	public static AbacusFareInfo create(List<PaxFare> listPaxFare){
		AbacusFareInfo fareInfo = new AbacusFareInfo();
		
		for(PaxFare paxFare : listPaxFare){		
			fareInfo.reset();
			double unidentified = 0;
			
			for(ServiceCharge serviceCharge : paxFare.getListCharge()){
				String code = serviceCharge.getChargeCode();
				double amount = serviceCharge.getAmount();				
				
				if(code.equals("FarePrice")) {
					fareInfo.setBaseFare(amount);
				}
				else if(code.equals("D5")) {
					fareInfo.setAirportTax(amount);
				}
				else if(code.equals("MarkedUpPrice")) {
					fareInfo.setUpselling(amount);
				}
				else{
					unidentified += amount;
				}				
				if(unidentified > 0) fareInfo.setUnidentified(unidentified);
				
				if(paxFare.getPaxType().equals("ADT")){
					fareInfo.setAdultFareDetail();
				}
				else if(paxFare.getPaxType().equals("CNN") || paxFare.getPaxType().startsWith("C")){
					fareInfo.setChildFareDetail();
				}		
				else if(paxFare.getPaxType().equals("INF")){
					fareInfo.setInfantFareDetail();
				}				
			}
		}
		return fareInfo;
	}
	
	public static AbacusFareInfo create(
			List<AbacusFareInfo> fareInfos, 
			double percentage,
			Upselling upSelling){
		AbacusFareInfo fareInfo = new AbacusFareInfo();
		Map<String, Object> adult = new HashMap<String, Object>();
		Map<String, Object> child = new HashMap<String, Object>();
		Map<String, Object> infant = new HashMap<String, Object>();
		Map<String, Object> others = new HashMap<String, Object>();
		double adultBaseFare = 0;
		double childBaseFare = 0;
		
		for (AbacusFareInfo tFareInfo : fareInfos) {			
			
			for (Map.Entry<String, Object> info : tFareInfo.getAdultFareDetail().entrySet()) {
				if(info.getValue() == null) continue;
				
				String fare = adult.get(info.getKey()) != null ? adult.get(info.getKey()).toString() : "0";
				double fare1 = Double.parseDouble(fare);
				double fare2 = Double.parseDouble(info.getValue().toString());
				double sumFare = fare1 + fare2;				
				sumFare *= percentage; 
				if(info.getKey().equals("base_fare"))
					adultBaseFare = sumFare;
				
				adult.put(info.getKey(), sumFare);
			}
			
			if(tFareInfo.getChildFareDetail() != null){
				for (Map.Entry<String, Object> info : tFareInfo.getChildFareDetail().entrySet()) {
					if(info.getValue() == null) continue;
					if(info.getKey() != null && info.getKey().equals("upselling") && upSelling.getValue() > 0) continue;
					
					String fare = child.get(info.getKey()) != null ? child.get(info.getKey()).toString() : "0";
					double fare1 = Double.parseDouble(fare);
					double fare2 = Double.parseDouble(info.getValue().toString());
					double sumFare = fare1 + fare2;					
					sumFare *= percentage; 
					if(info.getKey().equals("base_fare"))
						childBaseFare = sumFare;
					
					child.put(info.getKey(), sumFare);
				}
			}
			
			if(tFareInfo.getInfantFareDetail() != null){
				for (Map.Entry<String, Object> info : tFareInfo.getInfantFareDetail().entrySet()) {
					if(info.getValue() == null) continue;
					if(info.getKey() != null && info.getKey().equals("upselling") && upSelling.getValue() > 0) continue;
					
					String fare = infant.get(info.getKey()) != null ? infant.get(info.getKey()).toString() : "0";
					double fare1 = Double.parseDouble(fare);
					double fare2 = Double.parseDouble(info.getValue().toString());
					double sumFare = fare1 + fare2;;
					sumFare *= percentage; 
					
					infant.put(info.getKey(), sumFare);
				}
			}
			
			if(tFareInfo.getOthersFareDetail() != null){
				for (Map.Entry<String, Object> info : tFareInfo.getOthersFareDetail().entrySet()) {
					if(info.getValue() == null) continue;
					
					String fare = others.get(info.getKey()) != null ? others.get(info.getKey()).toString() : "0";
					double fare1 = Double.parseDouble(fare);
					double fare2 = Double.parseDouble(info.getValue().toString());
					double sumFare = fare1 + fare2;;
					sumFare *= percentage; 
					
					others.put(info.getKey(), sumFare);
				}
			}
		}					
		
		if(upSelling.getValue() > 0)
		{
			double adultUpsellingValue = 0;
			double childUpsellingValue = 0;
			
			if(upSelling.getType().equals(Upselling.Type.PERCENT)){
				adultUpsellingValue = adultBaseFare * upSelling.getValue() / 100;
				childUpsellingValue = childBaseFare * upSelling.getValue() / 100;
			}
			else{
				adultUpsellingValue = upSelling.getValue();
				childUpsellingValue = upSelling.getValue();
			}
			adult.put("upselling", adultUpsellingValue);
			if(child.size() > 0){
				child.put("upselling", childUpsellingValue);
			}
		}
		
		fareInfo.setAdultFareDetail(adult);
		
		if(child.size() > 0){
			fareInfo.setChildFareDetail(child);
		}
		
		if(infant.size() > 0){
			fareInfo.setInfantFareDetail(infant);
		}
		
		if(others.size() > 0){
			fareInfo.setOthersFareDetail(others);
		}
		
		return fareInfo;
	}
	
	public double getAdultSpecificFare(String key)
	{
		double total = 0;
		
		for (Map.Entry<String, Object> info : getAdultFareDetail().entrySet()) {
			if(info.getValue() == null) continue;			
									
			double fare = Double.parseDouble(info.getValue().toString());
			if(key.equals("") // DEFAULT, SUM ALL FARE INFO 
					|| info.getKey().equals(key)) 
				total += fare;			
		}
		
		return total;
	}
	
	public double getChildSpecificFare(String key)
	{
		double total = 0;
		if(getChildFareDetail() != null){
			for (Map.Entry<String, Object> info : getChildFareDetail().entrySet()) {
				if(info.getValue() == null) continue;			
										
				double fare = Double.parseDouble(info.getValue().toString());
				if(key.equals("") // DEFAULT, SUM ALL FARE INFO 
						|| info.getKey().equals(key))
				total += fare;
			}
		}
		
		return total;
	}
}
