package com.ito.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ito.app.dto.search.Search;
import com.ito.bean.AbacusAvailability;
import com.ito.out.SabreCommandLLSExecution;
import com.ito.out.search.BargainFinderMaxExecution;
import com.ito.out.session.SessionCreateExecution;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.fault.Fault;
import com.snail.core.util.DateUtil;
import com.snail.core.util.Timer;

@Service
public class SearchService {
	
	// FIXME this should be autowired
	private SessionCreateExecution session;
	
	public SearchService() {
		session = new SessionCreateExecution();
	}

	public DeliveryMap search(Search search, long workerId, Timer duration) throws Fault {
		
		String debugPath = "snail_logs/abacus/"+ DateUtil.now("yyyy/MMMM/dd") + "/" + DateUtil.currentUnixTimestamp() + "_" 
				+ this.getClass().getSimpleName();
		
		session.setDebug(search.is_debug());
		
//		SabreCommandLLSExecution cmd1 = new SabreCommandLLSExecution("OVHE/ROUT*");
//		cmd1.run(debugPath, session);
//		SabreCommandLLSExecution cmd2 = new SabreCommandLLSExecution("OVHE/ROUT/RAVL/HCC-CERT");
//		cmd2.run(debugPath, session);
//		SabreCommandLLSExecution cmd3 = new SabreCommandLLSExecution("OVHE/ROUT/RPNR/HCC-CERT");
//		cmd3.run(debugPath, session);
//		SabreCommandLLSExecution cmd4 = new SabreCommandLLSExecution("OVHE/ROUT/SPNR/HCC-CERT");
//		cmd4.run(debugPath, session);
//		SabreCommandLLSExecution cmd5 = new SabreCommandLLSExecution("OVHE/ROUT/RETP/HCC-CERT");
//		cmd5.run(debugPath, session);
//		SabreCommandLLSExecution cmd6 = new SabreCommandLLSExecution("OVHE/ROUT/RETK/HCC-CERT");
//		cmd6.run(debugPath, session);
//		SabreCommandLLSExecution cmd7 = new SabreCommandLLSExecution("OVHE/ROUT/CPNR/HCC-CERT");
//		cmd7.run(debugPath, session);
//		SabreCommandLLSExecution cmd8 = new SabreCommandLLSExecution("TTY-ON/SEND");
//		cmd8.run(debugPath, session);
		
		BargainFinderMaxExecution bfm = new BargainFinderMaxExecution(search);
		bfm.run(debugPath, session);
		
		DeliveryMap result = new DeliveryMap();	
		if (search.isRoundTrip()) 
		{
			List<DeliveryMap> results = bfm.getResults();
			result.put("go", results);
			result.put("count", results.size());
		}
		else
		{
			List<AbacusAvailability> listAv = bfm.getListAv();
			result.put("go", listAv);
			result.put("count", listAv.size());
		}	
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(DateUtil.now("MM/dd kk:mm"));
		strBuilder.append("\t->");
		strBuilder.append("\t#" + workerId);
		strBuilder.append("\t" + duration.diffAtMSec() + " msec");
		System.out.println(strBuilder);
		return result;
	}
	
}
