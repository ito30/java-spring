package com.ito.session;

import java.util.Timer;
import java.util.TimerTask;

public class Session {
	
	private String binarySecurityToken;
	private Timer timer;
	private TimerTask task;
	private boolean ready;
	private boolean valid;
	private String uri;
	private String ipcc;
	private long hitCount;
	
	private class SessionValidateTimerTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("Run :" + getBinarySecurityToken());
			
			try {
				String newBinarySecurityToken = null;
				if (getHitCount() == 0) {
					notValid();
				} else {
					resetHitCount();
				}
				
				if (newBinarySecurityToken == null) {
					stopTimer();
					notValid();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				setReady(true);
			}
		}
	}
	
	public Session(String binarySecurityToken, String uri, String ipcc){
		this.binarySecurityToken = binarySecurityToken;
		this.uri = uri;
		this.ready = true;
		this.valid = true;
		this.hitCount = 0;
		this.ipcc = ipcc;
		startTimer();
	}

	private void startTimer() {
		timer = new Timer();	
		
		// validate session every 14 min
		long sessionExpiry = 14 * 60 * 1000;
		
		task = new SessionValidateTimerTask(); 
		timer.scheduleAtFixedRate(task, sessionExpiry, sessionExpiry); 
	}
	
	public String getBinarySecurityToken() {
		return binarySecurityToken;
	}
	
	public void setBinarySecurityToken(String binarySecurityToken) {
		this.binarySecurityToken = binarySecurityToken;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void stopTimer() {
		if (timer != null) {
			task.cancel();
			timer.cancel();
		}
	}
	
	public boolean isReady() {
		return ready;
	}
	
	public void notValid(){
		this.valid = false;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void incrHitCount() {
		this.hitCount++;
	}
	
	public void resetHitCount() {
		this.hitCount = 0;
	}
	
	public long getHitCount() {
		return this.hitCount;
	}
	
	public String getIpcc() {
		return this.ipcc;
	}
}
