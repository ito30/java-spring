package com.snail.core.beans;

import java.util.ArrayList;
import java.util.List;

import com.snail.core.fault.Fault;
import com.snail.core.util.JsonHandler;
import com.snail.core.util.StringUtil;

public class TFlightSelect {

	private List<TFlightDesignator> list;

	// ADD
	public void add(TFlightDesignator flight) {
		if (list == null) {
			list = new ArrayList<TFlightDesignator>();
		}

		list.add(flight);
	}

	// GET LIST
	public List<TFlightDesignator> getList() {
		return list;
	}

	// SIZE
	public int size() {
		if (list != null) {
			return list.size();
		}

		return 0;
	}

	// IS SAME SIZE
	public boolean isSameSize(int size) {
		return size == size();
	}

	// INDEX OF
	public int indexOf(TSegment segment) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(segment)) {
					return i;
				}
			}
		}

		return -1;
	}

	// FIRST
	public TFlightDesignator first() {
		return get(0);
	}

	public boolean isFirst(TFlightDesignator designator) {
		return get(0).equals(designator);
	}

	// GET
	public TFlightDesignator get(int i) {
		if (list != null) {
			return list.get(i);
		}

		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				builder.append("|");
			}

			TFlightDesignator designator = list.get(i);
			builder.append(designator);

		}

		return builder.toString();
	}

	// ** FACTORY **

	// CREATE
	public static TFlightSelect create(String flights) {
		String[] flightChunks = flights.split("\\|");

		TFlightSelect select = new TFlightSelect();
		for (String string : flightChunks) {
			string = string.trim();
			if (!StringUtil.isNullOrEmpty(string)) {
				select.add(TFlightDesignator.create(string));
			}
		}
		return select;
	}
	
	// NUGROHO HACK
	// CREATE Hack Sriwijaya (String flights)
	public static TFlightSelect createHackSriwijaya(String flights) {
		TFlightSelect select = new TFlightSelect();
		select.add(TFlightDesignator.create(flights));
		return select;
	}

	// CREATE
	public static TFlightSelect create(JsonHandler json, String name)
			throws Fault {
		String flights = json.getAsString(name);

		return create(flights);

	}
	
	// CREATE ROUND TRIP
	public static List<TFlightSelect> createRoundTrip(JsonHandler json, String name)
			throws Fault {
		String flights = json.getAsString(name);
		
		String[] flightSelectStr = flights.split("\\~");
		
		List<TFlightSelect> flightSelect = new ArrayList<TFlightSelect>();
		
		for (String flightStr : flightSelectStr) {
			flightSelect.add(create(flightStr));
		}

		return flightSelect;
	}


	// **
	
	// NUGROHO HACK
	// CREATE Hack Sriwijaya (JsonHandler json, String name)
	public static TFlightSelect createHackSriwijaya(JsonHandler json, String name)
			throws Fault {
		String flights = json.getAsString(name);
		flights = flights.replaceAll("\\|", "/");
		return createHackSriwijaya(flights);

	}

	// **
	
	public static String createFlightSelectString(
			List<? extends TSegment> listSegment) {
		StringBuilder builder = new StringBuilder();

		if (listSegment != null) {
			for (int i = 0; i < listSegment.size(); i++) {
				TSegment leg = listSegment.get(i);

				// NUGROHO.H Add [2014-12-09]
				if(!checkDuplicate(leg, listSegment, i) || i==0){
					if (i > 0) {
						builder.append("|");
					}
					builder.append(leg.getFlightDesignatorAsString());
				}
			}
		}

		return builder.toString();
	}

	// NUGROHO.H Add checkDuplicate [2014-12-09]
	public static boolean checkDuplicate(TSegment legTemp, List<? extends TSegment> listSegment, int i){
		for (int j=0; j < i; j++) {
			if (legTemp.isSameFlight(listSegment.get(j).getDesignator()))
				return true;
		}
		return false;
	}
	
	// NUGROHO.H Add isDuplicateSegment [2014-12-09]
	public static boolean isDuplicateSegment(List<? extends TSegment> listSegment){
		int i=0;
		int j=0;
		for(TSegment segment1 : listSegment){
			j=0;
			for(TSegment segment2 : listSegment){
				if(segment1.isSameFlight(segment2.getDesignator()) && i!=j){
					return true;
				}
				j++;
			}
			i++;
		}
		return false;
	}
	
	// IS SELECTED
	public boolean isMatched(List<? extends TSegment> listSegment) {
		String tempFlightSelect = createFlightSelectString(listSegment);
		
		if(toString().equals(tempFlightSelect)) {
			return true;
		} else {
			return false;
		}
	}
}
