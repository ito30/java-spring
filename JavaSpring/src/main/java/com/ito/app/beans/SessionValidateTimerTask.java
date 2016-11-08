package com.ito.app.beans;

import java.util.TimerTask;

public class SessionValidateTimerTask extends TimerTask {

private AbacusSession session;
	
	public SessionValidateTimerTask(AbacusSession session) {
		this.session = session;
	}
	
	@Override
	public void run() {
		System.out.println("Run :" + session.getBinarySecurityToken());
//		SessionValidateExecution sessValidate = new SessionValidateExecution(session);
		
		try {
			String newBinarySecurityToken = null;
			if (session.getHitCount() == 0) {
				session.notValid();
//				SessionCloseExecution sessClose = new SessionCloseExecution(session);
//				sessClose.run();
			} else {
//				newBinarySecurityToken = sessValidate.run();
				session.resetHitCount();
			}
			
			if (newBinarySecurityToken == null) {
				session.stopTimer();
				session.notValid();
			}
		} catch (Exception e) {
			
			session = null;
			e.printStackTrace();
		} finally {
			session.setReady(true);
		}
	}
}
