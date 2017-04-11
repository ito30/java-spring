package com.snail.core.beans;


import org.apache.http.Header;
import org.apache.http.StatusLine;

import com.snail.core.util.StringUtil;

public class ResponseDetail {

	private StatusLine statusLine;
	private Header[] headers;

	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	public StatusLine getStatusLine() {
		return statusLine;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public String getHeader(String name) {
		for (Header header : headers) {
			String headerName = header.getName();

			if (StringUtil.isSame(headerName, name)) {
				return header.getValue();
			}
		}

		return null;

	}

	// GET REDIRECT LOCATION
	public boolean isRedirectTo(String expectedLocation) {
		if (statusLine.getStatusCode() == 302) {
			String location = getHeader("Location");

			return StringUtil.isSame(location, expectedLocation);
		}

		return false;
	}

	public String getRedirectLocation() {
		if (statusLine.getStatusCode() == 302) {
			return getHeader("Location");
		}

		return null;
	}

}
