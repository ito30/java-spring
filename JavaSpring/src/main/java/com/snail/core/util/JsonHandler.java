package com.snail.core.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.snail.core.fault.Fault;
import com.snail.core.fault.ValidationFault;

/**
 * 
 * @author iman
 * 
 */
public class JsonHandler {

	private JsonObject json;

	// NUGROHO.H Add [2014-11-03]
	// GET JSON
	public JsonObject getJson() {
		return json;
	}

	// CONSTRUCTOR
	public JsonHandler(JsonObject json) {
		this.json = json;
	}

	// CONSTRUCTOR
	public JsonHandler() {
		this.json = new JsonObject();
	}

	// SET JSON
	public void set(JsonObject json) {
		this.json = json;
	}

	// GET AS JSON
	public JsonHandler getAsJson(String name) throws Fault {
		if (json.has(name)) {

			JsonElement element = json.get(name);

			if (element.isJsonObject()) {
				return new JsonHandler(element.getAsJsonObject());
			} else {
				return null;
			}
		} else {
			String message = name + " is missing";
			throw ValidationFault.create(message);
		}
	}

	// GET AS STRING
	public String getAsString(String name) throws Fault {
		if (has(name)) {
			return json.get(name).getAsString();
		} else {
			String message = name + " is missing";
			throw ValidationFault.create(message);
		}
	}

	// GET AS DOUBLES
	public double getAsDouble(String name) throws Fault {
		if (json.has(name)) {
			return json.get(name).getAsDouble();
		} else {
			String message = name + " is missing";
			throw ValidationFault.create(message);
		}
	}

	// NUGROHO.H Modify [2014-11-12]
	// GET AS INT
	public int getAsInt(String name) throws Fault {
		if (json.has(name)) {
			String string = json.get(name).getAsString();
			try {
				return StringUtil.toInt(string);
			} catch (NumberFormatException e){
				throw ValidationFault.create(name.substring(0,1).toUpperCase()+ name.substring(1,name.length()) + " invalid format number");
			}
		} else {
			return 0;
		}
	}

	// GET AS BOOLEAN
	public boolean getAsBoolean(String name) {
		return json.get(name).getAsBoolean();
	}

	// GET AS OBJECT
	public List<JsonHandler> getAsList(String name) {
		if (json.has(name)) {
			return toList(json.get(name));
		} else {
			return null;
		}
	}

	// GET AS LIST OF LIST
	public List<List<JsonHandler>> getAsListOfList(String name) {
		List<List<JsonHandler>> list = new ArrayList<List<JsonHandler>>();

		JsonElement e0 = json.get(name);

		if (e0.isJsonArray()) {
			for (JsonElement e : e0.getAsJsonArray()) {
				if (e.isJsonArray()) {
					list.add(toList(e.getAsJsonArray()));
				}
			}
		} else if (e0.isJsonObject()) {
			for (Map.Entry<String, JsonElement> entry : e0.getAsJsonObject()
					.entrySet()) {
				JsonElement e = entry.getValue();
				if (e.isJsonArray()) {
					list.add(toList(e.getAsJsonArray()));
				}
			}
		}

		return list;
	}

	// TO STRING LIST
	public List<String> getAsStringList(String name) {

		List<String> list = new ArrayList<String>();
		for (JsonElement e : json.getAsJsonArray(name)) {
			list.add(e.getAsString());
		}

		return list;

	}

	// GET AS DATE
	public Date getAsDate(String name, String dateFormat) throws Fault {
		String string = getAsString(name);
		try {
			return DateUtil.toDate(string, dateFormat);
		} catch (ParseException e) {
			String message = name + " not dateformat " + dateFormat;
			throw ValidationFault.create(message);
		}
	}

	// GET AS DATE
	public Date getAsDate(String name) throws Fault {
		return getAsDate(name, "yyyy-MM-dd");
	}

	// HAS
	public boolean has(String name) {
		boolean hasElement = json.has(name);
		if (hasElement) {
			return !json.get(name).isJsonNull();
		}
		return false;
	}

	// IS JSON ARRAY
	public boolean isJsonArray() {
		return json.isJsonArray();
	}

