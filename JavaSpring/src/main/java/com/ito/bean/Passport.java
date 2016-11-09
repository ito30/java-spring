package com.ito.bean;

import java.util.Date;

import com.ito.common.JsonHandler;
import com.snail.core.fault.Fault;
import com.snail.core.util.DateUtil;

public class Passport {

	private String passportId;
	private Date expired;
	private String country;
	private Date issuedDate;

	// CONSTRUCTOR
	private Passport() {
	}

	public String getPassportId() {
		return passportId;
	}

	public String getExpired(String dateFormat) {
		return DateUtil.format(expired, dateFormat);
	}

	public String getCountry() {
		return country;
	}

	//

	public static Passport create(JsonHandler json) throws Fault {
		Passport passport = new Passport();
		passport.passportId = json.getAsString("passport_id");
		passport.expired = json.getAsDate("passport_expired");
		passport.country = json.getAsString("passport_country").toUpperCase();
		
		// NUGROHO.H Add [2014-12-29]
		if (json.has("passport_issued")) {
			passport.issuedDate = json.getAsDate("passport_issued");
		}else{
			passport.issuedDate  = null;
		}
				
		return passport;
	}
	
	// NUGROHO.H Add [2014-12-29]
	public boolean hasIssuedDate(){
		if(issuedDate != null){
			return true;
		}
		return false;
	}

	public String getIssuedDate(String dateFormat) {
		return DateUtil.format(issuedDate, dateFormat);
	}
}
