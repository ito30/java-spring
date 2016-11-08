package com.ito.app.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.snail.core.util.StringUtil;

public class Pax {
	public static final String CHILD = "child";
	public static final String ADULT = "adult";
	public static final String INFANT = "infant";
	public static final String MALE = "male";
	public static final String FEMALE = "female";

	private String index;
	private String firstName;
	private String lastName;
	private String type;
	private String gender;
	private String nationality;
	private Date dob;
	private String title;

	private int baggageSize;	
	private Passport passport;
	
	private String nameNumber;
	private String refNameNumber;
	
	public String getRefNameNumber() {
		return refNameNumber;
	}

	public void setRefNameNumber(String refNameNumber) {
		this.refNameNumber = refNameNumber;
	}

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

	public void setIndex(String index) {
		this.index = index;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality.toUpperCase();
	}

	public void setBaggageSize(int baggageSize) {
		this.baggageSize = baggageSize;
	}

	public int getBaggageSize() {
		return baggageSize;
	}

	public String getNationality() {
		return nationality;
	}

	public String getIndex() {
		return index;
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

	public String getDob(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
		return dateFormat.format(dob);
	}

	public String getGenderIndex() {
		return isMale() ? "1" : "2";
	}

	public Calendar getDobAsCalendar() {
		if (dob != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dob);

			return cal;
		}

		return null;
	}

	public String getTigerairGender() {
		if (isFemale()) {
			return "Female";
		} else {
			return "Male";
		}
	}
	
	public String getGender() {
		if (isFemale()) {
			return "F";
		} else {
			return "M";
		}
	}

	public String getPaxType() {
		if (isChild()) {
			return "CHD";
		} else {
			return "ADT";
		}
	}

	public String getWeightCategory() {
		if (isAdult()) {
			return getTigerairGender();
		} else {
			return "Child";
		}
	}

	public boolean isChild() {
		return StringUtil.isSame(type, CHILD);
	}

	public boolean isAdult() {
		return StringUtil.isSame(type, ADULT);
	}
	
	public boolean isInfant() {
		return StringUtil.isSame(type, INFANT);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public boolean hasBaggage() {
		return baggageSize > 0;
	}

	public boolean isFemale() {
		return StringUtil.isSame(gender, FEMALE);
	}

	public boolean isMale() {
		return StringUtil.isSame(gender, MALE);
	}
	
	public boolean hasPassport() {
		return passport != null;
	}
		
	public String getPassengerTypePreference( Date departureDate )
	{
		String prefix;
		Calendar departureCal = Calendar.getInstance();
		departureCal.setTime(departureDate);
		Calendar dobCal = getDobAsCalendar();
				
		int yearDiff = departureCal.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
		int monthDiff = departureCal.get(Calendar.MONTH) - dobCal.get(Calendar.MONTH);
		
		int months = (yearDiff * 12) + monthDiff;
		
		if( isAdult() )
			return "ADT";
		else if( isChild() ){
			prefix = "C";
			months /= 12;
		}
		else
		{
			prefix = "I";
		}
		
		String age = prefix;
								
		if( months < 10 )
			age += "0"+months;
		else
			age += months;
		
		return age;
		
	}
}