	// GET AS LIST
	public List<JsonHandler> getAsList() {
		return toList(json.getAsJsonObject());
	}

	// IS TRUE
	public boolean isTrue(String name) throws Fault {
		if (json.has(name)) {
			String string = json.get(name).getAsString();
			return StringUtil.isTrue(string);
		}
		return false;
	}
	
	// GET VALUE AS LIST
	public List<String> getValues()
	{
		List<String> data = new ArrayList<String>();
		for (Map.Entry<String,JsonElement> value : json.entrySet()) {
			data.add(value.getValue().toString());
		}
		return data;
	}

	// GET KEY AS LIST
	public List<String> getKeys()
	{
		List<String> data = new ArrayList<String>();
		for (Map.Entry<String,JsonElement> value : json.entrySet()) {
			data.add(value.getKey().toString());
		}
		return data;
	}

	// ADD
	public void add(String key, Object value) {
		if (value instanceof JsonHandler) {
			json.add(key, ((JsonHandler) value).json);
		} else if (value instanceof JsonElement) {
			json.add(key, (JsonElement) value);
		} else if (value instanceof Number) {
			json.addProperty(key, (Number) value);
		} else if (value instanceof Character) {
			json.addProperty(key, (Character) value);
		} else if (value instanceof Boolean) {
			json.addProperty(key, (Boolean) value);
		} else if (value == null) {
			json.add(key, (JsonElement) value);
		} else {
			json.addProperty(key, value.toString());
		}
	}
	
	public void addArray(String key, Object[] value) {
		JsonArray arr = new JsonArray();
        for (Object val : value) {
            JsonPrimitive element;
            if (val instanceof JsonHandler) {
            	 arr.add(((JsonHandler) val).getJson());
    		} else if (val instanceof JsonElement) {
    			arr.add((JsonElement)val);
    		} else {
    			if (val instanceof Number) {
        			element = new JsonPrimitive((Number)val);
        		} else if (val instanceof Character) {
        			element = new JsonPrimitive((Character)val);
        		} else if (val instanceof Boolean) {
        			element = new JsonPrimitive((Boolean)val);
        		} else {
        			element = new JsonPrimitive((String)val);
        		}
    			arr.add(element);
    		}
        }
        json.add(key, arr);
	}

	// TO STRING
	@Override
	public String toString() {
		return json.toString();
	}

	// TO PLAIN STRING
	public String toPlainString() {
		StringBuilder builder = new StringBuilder();

		for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
			builder.append(entry.getKey() + ": " + entry.getValue());
		}
		return builder.toString();
	}

	// ** FACTORY **

	// WRITE TO FILE
	public static void writeFile(JsonHandler jsonHandler, String filename) {
		boolean isSuccess = FileUtil.write(filename, jsonHandler.toString());

		if (isSuccess) {
			System.out.println("JsonHandler.writeToFile: " + filename);
		}
	}

	// PARSE FROM FILE
	public static JsonHandler read(String filename) {
		String raw = FileUtil.read(filename);
		if (raw == null) {
			return null;
		} else {
			return parse(raw);
		}
	}

	// TO JSON
	public static JsonHandler parse(String json) {
		JsonObject jsonObj = GsonUtil.parse(json).getAsJsonObject();
		return new JsonHandler(jsonObj);
	}

	// ELEMENT TO LIST
	public static List<JsonHandler> toList(JsonElement element) {
		if (element.isJsonArray()) {
			List<JsonHandler> list = new ArrayList<JsonHandler>();

			for (JsonElement e : element.getAsJsonArray()) {
				if (e.isJsonObject()) {
					JsonHandler handler = new JsonHandler(e.getAsJsonObject());
					list.add(handler);
				}
			}
			return list;
		} else if (element.isJsonObject()) {
			List<JsonHandler> list = new ArrayList<JsonHandler>();
			JsonObject obj = element.getAsJsonObject();
			for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
				JsonElement value = entry.getValue();

				if (value.isJsonObject()) {
					JsonHandler handler = new JsonHandler(
							value.getAsJsonObject());
					list.add(handler);
				}
			}

			return list;
		} else {
			return null;
		}
	}
}