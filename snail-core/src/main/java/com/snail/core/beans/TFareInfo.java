package com.snail.core.beans;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFareInfo implements Deliverable {
	private Map<String, Object> adultFareDetail;
	private Map<String, Object> childFareDetail;
	private Map<String, Object> infantFareDetail;
	private Map<String, Object> othersFareDetail;

	private TFareInfoDetail detail;

	public TFareInfo() {
		detail = new TFareInfoDetail();
	}

	public Map<String, Object> getAdultFareDetail() {
		return adultFareDetail;
	}

	public Map<String, Object> getChildFareDetail() {
		return childFareDetail;
	}

	public Map<String, Object> getInfantFareDetail() {
		return infantFareDetail;
	}

	public Map<String, Object> getOthersFareDetail() {
		return othersFareDetail;
	}

	public Map<String, Object> setFareDetail(TFareInfoDetail detail) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("nta", this.setValue(detail.getNta()));
		map.put("admin_fee", this.setValue(detail.getAdminFee()));
		map.put("fuel_surcharge", this.setValue(detail.getFuelSurcharge()));
		map.put("base_fare", this.setValue(detail.getBaseFare()));		
		map.put("iwjr", this.setValue(detail.getIwjr()));
		map.put("psc", this.setValue(detail.getPsc()));
		map.put("airport_tax", this.setValue(detail.getAirportTax()));
		map.put("commission", this.setValue(detail.getCommission()));
		map.put("upselling", this.setValue(detail.getUpselling()));
		map.put("tax", this.setValue(detail.getTax()));
		map.put("fly_thru", this.setValue(detail.getFlyThru()));
		map.put("klia2", this.setValue(detail.getKlia2()));
		map.put("gst", this.setValue(detail.getGst()));
		map.put("surcharge", this.setValue(detail.getSurcharge()));
		map.put("sti", this.setValue(detail.getSti()));
		map.put("agent_discount", this.setValue(detail.getAgentDiscount()));
		map.put("incentive", this.setValue(detail.getIncentive()));
		map.put("thru", this.setValue(detail.getThru()));
		map.put("booking_fee", this.setValue(detail.getBookingFee()));
		map.put("agt", this.setValue(detail.getAgt()));
		map.put("tad", this.setValue(detail.getTad()));
		map.put("insurance", this.setValue(detail.getInsurance()));
		map.put("terminal_fee", this.setValue(detail.getTerminalFee()));
		map.put("discount", this.setValue(detail.getDiscount()));
		map.put("baggage_allowance", this.setValue(detail.getBaggageAllowance()));
		map.put("unidentified", this.setValue(detail.getUnidentified())); //ambil fare yang ga bisa di klasifikasikan
		
		return map;
	}

	public void setAdultFareDetail() {
		this.adultFareDetail = setFareDetail(detail);
	}
	
	public void setAdultFareDetail(Map<String, Object> map) {
		this.adultFareDetail = new HashMap<String, Object>();

	for (Map.Entry<String, Object> value : map.entrySet()) {
			if (value.getValue() != null) {
				adultFareDetail.put(value.getKey(), value.getValue().toString());
			}
		}
	}



	public void setChildFareDetail() {
		this.childFareDetail = setFareDetail(detail);
	}
	
	public void setChildFareDetail(Map<String, Object> map) {
		this.childFareDetail = new HashMap<String, Object>();

		for (Map.Entry<String, Object> value : map.entrySet()) {
			if (value.getValue() != null) {
				childFareDetail.put(value.getKey(), value.getValue().toString());
			}
		}
	}



	public void setInfantFareDetail() {
		this.infantFareDetail = setFareDetail(detail);
	}
	
	public void setInfantFareDetail(Map<String, Object> map) {
		this.infantFareDetail = new HashMap<String, Object>();

		for (Map.Entry<String, Object> value : map.entrySet()) {
			if (value.getValue() != null) {
				infantFareDetail.put(value.getKey(), value.getValue().toString());
			}
		}
	}



	public void setOthersFareDetail(Map<String, Object> map) {
		othersFareDetail = new HashMap<String, Object>();

		for (Map.Entry<String, Object> value : map.entrySet()) {
			if (value.getValue() != null) {
				othersFareDetail.put(value.getKey(), value.getValue().toString());
			}
		}
	}

	// SET
	public void setNta(double nta) {
		detail.setNta(nta);
	}
	
	public void setIncentive(double incentive) {
		detail.setIncentive(incentive);
	}
	
	public void setAgentDiscount(double agentDiscount) {
		detail.setAgentDiscount(agentDiscount);
	}
	
	public void setSurcharge(double surcharge){
		detail.setSurcharge(surcharge);
	}
	
	public void setSti(double sti) {
		detail.setSti(sti);
	}
	
	public void setBaseFare(double baseFare) {
		detail.setBaseFare(baseFare);
	}

	public void setIwjr(double iwjr) {
		detail.setIwjr(iwjr);
	}

	public void setAirportTax(double airportTax) {
		detail.setAirportTax(airportTax);
	}

	public void setPsc(double psc) {
		detail.setPsc(psc);

	}

	public void setUpselling(double upselling) {
		detail.setUpselling(upselling);
	}
	
	public void setCommission(double commission) {
		detail.setCommission(commission);
	}

	public void setTax(double tax) {
		detail.setTax(tax);
	}

	public void setFlyThru(double flyThru) {
		detail.setFlyThru(flyThru);
	}

	public void setKlia2(double klia2) {
		detail.setKlia2(klia2);
	}

	public void setGst(double gst) {
		detail.setGst(gst);
	}
	
	public void setBookingFee(double bookingFee) {
		detail.setBookingFee(bookingFee);
	}
	
	public void setThru(double thru) {
		detail.setThru(thru);
	}
	
	public void setAgt(double agt) {
		detail.setAgt(agt);
	}
	
	public void setTad(double tad) {
		detail.setTad(tad);
	}
	
	public void setInsurance(double insurance) {
		detail.setInsurance(insurance);
	}
	
	public void setTerminalFee(double terminalFee) {
		detail.setTerminalFee(terminalFee);
	}
	
	public void setDiscount(double discount) {
		detail.setDiscount(discount);
	}
	
	public void setUnidentified(double unidentified){
		detail.setUnidentified(unidentified);
	}
	
	public void setAdminFee(double adminFee){
		detail.setAdminFee(adminFee);
	}
	
	public void setFuelSurcharge(double fuelSurcharge){
		detail.setFuelSurcharge(fuelSurcharge);
	}
	
	public void setBaggageAllowance(double baggageAllowance){
		detail.setBaggageAllowance(baggageAllowance);
	}
	
	public void reset() {
		this.setBaseFare(0.0);
		this.setIwjr(0.0);
		this.setPsc(0.0);
		this.setAirportTax(0.0);
		this.setCommission(0.0);
		this.setTax(0.0);
		this.setFlyThru(0.0);
		this.setKlia2(0.0);
		this.setGst(0.0);
		this.setSurcharge(0.0);
		this.setSti(0.0);
		this.setAgentDiscount(0.0);
		this.setIncentive(0.0);
		this.setThru(0.0);
		this.setBookingFee(0.0);
		this.setAgt(0.0);
		this.setTad(0.0);
		this.setInsurance(0.0);
		this.setDiscount(0.0);
		this.setTerminalFee(0.0);
		this.setUnidentified(0.0);
		this.setNta(0.0);
		this.setFuelSurcharge(0.0);
		this.setAdminFee(0.0);
		this.setBaggageAllowance(0.0);
		this.setUpselling(0.0);
	}

	@Override
	public DeliveryMap deliver() {
		DeliveryMap map = new DeliveryMap();
		map.put("adult", this.adultFareDetail);
		map.put("child", this.childFareDetail);
		map.put("infant", this.infantFareDetail);
		map.put("others", this.othersFareDetail);
		return map;
	}

	public BigDecimal setValue(double data) {
//		if (data.isEmpty() || data == null || data.equalsIgnoreCase("0.0") || data.equalsIgnoreCase("0")) {
		if(data == 0){
			return null;
		}
		
		return new BigDecimal(data);
	}

	// DETAIL FARE INFO
	public class TFareInfoDetail {
		private double baseFare = 0;
		private double nta = 0;
		private double iwjr = 0;
		private double airportTax = 0;
		private double psc = 0;
		private double commission = 0;
		private double upselling = 0;
		private double tax = 0;
		private double flyThru = 0;
		private double klia2 = 0; // Airasia, Malaysia flight
		private double gst = 0; // Airasia, Malaysia Good and service tax
		private double surcharge = 0; // Sriwijaya , Kalstar
		private double sti = 0; // Sriwijaya
		private double agentDiscount = 0; // Sriwijaya
		private double incentive = 0; // Sriwijaya
		private double thru = 0; // Tigerair
		private double bookingFee = 0; // Tigerair, Kalstar
		private double agt = 0; // Jetstar 
		private double tad = 0; // Jetstar = Travel Agent Discount 
		private double insurance = 0; // Kalstar 
		private double terminalFee = 0; // Kalstar 
		private double discount = 0; // citilink 
		private double unidentified=0;
		private double fuelSurcharge=0;
		private double adminFee=0;
		private double baggageAllowance=0;
								
		public double getUpselling() {
			return upselling;
		}

		public void setUpselling(double upselling) {
			this.upselling = upselling;
		}

		public double getBaggageAllowance() {
			return baggageAllowance;
		}

		public void setBaggageAllowance(double baggageAllowance) {
			this.baggageAllowance = baggageAllowance;
		}

		public double getFuelSurcharge() {
			return fuelSurcharge;
		}

		public void setFuelSurcharge(double fuelSurcharge) {
			this.fuelSurcharge = fuelSurcharge;
		}

		public double getAdminFee() {
			return adminFee;
		}

		public void setAdminFee(double adminFee) {
			this.adminFee = adminFee;
		}

		public double getNta() {
			return nta;
		}

		public void setNta(double nta) {
			this.nta = nta;
		}
	
		public void setAgt(double agt) {
			this.agt = agt;
		}

		public void setTad(double tad) {
			this.tad = tad;
		}

		public void setAgentDiscount(double agentDiscount) {
			this.agentDiscount = agentDiscount;
		}
		
		public void setSti(double sti) {
			this.sti = sti;
		}

		public void setSurcharge(double surcharge) {
			this.surcharge = surcharge;
		}

		public void setIncentive(double incentive) {
			this.incentive = incentive;
		}

		public void setBaseFare(double baseFare) {
			this.baseFare = baseFare;
		}

		public void setIwjr(double iwjr) {
			this.iwjr = iwjr;
		}

		public void setAirportTax(double airportTax) {
			this.airportTax = airportTax;
		}

		public void setPsc(double psc) {
			this.psc = psc;
		}

		public void setCommission(double commission) {
			this.commission = commission;
		}

		public void setTax(double tax) {
			this.tax = tax;
		}

		public void setFlyThru(double flyThru) {
			this.flyThru = flyThru;
		}

		public void setKlia2(double klia2) {
			this.klia2 = klia2;
		}

		public void setGst(double gst) {
			this.gst = gst;
		}
		
		public void setThru(double thru) {
			this.thru = thru;
		}
		
		public void setBookingFee(double bookingFee) {
			this.bookingFee = bookingFee;
		}
		
		public void setInsurance(double insurance) {
			this.insurance = insurance;
		}
		
		public void setTerminalFee(double terminalFee) {
			this.terminalFee = terminalFee;
		}
		
		public void setDiscount(double discount) {
			this.discount = discount;
		}
		
		public void setUnidentified(double unidentified) {
			this.unidentified = unidentified;
		}
		
		public Double getBaseFare() {
			return this.parseDouble(baseFare);
		}

		public Double getIwjr() {
			return this.parseDouble(iwjr);
		}

		public Double getAirportTax() {
			return this.parseDouble(airportTax);
		}

		public Double getPsc() {
			return this.parseDouble(psc);
		}

		public Double getCommission() {
			return this.parseDouble(commission);
		}

		public Double getTax() {
			return this.parseDouble(tax);
		}

		public Double getFlyThru() {
			return this.parseDouble(flyThru);
		}

		public Double getKlia2() {
			return this.parseDouble(klia2);
		}

		public Double getGst() {
			return this.parseDouble(gst);
		}

		public Double getSurcharge() {
			return this.parseDouble(surcharge);
		}

		public Double getSti() {
			return this.parseDouble(sti);
		}

		public Double getAgentDiscount() {
			return this.parseDouble(agentDiscount);
		}

		public Double getIncentive() {
			return this.parseDouble(incentive);
		}

		public Double getThru() {
			return this.parseDouble(thru);
		}
		
		public Double getBookingFee() {
			return this.parseDouble(bookingFee);
		}
		
		public Double getAgt() {
			return this.parseDouble(agt);
		}
		
		public Double getTad() {
			return this.parseDouble(tad);
		}
		
		public Double getInsurance() {
			return this.parseDouble(insurance);
		}

		public Double getTerminalFee() {
			return this.parseDouble(terminalFee);
		}
		
		public Double getDiscount() {
			return this.parseDouble(discount);
		}
		
		public Double getUnidentified() {
			return this.parseDouble(unidentified);
		}
		
		public Double parseDouble(Double value) {
			DecimalFormatSymbols decimalFormat = new DecimalFormatSymbols();
			decimalFormat.setDecimalSeparator('.');
			
			String asd = value.toString();
			String format = "#."+formatString(asd.substring(asd.indexOf(".")));
			
			return Double.parseDouble(new DecimalFormat(format, decimalFormat)
					.format(value));
		}
		
		public String formatString(String value){
			String data = "";
			
			for (int i = 0; i < value.length(); i++) {
				data += "#";
			}
			
			return data;
		}
	}
	

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
	
	public static TFareInfo createFareInfo(List<TFareInfo> fareInfos, double percentage){
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
				sumFare *= percentage; 
				
				adult.put(info.getKey(), sumFare);
			}
			
			if(tFareInfo.getChildFareDetail() != null){
				for (Map.Entry<String, Object> info : tFareInfo.getChildFareDetail().entrySet()) {
					if(info.getValue() == null) continue;
					
					String fare = child.get(info.getKey()) != null ? child.get(info.getKey()).toString() : "0";
					double fare1 = Double.parseDouble(fare);
					double fare2 = Double.parseDouble(info.getValue().toString());
					double sumFare = fare1 + fare2;;
					sumFare *= percentage; 
					
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
	
	public static TFareInfo clone(TFareInfo fareInfo) {
		TFareInfo result = new TFareInfo();
		
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
}