package com.snail.core.util;

import java.util.Date;

import com.ito.app.controller.JsonHandler;
import com.snail.core.fault.Fault;
import com.snail.core.fault.ValidationFault;

public class Validation {

	// DEFAULT STRING
	public static String defaultString(JsonHandler param, String name,
			String defaultValue) {

		try {
			return param.getAsString(name);
		} catch (Fault e) {
			return defaultValue;
		}
	}

	// DEFAULT STRING
	public static String defaultString(JsonHandler param, String name) {
		return defaultString(param, name, "");
	}

	// DEFAULT INT
	public static int defaultInt(JsonHandler param, String name,
			int defaultValue) {

		try {
			String string = param.getAsString(name);
			return Integer.parseInt(string);
		} catch (Exception e) {
			return defaultValue;
		}

	}

	// MIN LENGTH
	public static String minLength(JsonHandler param, String name, int length)
			throws Fault {

		String str = param.getAsString(name);

		if (str.length() < length) {
			String message = name + " at least " + length + " character";
			throw ValidationFault.create(message);
		}

		return str;
	}

	// FIXED LENGTH
	public static String fixedLength(JsonHandler param, String name, int length)
			throws Fault {
		String str = param.getAsString(name);

		if (str.length() != length) {
			String message = name + " must be " + length + " character";
			throw ValidationFault.create(message);
		}

		return str;
	}

	// VALID VALUE
	public static String validValue(JsonHandler param, String name,
			String... validValues) throws Fault {

		String str = param.getAsString(name);

		StringBuilder info = new StringBuilder();
		for (int i = 0; i < validValues.length; i++) {
			String value = validValues[i];

			if (value.equalsIgnoreCase(str)) {
				return str;
			} else {
				if (i > 0) {
					info.append(", ");
				}
				info.append(value);
			}
		}

		String message = name + " must be " + info.toString();
		throw ValidationFault.create(message);

	}

	// VALIDATE NOT SAME WITH
	public static String notSameWith(JsonHandler param, String name,
			String withName) throws Fault {
		String string = param.getAsString(name);
		String with = param.getAsString(withName);

		if (string.equalsIgnoreCase(with)) {
			String message = name + " can't be same with " + with;
			throw ValidationFault.create(message);
		}

		return string;
	}

	// GET DATE
	public static Date date(JsonHandler param, String name, int dayRange)
			throws Fault {

		Date date = param.getAsDate(name);
		if (Timer.createByNow().diffAtDay(date) < 0) {
			String message = "Date must be today or next days";
			throw ValidationFault.create(message);
		} else if (dayRange > 0) {
			long dateDiff = Timer.createByNow().diffAtDay(date);
			if (dateDiff > dayRange) {
				String message = "Max " + dayRange + " days in advance";
				throw ValidationFault.create(message);
			}
		}

		return date;
	}

}
