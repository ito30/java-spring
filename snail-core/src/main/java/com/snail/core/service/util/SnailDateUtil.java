package com.snail.core.service.util;

import java.util.Calendar;

/**
 * 
 * @author iman
 * 
 */
public class SnailDateUtil {

	// CREATE DATE
	public static Calendar createDate(int hour, int minute, Calendar prevCal) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(prevCal.getTime());
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);

		int amPm1 = cal.get(Calendar.AM_PM);
		int amPm0 = prevCal.get(Calendar.AM_PM);
		int prevHour = prevCal.get(Calendar.HOUR_OF_DAY);

		if (amPm1 < amPm0 || (amPm1 == amPm0 && prevHour > hour)) {
			cal.add(Calendar.DATE, 1);
		}
		return cal;
	}
}
