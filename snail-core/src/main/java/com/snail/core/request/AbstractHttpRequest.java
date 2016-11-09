package com.snail.core.request;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;

public abstract class AbstractHttpRequest {
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36";
	private Map<String, String> headers;
	private List<Cookie> listCookie;

	// CONSTRUCTOR
	public AbstractHttpRequest(String userAgent) {
		headers = new HashMap<String, String>();
		headers.put("User-Agent", userAgent);		
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");		
		headers.put("Accept-Language", "en-US,en;q=0.8");
		headers.put("Connection", "keep-alive");
		headers.put("Accept-Encoding", "gzip, deflate, sdch");
	}

	// CONSTRUCTOR
	public AbstractHttpRequest() {
		this(USER_AGENT);
	}

	// ADD COOKIE
	public void addCookie(Cookie cookie) {
		if (listCookie == null) {
			listCookie = new ArrayList<Cookie>();
		}

		listCookie.add(cookie);
	}

	public List<Cookie> getListCookie() {
		return listCookie;
	}

	// SET REFERER
	public void setReferer(String referer) {
		addHeader("Referer", referer);
	}

	// ADD HEADER
	public void addHeader(String key, String value) {
		if (value != null) {
			headers.put(key, value);
		}
	}

	// INIT HEADER
	public void injectHeader(HttpRequestBase request) {
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			request.addHeader(key, value);
		}
	}

	// CREATE HTTP REQUEST
	public abstract HttpRequestBase generate() throws Exception;

}
