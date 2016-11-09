package com.ito.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ito.app.dto.search.Search;
import com.ito.bean.AbacusAvailability;
import com.ito.out.search.BargainFinderMaxExecution;
import com.ito.out.session.SessionCreateExecution;
import com.snail.core.beans.DeliveryMap;

@Service
public class SearchService {
	
	private SessionCreateExecution session;
	
	public SearchService() {
		session = new SessionCreateExecution();
	}

	public DeliveryMap search(Search search) {
		BargainFinderMaxExecution bfm = new BargainFinderMaxExecution(
				search, session.getConfig().getIpcc());
		bfm.run(session);
		
		DeliveryMap result = new DeliveryMap();	
		List<AbacusAvailability> listAv = bfm.getListAv();
		result.put("go", listAv);
		result.put("count", listAv.size());
		
		return result;
	}
	
}
