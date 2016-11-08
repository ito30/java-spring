package com.snail.core.beans;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.snail.core.fault.Fault;
import com.snail.core.util.JsonHandler;
import com.snail.core.util.StringUtil;

public class TJourneySellKeySelect {

	private List<TJourneySellKeyDesignator> list;
	private String journeySellKey;

	// ADD
	public void add(TJourneySellKeyDesignator journeySellKeyDesignator) {
		if (list == null) {
			list = new ArrayList<TJourneySellKeyDesignator>();
		}

		list.add(journeySellKeyDesignator);
	}

	// GET LIST
	public List<TJourneySellKeyDesignator> getList() {
		return list;
	}
	
	public String[] getArrayKey() {
		return journeySellKey.split("\\^");
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

	// FIRST
	public TJourneySellKeyDesignator first() {
		return get(0);
	}

	// GET
	public TJourneySellKeyDesignator get(int i) {
		if (list != null) {
			return list.get(i);
		}

		return null;
	}

	// ** FACTORY **

	// CREATE
	public static TJourneySellKeySelect create(String journeySellKeySelect) throws ParseException {
		String[] flightChunks = journeySellKeySelect.split("\\^");

		TJourneySellKeySelect select = new TJourneySellKeySelect();
		select.setKey(journeySellKeySelect);
		for (String string : flightChunks) {
			string = string.trim();
			if (!StringUtil.isNullOrEmpty(string)) {
				select.add(TJourneySellKeyDesignator.create(string));
			}
		}
		return select;
	}

	// CREATE
	public static TJourneySellKeySelect create(JsonHandler json, String name)
			throws Fault, ParseException {
		String journeySellKeySelect = json.getAsString(name);

		return create(journeySellKeySelect);

	}

	public String getKey() {
		return journeySellKey;
	}

	public void setKey(String journeySellKey) {
		this.journeySellKey = journeySellKey;
	}
	
	
}
