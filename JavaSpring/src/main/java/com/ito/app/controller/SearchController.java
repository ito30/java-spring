package com.ito.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.ito.app.dto.search.Search;
import com.ito.app.service.SearchService;
import com.ito.bean.Upselling;
import com.ito.bean.Upselling.Type;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.fault.Fault;

@RestController
@RequestMapping("get_fare")
public class SearchController {
	
	@Autowired
	private SearchService service;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DeliveryMap search(@RequestBody Search search) throws Fault {
		return service.search(search);
	}

	private static abstract class Test implements Callable<Void> {
		
		@Override
	    public final Void call() throws Exception {
	      return callImpl();
	    }
		
		protected abstract Void callImpl() throws Exception;
	}
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(50);
		List<Future<Void>> processList = new ArrayList<Future<Void>>();
		
		for (int i = 0; i < 100; i++) {
			long from = new Date().getTime();
			
			Test t = new Test() {
				
				@Override
				protected Void callImpl() throws Exception {
					try {
						Upselling u = new Upselling();
						u.setAirline("MH");
						u.setType(Type.PERCENTAGE);
						u.setValue(0.7);
						
						List<Upselling> l = new ArrayList<Upselling>();
						l.add(u);
						
						Map<String, Object> data = new HashMap<String, Object>();
						data.put("departure", "CGK");
						data.put("arrival", "KUL");
						data.put("date", "2016-11-25");
						data.put("adult", "1");
						data.put("child", "0");
						data.put("infant", "0");
						data.put("upSellings", l);
//						data.put("_debug", "true");
						
						String url = "http://localhost:9000/get_fare";
						Gson gson = new Gson();
						
						System.out.println(
								HttpRequest
								.post(url)
								.contentType("application/json")
								.send(gson.toJson(data))
								.body());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return null;
				}
				
			};
			
			System.out.print(" " + (i+1));
			
			System.out.println(" " + (new Date().getTime() - from) + "ms");
			
			Future<Void> submit = executor.submit(t);
	        processList.add(submit);
		}
		
		for (Future<Void> future : processList) {
		    try {
		    	future.get();
		    } catch (InterruptedException e) {
		    	// TODO: logging
	            e.printStackTrace();
		    } catch (ExecutionException e) {
		    	// TODO: logging
	            e.printStackTrace();
		    }
		}
	}
}
