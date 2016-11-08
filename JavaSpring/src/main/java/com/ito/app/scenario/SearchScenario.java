package com.ito.app.scenario;

import java.util.List;

import com.ito.app.beans.AbacusAvailability;
import com.ito.app.controller.ServiceController;
import com.ito.app.execution.BargainFinderMaxExecution;
import com.ito.app.inquiry.AbacusSearchInquiry;
import com.snail.core.beans.Deliverable;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.util.JsonHandler;

public class SearchScenario {
	
	public Deliverable run(ServiceController controller,JsonHandler param) throws Exception
	{
		AbacusSearchInquiry inquiry = new AbacusSearchInquiry();
		inquiry.inquire(param);	
		
		BargainFinderMaxExecution bfm = new BargainFinderMaxExecution(inquiry, controller.getIpcc());
		bfm.run(controller);
		DeliveryMap result = new DeliveryMap();	
		List<AbacusAvailability> listAv = bfm.getListAv();
		result.put("go", listAv);
		result.put("count", listAv.size());
		return result;
	}

}
