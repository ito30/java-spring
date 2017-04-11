package com.ito.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ito.app.beans.AbacusSession;
import com.ito.app.execution.SessionCreateExecution;
import com.ito.app.scenario.SearchScenario;
import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.util.DateUtil;
import com.snail.core.util.FileUtil;
import com.snail.core.util.JsonHandler;
import com.snail.core.util.Timer;

@RestController
public class ServiceController {
	
	private LinkedBlockingDeque<AbacusSession> sessions;
	private String epr;
	private String ipcc;
	private String password;
	private String uri;
	private String conversationId;	
	private int workerId;
	private String envTarget;
	private List<String> prefAirline;	
	private double commissionTax;
	
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
	
	public ServiceController()
	{
		sessions = new LinkedBlockingDeque<AbacusSession>();
		conversationId = UUID.randomUUID().toString();
		workerId = 0;
		
		StringBuilder builder = new StringBuilder();
		String version = "v0.0.25";
		String instanceName = "abacus";
		double worker = 100;
		String port = "8080";
		String serviceName = getClass().getSimpleName();

		builder.append("\n\nAirlines Engagement System (c) 2013 TIKET.COM\n\n");
		builder.append("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
		builder.append("Service Name: " + serviceName + "\n");
		builder.append("Instance: " + instanceName + "\n");
		builder.append("Version: " + version + "\n");
		builder.append("Listen to port " + port + "\n");
		builder.append("Maximum " + worker + " concurrent process\n");
		builder.append("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");

		System.out.println(builder);
		
		try {
			prepare();
		} catch (Exception e) {

		}
		
		System.out.println(DateUtil.now("MM/dd kk:mm")
				+ "\t Server is READY\n");
	}
	
	private void prepare() throws Exception {
		JsonHandler config = JsonHandler.parse(FileUtil.read("config.json"));
		
		JsonHandler abacusJson = config.getAsJson("abacus");
		envTarget = abacusJson.getAsString("env_target");
		
		JsonHandler uriJson = abacusJson.getAsJson("env").getAsJson(envTarget);
		
		JsonHandler accountJson = uriJson.getAsJson("account");
		uri = uriJson.getAsString("url");
		
		epr = accountJson.getAsString("epr");
		password = accountJson.getAsString("password");
		ipcc = accountJson.getAsString("ipcc");
		
		prefAirline = new ArrayList<String>();
		String []temp = uriJson.getAsString("airline").split(",");
		for (int i = 0; i < temp.length; i++) {
			prefAirline.add(temp[i]);
		}

		if( uriJson.has("commission_tax") ){
			commissionTax = uriJson.getAsDouble("commission_tax");			
		}
		else
		{
			commissionTax = 0;
		}		
	}
	
	private void printIncomingRequest(int workerId, String endPoint, String param)
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(DateUtil.now("MM/dd kk:mm"));
		strBuilder.append("\t<-");
		strBuilder.append("\t#" + workerId);
		strBuilder.append("\t" + endPoint);
		strBuilder.append("\t" + param);			
		System.out.println(strBuilder);
	}
	
	private void printOutgoingResponse(int workerId, long msec)
	{
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(DateUtil.now("MM/dd kk:mm"));
		strBuilder.append("\t->");
		strBuilder.append("\t#" + workerId);
		strBuilder.append("\t" + msec + " msec");
		System.out.println(strBuilder);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, value="/get_fare")
	public @ResponseBody Deliverable search(@RequestBody String search) throws Exception  {
		Timer duration = Timer.createByNow();
		int workerId = ++this.workerId;
		printIncomingRequest(workerId, "get_fare", search);		
		JsonHandler param = JsonHandler.parse(search) ;		
		SearchScenario searching = new SearchScenario();	
		DeliveryMap result = (DeliveryMap) searching.run(this, param);
		printOutgoingResponse(workerId, duration.diffAtMSec());
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
