package com.snail.core.beans;

import java.util.ArrayList;
import java.util.List;

import com.ito.app.controller.JsonHandler;
import com.snail.core.fault.Fault;
import com.snail.core.util.StringUtil;


/**
 * @author NUGROHO.H-DEV028
 *
 */
// NUGROHO.H Add [2014-11-20]
public class TFareSellKeySelect {

	private List<String> list;

	// ADD
	public void add(String fareSellKeySelect) {
		if (list == null) {
			list = new ArrayList<String>();
		}

		list.add(fareSellKeySelect);
	}

	// GET LIST
	public List<String> getList() {
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
	public int indexOf(String fareSellKeySelect) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals(fareSellKeySelect)) {
					return i;
				}
			}
		}

		return -1;
	}

	// FIRST
	public String first() {
		return get(0);
	}

	public boolean isFirst(String fareSellKeySelect) {
		return get(0).equals(fareSellKeySelect);
	}

	// GET
	public String get(int i) {
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
				builder.append("^");
			}

			String fareSellKeySelect = list.get(i);
			builder.append(fareSellKeySelect);

		}

		return builder.toString();
	}

	// ** FACTORY **

	// CREATE
	public static TFareSellKeySelect create(String fareSellKeySelects) {
		String[] flightChunks = fareSellKeySelects.split("\\|");

		TFareSellKeySelect select = new TFareSellKeySelect();
		for (String string : flightChunks) {
			string = string.trim();
			if (!StringUtil.isNullOrEmpty(string)) {
				select.add(string);
			}
		}
		return select;
	}

	// CREATE
	public static TFareSellKeySelect create(JsonHandler json, String name)
			throws Fault {
		String fareSellKeySelect = json.getAsString(name);

		return create(fareSellKeySelect);

	}
	
	public static String createFareSellKeySelectString(
			List<String> listFareSellKey) {
		StringBuilder builder = new StringBuilder();

		if (listFareSellKey != null) {
			for (int i = 0; i < listFareSellKey.size(); i++) {
				String fareSellKey = listFareSellKey.get(i);

				if (i > 0) {
					builder.append("|");
				}

				builder.append(fareSellKey);
			}
		}

		return builder.toString();
	}

	// IS SELECTED
	public boolean isMatched(List<String> listFareSellKey) {
		int listSegmentSize = listFareSellKey.size();

		if (size() == listSegmentSize) {
			for (int i = 0; i < listSegmentSize; i++) {
				String fareSellKey = listFareSellKey.get(i);
				String fareSellKey0 = get(i);

				if (!fareSellKey.equals(fareSellKey0)) {
					return false;
				}
			}

			return true;
		}

		return false;
	}
}
