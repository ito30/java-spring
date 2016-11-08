package com.snail.core.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TTripFareInfo {
	
	public static TFareInfo createFareInfo(List<TFareInfo> fareInfos){
		TFareInfo fareInfo = new TFareInfo();
		Map<String, Object> adult = new HashMap<String, Object>();
		Map<String, Object> child = new HashMap<String, Object>();
		Map<String, Object> infant = new HashMap<String, Object>();
		Map<String, Object> others = new HashMap<String, Object>();
		
		for (TFareInfo tFareInfo : fareInfos) {
			for (Map.Entry<String, Object> info : tFareInfo.getAdultFareDetail().entrySet()) {
				if(info.getValue() == null) continue;
				
				String fare = adult.get(info.getKey()) != null ? adult.get(info.getKey()).toString() : "0";
				double fare1 = Double.parseDouble(fare);
				double fare2 = Double.parseDouble(info.getValue().toString());
				double sumFare = fare1 + fare2;
				
				adult.put(info.getKey(), sumFare);
			}
			
			if(tFareInfo.getChildFareDetail() != null){
				for (Map.Entry<String, Object> info : tFareInfo.getChildFareDetail().entrySet()) {
					if(info.getValue() == null) continue;
					
					String fare = child.get(info.getKey()) != null ? child.get(info.getKey()).toString() : "0";
					double fare1 = Double.parseDouble(fare);
					double fare2 = Double.parseDouble(info.getValue().toString());
					double sumFare = fare1 + fare2;;
					
					child.put(info.getKey(), sumFare);
				}
			}
			
			if(tFareInfo.getInfantFareDetail() != null){
				for (Map.Entry<String, Object> info : tFareInfo.getInfantFareDetail().entrySet()) {
					if(info.getValue() == null) continue;
					
					String fare = infant.get(info.getKey()) != null ? infant.get(info.getKey()).toString() : "0";
					double fare1 = Double.parseDouble(fare);
					double fare2 = Double.parseDouble(info.getValue().toString());
					double sumFare = fare1 + fare2;;
					
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
					
					others.put(info.getKey(), sumFare);
				}
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
}
