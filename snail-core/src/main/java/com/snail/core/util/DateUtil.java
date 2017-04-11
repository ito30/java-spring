package com.snail.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	public static int MONTH_DAY[] = new int[] { 31, 29, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 };

	// TO DATE
	public static Date toDate(String string, String dateFormat)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		formatter.setLenient(false);
		return formatter.parse(string);
	}

	// TO CALENDAR
	public static Calendar toCalendar(String string, String dateFormat)
			throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate(string, dateFormat));
		return calendar;
	}

	// NOW
	public static String now(String dateFormat) {
		return format(new Date(), dateFormat);
	}

	// FORMAT
	public static String format(Date date, String dateFormat) {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		formatter.setLenient(false);
		return formatter.format(date);
	}

	// FORMAT
	public static String format(Calendar calendar, String dateFormat) {
		return format(calendar.getTime(), dateFormat);
	}

	// PLUS DAY
	public static Date plusDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}
	
	// MINUS DAY
	public static Date minusDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);

		return cal.getTime();
	}

	// PLUS DAYS AS STRING
	public static String plusDaysAsString(Date date, int days, String dateFormat) {
		return format(plusDays(date, days), dateFormat);
	}

	// PLUS HOURS
	public static Date plusHours(Date date, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hours);

		return c.getTime();
	}
	
	public static Date plusMinutes(Date date, int minutes) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);

		return c.getTime();
	}

	// using format yyyy-MM-dd
	public static boolean isYYYYMMDD(String strDate) {
		String[] chunks = strDate.split("-");

		if (chunks.length != 3) {
			return false;
		} else {
			try {
				int year = Integer.parseInt(chunks[0]);
				int month = Integer.parseInt(chunks[1]);
				int day = Integer.parseInt(chunks[2]);

				if (year > 1970 && year < 2999) {
					return true;
				} else if (month <= 12 && month >= 1) {
					return true;
				} else if (day >= 1 && day <= MONTH_DAY[month - 1]) {
					return true;
				} else {
					return false;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}

	public static long currentUnixTimestamp() {
		return System.currentTimeMillis() / 1000L;
	}

	// NUGROHO.H Add [2014-09-15]
	// TO DATE Lenient true
	public static Date toDateLenient(String string, String dateFormat)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		formatter.setLenient(true);
		return formatter.parse(string);
	}

	// NUGROHO.H Add [2014-09-24]
	// monthWordtoNumber
	public static String monthWordtoNumber(String monthWordEn)
			throws ParseException {
		Date date = new SimpleDateFormat("MMM", Locale.ENGLISH)
				.parse(monthWordEn);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		return Integer.toString(month + 1);
	}

	// NUGROHO.H Add [2014-09-24]
	// monthWordIdtoEn
	public static String monthWordIdtoEn(String monthWordId) {
		String monthIn = monthWordId;
		if (monthWordId.equalsIgnoreCase("Januari")) {
			// 1
			monthIn = "January";
		} else if (monthWordId.equalsIgnoreCase("Februari") || monthWordId.equalsIgnoreCase("Pebruari")) {
			// 2
			monthIn = "February";
		} else if (monthWordId.equalsIgnoreCase("Maret")) {
			// 3
			monthIn = "March";
		} else if (monthWordId.equalsIgnoreCase("April")) {
			// 4
			monthIn = "April";
		} else if (monthWordId.equalsIgnoreCase("Mei")) {
			// 5
			monthIn = "May";
		} else if (monthWordId.equalsIgnoreCase("Juni")) {
			// 6
			monthIn = "June";
		} else if (monthWordId.equalsIgnoreCase("Juli")) {
			// 7
			monthIn = "July";
		} else if (monthWordId.equalsIgnoreCase("Agustus")) {
			// 8
			monthIn = "August";
		} else if (monthWordId.equalsIgnoreCase("September")) {
			// 9
			monthIn = "September";
		} else if (monthWordId.equalsIgnoreCase("Oktober")) {
			// 10
			monthIn = "October";
		} else if (monthWordId.equalsIgnoreCase("November") || monthWordId.equalsIgnoreCase("Nopember")) {
			// 11
			monthIn = "November";
		} else if (monthWordId.equalsIgnoreCase("Desember")) {
			// 12
			monthIn = "December";
		}
		return monthIn;
	}
	
	// NUGROHO.H Add [2014-10-02]
	// hoursDifference int
	public static int timeDifference(Date date1, Date date2) {
	    return (int) (date1.getTime() - date2.getTime());
	}
	
	// NUGROHO.H Add [2014-12-31]
	// hoursDifference long
	public static long timeDifferenceLong(Date date1, Date date2) {
	    return (long) (date1.getTime() - date2.getTime());
	}
	
	// NUGROHO.H Add [2014-10-13]
	// TO DATE
	public static Date toDate(String string, String dateFormat, String timeZone)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		formatter.setLenient(false);
		return formatter.parse(string);
	}
}

