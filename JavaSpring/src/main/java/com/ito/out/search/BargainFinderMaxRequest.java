package com.ito.out.search;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;

import com.ito.App;
import com.ito.app.dto.search.Search;
import com.ito.out.AbstractAbacusRequest;

public class BargainFinderMaxRequest extends AbstractAbacusRequest {

	private static final String VERSION = "1.9.5";
	private Search search;
	
	public BargainFinderMaxRequest(Search search) {
		super("BargainFinderMaxRQ");
		this.search = search;
	}
	
	@Override
	public void initEnvelope(SOAPEnvelope env) throws Exception {
		// TODO Auto-generated method stub
		super.initEnvelope(env);
		env.addNamespaceDeclaration(NS, BFM_NS_URI);
	}
	
	// INIT BODY
		@Override
		public void initBodyMessage(SOAPElement body) throws Exception {

			String departure = search.getDeparture();
			String arrival = search.getArrival();
			String date = search.getDate("yyyy-MM-dd");		

			int paxCount = search.getPaxCount();			
			
			SOAPElement otaAirLowFareSearchRQ = body.addChildElement("OTA_AirLowFareSearchRQ", NS);
			otaAirLowFareSearchRQ.addAttribute(new QName( "Version"), VERSION);
			
			SOAPElement source = otaAirLowFareSearchRQ.addChildElement("POS", NS)
												   .addChildElement("Source", NS)
												   .addAttribute(
														   new QName( "PseudoCityCode"), 
														   App.config
														   .getEnv_target()
														   .getEnv()
														   .getAccount()
														   .getIpcc());
			
			SOAPElement requestorID = source.addChildElement("RequestorID", NS)		   
			   								.addAttribute(new QName( "Type"), "1")
											.addAttribute(new QName( "ID"), "1");
			
			SOAPElement companyName = requestorID.addChildElement("CompanyName", NS)		   
				.addAttribute(new QName( "Code"), "TN");
			companyName.addTextNode("TN");
			
			SOAPElement originDestinationInformation = otaAirLowFareSearchRQ.addChildElement("OriginDestinationInformation", NS)
																			.addAttribute(new QName( "RPH"), "1");
			originDestinationInformation.addChildElement("DepartureDateTime", NS)
			 .addTextNode(date + "T11:00:00");		
			
			originDestinationInformation.addChildElement("OriginLocation", NS)
			.addAttribute(new QName( "LocationCode"), departure);
			originDestinationInformation.addChildElement("DestinationLocation", NS)
			.addAttribute(new QName( "LocationCode"), arrival);
			originDestinationInformation.addChildElement("TPA_Extensions", NS)
			.addChildElement("SegmentType", NS);
					
			SOAPElement travelPreferences = otaAirLowFareSearchRQ.addChildElement("TravelPreferences", NS);						
			
			travelPreferences.addChildElement("FlightTypePref", NS)				
			.addAttribute(new QName( "MaxConnections"), "2");
						
			travelPreferences.addChildElement("CabinPref", NS)
			.addAttribute(new QName( "PreferLevel"), "Preferred")
			.addAttribute(new QName( "Cabin"), "Y");
			travelPreferences.addChildElement("TPA_Extensions", NS)
			.addChildElement("TripType", NS)
			.addAttribute(new QName( "Value"), "Return");
			
			SOAPElement travelInfoSummary = otaAirLowFareSearchRQ.addChildElement("TravelerInfoSummary", NS);
			travelInfoSummary.addChildElement("SeatsRequested", NS)
			.addTextNode( String.valueOf( paxCount));
			SOAPElement airTravelerAvail = travelInfoSummary.addChildElement("AirTravelerAvail", NS);
			
			if( search.hasAdult() )
			{
				airTravelerAvail.addChildElement("PassengerTypeQuantity", NS)
				.addAttribute(new QName( "Code"), "ADT")
				.addAttribute(new QName( "Quantity"), String.valueOf( search.getAdult()));
			}
			
			if( search.hasChild() )
			{
				airTravelerAvail.addChildElement("PassengerTypeQuantity", NS)
				.addAttribute(new QName( "Code"), "CNN")
				.addAttribute(new QName( "Quantity"), String.valueOf( search.getChild()));
			}
			
			if( search.hasInfant() )
			{
				airTravelerAvail.addChildElement("PassengerTypeQuantity", NS)
				.addAttribute(new QName( "Code"), "INF")
				.addAttribute(new QName( "Quantity"), String.valueOf( search.getInfant()));
			}			
			
			otaAirLowFareSearchRQ.addChildElement("TPA_Extensions", NS)
			.addChildElement("IntelliSellTransaction", NS)
			.addChildElement("RequestType", NS)
			.addAttribute(new QName( "Name"), "200ITINS");
										
		}
	
}
