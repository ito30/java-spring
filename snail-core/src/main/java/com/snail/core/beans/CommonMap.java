package com.snail.core.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.snail.core.util.DateUtil;
import com.snail.core.util.StringUtil;

public class CommonMap {
	private Map<String, Object> map;

	// CONSTRUCTOR
	public CommonMap() {
		this(null);
	}

	// CONSTRUCTOR
	public CommonMap(Map<String, Object> map) {
		this.map = map;
	}

	// PUT
	public void put(String key, Object value) {
		if (map == null) {
			map = new LinkedHashMap<String, Object>();
		}
		map.put(key, value);
	}

	// PUT AS MAP
	public void put(String key, CommonMap commonMap) {
		put(key, convertToAllMap(commonMap));
	}

	// PUT DATE
	public boolean put(String key, Date date, String dateFormat) {
		if (date != null) {
			put(key, DateUtil.format(date, dateFormat));
			return true;
		}

		return false;
	}

	// ENTRYSET
	public Set<Map.Entry<String, Object>> entrySet() {
		if (map != null) {
			return map.entrySet();
		}
		return null;
	}

	// GET MAP
	public Map<String, Object> getMap() {
		return map;
	}

	// GET
	public Object get(String name) {
		if (map != null) {
			return map.get(name);
		}
		return null;
	}

	// GET AS STRING
	public String getAsString(String name) {
		if (map != null && map.containsKey(name)) {
			Object obj = map.get(name);

			if (obj != null) {
				return obj.toString();
			}
		}
		return null;

	}

	// GET AS DOUBLE
	public double getAsDouble(String name) {
		String string = getAsString(name);
		if (string != null) {
			return StringUtil.toDouble(string);
		}
		return 0;
	}

	// HAS
	public boolean has(String key) {
		if (map != null) {
			return map.containsKey(key);
		}

		return false;

	}

	// SIZE
	public int size() {
		if (map != null) {
			return map.size();
		}
		return 0;
	}

	// ** FACTORY **

	// SINGLE ENTRY
	public static CommonMap singleEntry(String key, Object value) {
		CommonMap map = new CommonMap();
		map.put(key, value);

		return map;
	}

	// CONVERT TO ALL MAP
	public static Map<String, Object> convertToAllMap(CommonMap commonMap) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		for (Map.Entry<String, Object> entry : commonMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof CommonMap) {
				map.put(key, convertToAllMap((CommonMap) value));
			} else if (value instanceof List) {

				List<Object> newList = new ArrayList<Object>();
				for (Object item : (List<?>) value) {
					if (item instanceof CommonMap) {
						newList.add(convertToAllMap((CommonMap) item));
					} else {
						newList.add(item);
					}
				}

				map.put(key, newList);

			} else {
				map.put(key, value);
			}
		}

		return map;
	}
}
