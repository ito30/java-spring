package com.ito.app.execution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ito.app.beans.AbacusAvailability;
import com.ito.app.beans.AbacusSegment;
import com.ito.app.beans.AbacusTrip;
import com.ito.app.beans.Upselling;
import com.ito.app.inquiry.AbacusSearchInquiry;
import com.ito.app.request.AbstractAbacusRequest;
import com.ito.app.request.BargainFinderMaxRequest;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.beans.TFlightSelect;
import com.snail.core.util.SoapHandler;

import rx.Observable;
import rx.schedulers.Schedulers;

public class BargainFinderMaxExecution extends AbstractAbacusExecution{

	private AbacusSearchInquiry inquiry;
	private List<SoapHandler> listSoapJourney;
	private List<AbacusAvailability> listAv;	
	private List<DeliveryMap> results;
	private String ipcc;
	
	// CONSTRUCTOR
	public BargainFinderMaxExecution(
			AbacusSearchInquiry inquiry, 		
			String ipcc
			) {
		this.inquiry = inquiry;			
		this.ipcc = ipcc;
		this.results = new ArrayList<DeliveryMap>();
	}
	
	@Override
	public AbstractAbacusRequest createRequest() throws Exception {
		return new BargainFinderMaxRequest(ipcc, inquiry);
	}

	// ON RUN
//	@Override
//	public void onRunDone(SoapHandler result) throws Exception {
//		listSoapJourney = result.findAsList("PricedItinerary");
//		listAv = new ArrayList<AbacusAvailability>();
//		
//		if(listSoapJourney.size()>0){
//			Map<String, String> paxMap = new HashMap<String, String>();
//			paxMap.put("adultCount", Integer.toString(inquiry.getAdult()));
//			paxMap.put("childCount", Integer.toString(inquiry.getChild()));
//			paxMap.put("infantCount", Integer.toString(inquiry.getInfant()));
//			for (SoapHandler soapJourney : listSoapJourney) {
//				AbacusAvailability aa = AbacusAvailability.create(soapJourney, paxMap, inquiry.getUpsellings());
//				if( !aa.isMarkAsRemoved() ){
//					listAv.add(aa);
//					String arrival = inquiry.getArrival();
//					if(inquiry.isRoundTrip()){
//						Upselling upSelling = new Upselling();
//						for (Upselling _upSelling : inquiry.getUpsellings()) {
//							if (_upSelling.getAirline().equals(aa.getValidatingCarrier())) {
//								upSelling = _upSelling;
//								break;
//							}
//						}
//						Upselling returnUpselling = new Upselling();
//						for (Upselling _upselling : inquiry.getReturnUpsellings()) {
//							if (_upselling.getAirline().equals(aa.getValidatingCarrier())) {
//								returnUpselling = _upselling;
//								break;
//							}
//						}
//						AbacusAvailability dep = AbacusAvailability.getDepartAvailability(aa, arrival, 0.5, upSelling);
//						AbacusAvailability ret = AbacusAvailability.getReturnAvailability(aa, arrival, 0.5, returnUpselling);
//						if( dep == null || ret == null )
//							continue;
//						List<AbacusAvailability> avList = new ArrayList<AbacusAvailability>();
//						avList.add(dep);
//						avList.add(ret);
//						AbacusTrip trip = AbacusTrip.createFromList(avList);
//						DeliveryMap map = new DeliveryMap();
//						map.put("depart", dep);
//						map.put("return", ret);
//						map.put("trip", trip);
//						results.add(map);
//					}
//				}
//			}
//		}
//	}	
	
	@Override
	public void onRunDone(SoapHandler result) throws Exception {
		listSoapJourney = result.findAsList("PricedItinerary");
		listAv = new ArrayList<AbacusAvailability>();
		
		Map<String, String> paxMap = new HashMap<String, String>();
		paxMap.put("adultCount", Integer.toString(inquiry.getAdult()));
		paxMap.put("childCount", Integer.toString(inquiry.getChild()));
		paxMap.put("infantCount", Integer.toString(inquiry.getInfant()));
		
		Observable.from(listSoapJourney)	
		.filter((soapJourney) -> {
			try {
				return !AbacusAvailability.create(soapJourney, paxMap, inquiry.getUpsellings()).isMarkAsRemoved();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
		})
		.subscribe((soapJourney) -> {
			
			try {
				AbacusAvailability aa = AbacusAvailability.create(soapJourney, paxMap, inquiry.getUpsellings());
				listAv.add(aa);
			} catch (Exception e) {
				
			}			
		});
	}	
	
	public List<DeliveryMap> getResults() {
		return results;
	}

	public List<AbacusAvailability> getListAv() {
		return listAv;
	}
	
	public AbacusAvailability getAv(TFlightSelect select) {
		for (AbacusAvailability av : listAv) 
		{
			List<AbacusSegment> listSegment = av.getListSegment();

			if (select.isMatched(listSegment)) 
			{
				return av;
			}
		}
		return null;
	}
}