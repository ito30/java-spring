package com.ito.app.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ito.app.beans.AbacusAvailability;
import com.ito.app.execution.BargainFinderMaxExecution;
import com.ito.app.execution.SessionCreateExecution;
import com.ito.app.inquiry.AbacusSearchInquiry;
import com.snail.core.beans.DeliveryMap;

@RestController
@RequestMapping("get_fare")
public class SearchController {
	
	private LinkedBlockingDeque<AbacusSession> sessions;
	private String epr;
	private String ipcc;
	private String password;
	private String uri;
	private String conversationId;		
	
	public String getConversationId() {
		return conversationId;
	}

	public String getEpr() {
		return epr;
	}

	public String getIpcc() {
		return ipcc;
	}

	public String getPassword() {
		return password;
	}

	public String getUri() {
		return uri;
	}
	
	public SearchController()
	{
		sessions = new LinkedBlockingDeque<AbacusSession>();
		epr = "666";
		ipcc = "4ZB8";
		password = "COKLAT1";
		uri = "https://sws-tls.cert.sabre.com";
		conversationId = UUID.randomUUID().toString();
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DeliveryMap search(@RequestBody String search) throws Exception  {
		
		JsonHandler param = JsonHandler.parse(search) ;
		
		AbacusSearchInquiry inquiry = new AbacusSearchInquiry();
		inquiry.inquire(param);	
		
		BargainFinderMaxExecution bfm = new BargainFinderMaxExecution(inquiry, ipcc);
		bfm.run(this);
		DeliveryMap result = new DeliveryMap();	
		List<AbacusAvailability> listAv = bfm.getListAv();
		result.put("go", listAv);
		result.put("count", listAv.size());
		return result;
	}
	
	// NEW SESSIONS
	public AbacusSession createNewSession() throws Exception {
		SessionCreateExecution scExe = new SessionCreateExecution(this);
		String binarySecurityToken = scExe.getBinarySecurityToken();
		AbacusSession session = new AbacusSession(binarySecurityToken, uri, ipcc); 
		
		System.out.println("Bin :" + binarySecurityToken);
		
		return session;
	}
	
	// GET SESSION
	public AbacusSession getSession() throws Exception{
		//retrieve session is available
		AbacusSession session;
		
		while (!sessions.isEmpty()){
			session = sessions.pop();
			
			if(session.isValid()){
				return session;
			}
		}
		
		session = createNewSession();
		
		// create session
		return session;
	}
	
	// SAVE SESSION
	public boolean saveSession(AbacusSession session){
		if(session != null){
			sessions.push(session);
			
			return true;
		}
		return false;
	}
}
