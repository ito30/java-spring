package com.ito.app.beans;

import com.snail.core.util.StringUtil;

public class Contact {
	public static final String ADDRESS = "Jl. Kawi No.45, Setiabudi";
	public static final String COMPANY = "PT Global Tiket";
	public static final String COUNTRY_CODE = "ID";
	public static final String DISTRIBUTION_OPTION = "Email";
	public static final String CITY = "Jakarta";
	public static final String PROVINCE = "JK";
	public static final String POSTAL = "12980";
	public static final String OTHER_PHONE = "+622183782121";

	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String gender;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getGender() {
		return gender;
	}

	public boolean isFemale() {
		return StringUtil.isSame(gender, Pax.FEMALE);
	}

	public String getTitle() {
		if (isFemale()) {
			return "MS";
		} else {
			return "MR";
		}
	}
}
