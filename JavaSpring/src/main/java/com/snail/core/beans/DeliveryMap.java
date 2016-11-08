package com.snail.core.beans;

import java.util.ArrayList;
import java.util.List;

public class DeliveryMap extends CommonMap implements Deliverable {

	@Override
	public void put(String key, Object value) {
		if (value instanceof CommonMap) {
			super.put(key, ((CommonMap) value).getMap());
		} else if (value instanceof Deliverable) {
			super.put(key, ((Deliverable) value).deliver().getMap());
		} else if (value instanceof List<?>) {
			super.put(key, expand((List<?>) value));
		} else {
			super.put(key, value);
		}
	}

	public static List<Object> expand(List<?> list) {
		List<Object> newList = new ArrayList<Object>();

		for (Object item : list) {
			if (item instanceof Deliverable) {
				newList.add((((Deliverable) item)).deliver().getMap());
			} else if (item instanceof CommonMap) {
				newList.add(((CommonMap) item).getMap());
			} else {
				newList.add(item);
			}
		}

		return newList;
	}

	// DELIVER
	@Override
	public DeliveryMap deliver() {
		return this;
	}

	// ** FACTORY **
	public static DeliveryMap singleEntry(String key, Object value) {
		DeliveryMap map = new DeliveryMap();
		map.put(key, value);

		return map;
	}

}
