package com.snail.core.util;

public class TimerWithExpired extends Timer {

	public static final long DEFAULT_TOLERANT = 150;
	public static final long DEFAULT_TIMEOUT = 2 * 60 * 1000;

	private long expiredTime;

	// CONSTRUCTOR
	public TimerWithExpired() {
		reset();
		setExpiredTime(DEFAULT_TIMEOUT);
	}

	// SET EXPIRED
	public void setExpiredTime(long msec, long tolerant) {
		this.expiredTime = msec - tolerant;
	}

	// SET EXPIRED
	public void setExpiredTime(long msec) {
		setExpiredTime(msec, DEFAULT_TOLERANT);
	}

	// SET EXPIRED TIME IN SECOND
	public void setExpiredTimeInSecond(long sec) {
		setExpiredTime(sec * 1000);
	}

	// SET EXPIRED TIME IN MINUTE
	public void setExpiredTimeInMinute(long min) {
		setExpiredTime(min * 60 * 1000);
	}

	// IS EXPIRED
	public boolean isExpired() {
		return diffAtMSec() >= expiredTime;
	}
}
