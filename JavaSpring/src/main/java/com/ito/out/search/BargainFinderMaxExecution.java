package com.ito.out.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ito.app.dto.search.Search;
import com.ito.bean.AbacusAvailability;
import com.ito.bean.AbacusSegment;
import com.ito.bean.AbacusTrip;
import com.ito.bean.Upselling;
import com.ito.common.SoapHandler;
import com.ito.out.AbstractAbacusExecution;
import com.ito.out.AbstractAbacusRequest;
import com.snail.core.beans.DeliveryMap;
import com.snail.core.beans.TFlightSelect;

public class BargainFinderMaxExecution extends AbstractAbacusExecution{

	private Search inquiry;
	private List<AbacusAvailability> listAv;	
	private List<DeliveryMap> results;
	private String ipcc;
	
	// CONSTRUCTOR
	public BargainFinderMaxExecution(
			Search inquiry, 		
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
	@Override
	public void onRunDone(SoapHandler result) throws Exception {
		List<SoapHandler> listSoapJourney = result.findAsList("PricedItinerary");
		listAv = new ArrayList<AbacusAvailability>();
		if(listSoapJourney.size()>0){
			Map<String, String> paxMap = new HashMap<String, String>();
			paxMap.put("adultCount", Integer.toString(inquiry.getAdult()));
			paxMap.put("childCount", Integer.toString(inquiry.getChild()));
			paxMap.put("infantCount", Integer.toString(inquiry.getInfant()));
			for (SoapHandler soapJourney : listSoapJourney) {
				AbacusAvailability aa = AbacusAvailability.create(soapJourney, paxMap, inquiry.getUpsellings());
				if( !aa.isMarkAsRemoved() ){
					listAv.add(aa);
					String arrival = inquiry.getArrival();
					if(inquiry.isRoundTrip()){
						Upselling upSelling = new Upselling();
						for (Upselling _upSelling : inquiry.getUpsellings()) {
							if (_upSelling.getAirline().equals(aa.getValidatingCarrier())) {
								upSelling = _upSelling;
								break;
							}
						}
						Upselling returnUpselling = new Upselling();
						for (Upselling _upselling : inquiry.getReturnUpsellings()) {
							if (_upselling.getAirline().equals(aa.getValidatingCarrier())) {
								returnUpselling = _upselling;
								break;
							}
						}
						AbacusAvailability dep = AbacusAvailability.getDepartAvailability(aa, arrival, 0.5, upSelling);
						AbacusAvailability ret = AbacusAvailability.getReturnAvailability(aa, arrival, 0.5, returnUpselling);
						if( dep == null || ret == null )
							continue;
						List<AbacusAvailability> avList = new ArrayList<AbacusAvailability>();
						avList.add(dep);
						avList.add(ret);
						AbacusTrip trip = AbacusTrip.createFromList(avList);
						DeliveryMap map = new DeliveryMap();
						map.put("depart", dep);
						map.put("return", ret);
						map.put("trip", trip);
						results.add(map);
					}
				}
			}
		}
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