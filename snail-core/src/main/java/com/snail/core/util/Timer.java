package com.snail.core.util;

import java.util.Date;

public class Timer {

	public static final long DAY_IN_MSEC = 24 * 60 * 60 * 1000;

	private long startTime;

	// CONSTRUCTOR
	protected Timer() {
	}

	// START
	public void set(Date date) {
		if (date != null) {
			this.startTime = date.getTime();
		}
	}

	// START
	public void reset() {
		set(new Date());
	}

	// DIFFERENCE AT MILLISECOND
	public long diffAtMSec() {
		return diffAtMSec(new Date());
	}

	// DIFFERENCE AT MILLISECOND
	public long diffAtMSec(Date date) {
		if (startTime <= 0) {
			return -1;
		} else {
			return date.getTime() - startTime;
		}
	}

	// DIFFERENCE AT DAY
	public long diffAtDay(Date date) {
		if (startTime <= 0) {
			return -1;
		} else {
			long msec = date.getTime() - startTime;
			return msec / DAY_IN_MSEC;
		}
	}

	// ** FACTORY **

	// CREATE
	public static Timer create() {
		return new Timer();
	}

	// CREATE BY DATE
	public static Timer createByDate(Date date) {
		Timer timer = new Timer();
		timer.set(date);

		return timer;
	}

	public static Timer createByNow() {
		Timer timer = new Timer();
		timer.set(new Date());

		return timer;
	}

}
