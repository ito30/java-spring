package com.ito.bean;

import java.util.Calendar;
import java.util.Date;

import com.snail.core.util.DateUtil;
import com.snail.core.util.StringUtil;

public class Infant {
	private String firstName;
	private String lastName;
	private Date dob;
	private String gender;
	private Passport passport;
	
	private String nameNumber;
	
	public String getNameNumber() {
		return nameNumber;
	}

	public void setNameNumber(String nameNumber) {
		this.nameNumber = nameNumber;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public Passport getPassport() {
		return passport;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getDob() {
		return dob;
	}

	public String getDob(String dateFormat) {
		return DateUtil.format(dob, dateFormat);
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return isMale() ? "M" : "F";
	}

	public boolean isFemale() {
		return StringUtil.isSame(gender, Pax.FEMALE);
	}

	public boolean isMale() {
		return StringUtil.isSame(gender, Pax.MALE);
	}

	public String getTitle() {
		if (isMale()) {
			return "MR";
		} else {
			return "MISS";
		}
	}

	public Calendar getDobAsCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dob);
		return cal;
	}
	
	public boolean hasPassport() {
		return passport != null;
	}
		
	public String getPassengerTypePreference( Date departureDate )
	{
		String age = "I";
		Calendar departureCal = Calendar.getInstance();
		departureCal.setTime(departureDate);
		Calendar dobCal = getDobAsCalendar();
				
		int yearDiff = departureCal.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
		int monthDiff = departureCal.get(Calendar.MONTH) - dobCal.get(Calendar.MONTH);
		
		int months = (yearDiff * 12) + monthDiff;				

		if( months < 10 )
			age += "0"+months;
		else
			age += months;
		
		return age;
	}
}

