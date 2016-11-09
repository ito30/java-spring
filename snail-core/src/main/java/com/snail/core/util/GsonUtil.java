package com.snail.core.util;


import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.snail.core.beans.CommonMap;

public class GsonUtil {

	public static Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting()
			.disableHtmlEscaping().create();
	public static Gson GSON = new Gson();
	public static JsonParser PARSER = new JsonParser();

	// TO JSON FORMAT
	public static String format(Map<String, Object> map) {
		return GSON.toJson(map);
	}

	// TO PRETTY JSON FORMAT
	public static String prettyFormat(Map<String, Object> map) {
		return GSON_PRETTY.toJson(map);
	}

	// OBJECT TO JSON
	public static JsonElement parse(Object object) {
		if (object instanceof String) {
			return PARSER.parse((String) object);
		} else if (object instanceof JsonElement) {
			return (JsonElement) object;
		} else if (object instanceof CommonMap) {
			Map<String, Object> map = ((CommonMap) object).getMap();
			return PARSER.parse(GSON.toJson(map));
		} else {
			return PARSER.parse(GSON.toJson(object));
		}
	}

}

