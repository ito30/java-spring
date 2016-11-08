package com.ito.app.inquiry;

import java.util.ArrayList;
import java.util.List;

import com.ito.app.beans.Upselling;
import com.ito.app.controller.JsonHandler;
import com.snail.core.fault.Fault;
import com.snail.core.scenario.inquiry.SearchInquiry;

public class AbacusSearchInquiry extends SearchInquiry {
	private String currency;
	private List<Upselling> upSellings;
	private List<Upselling> returnUpsellings;
	
	public void inquire(JsonHandler param) throws Fault{
		super.inquire(param);
		this.upSellings = new ArrayList<Upselling>();
		this.returnUpsellings = new ArrayList<Upselling>();
		
		if(param.has("currency")){
			currency = param.getAsString("currency");
		}else{
			currency = "IDR";
		}
		
		if (param.has("upselling")) {
			List<JsonHandler> upSellings = param.getAsList("upselling");
			
			for (JsonHandler upSelling : upSellings) {
				Upselling upSell = new Upselling();
				upSell.setAirline(upSelling.getAsString("airline"));
				upSell.setType(upSelling.getAsString("type"));
				upSell.setValue(upSelling.getAsDouble("value"));
				
				this.upSellings.add(upSell);
			}
		}
		
		if (param.has("upselling_return")) {
			List<JsonHandler> returnUpsellings = param.getAsList("upselling_return");
			
			for (JsonHandler upSelling : returnUpsellings) {
				Upselling upSell = new Upselling();
				upSell.setAirline(upSelling.getAsString("airline"));
				upSell.setType(upSelling.getAsString("type"));
				upSell.setValue(upSelling.getAsDouble("value"));
				
				this.returnUpsellings.add(upSell);
			}
		}
	}
	
	public List<Upselling> getReturnUpsellings() {
		return returnUpsellings;
	}

	public List<Upselling> getUpsellings() {
		return upSellings;
	}
	
	public String getCurrency(){
		return currency;
	}
}