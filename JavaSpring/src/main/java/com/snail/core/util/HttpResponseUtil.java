package com.snail.core.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ito.app.controller.ResponseDetail;

public class HttpResponseUtil {

	// **

	public static ResponseDetail getResponseDetail(
			CloseableHttpResponse response) {
		StatusLine statusLine = response.getStatusLine();
		Header[] headers = response.getAllHeaders();

		ResponseDetail resDetail = new ResponseDetail();
		resDetail.setStatusLine(statusLine);
		resDetail.setHeaders(headers);

		return resDetail;
	}

	// TO BASE 64
	public static String getBase64(HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			byte[] bytes = EntityUtils.toByteArray(entity);
			return Base64.encodeBase64String(bytes);
		} else {
			throw new Exception("Http entity is missing");
		}
	}

	// GET RESPONSE AS DOCUMENT
	public static Document getDocument(HttpResponse response) throws Exception {

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String string = IOUtils.toString(entity.getContent());
			return Jsoup.parse(string);
		} else {
			throw new Exception("Http entity is missing");
		}
	}

	// GET STRING
	public static String getString(HttpResponse response) throws Exception {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			return IOUtils.toString(entity.getContent());
		} else {
			return null;
		}
	}

	// GET SCRIPT
	public static String getScript(Document doc, String cssQuery) {
		Elements scriptTags = doc.select(cssQuery);
		for (Element tag : scriptTags) {
			for (DataNode node : tag.dataNodes()) {
				return node.getWholeData();
			}
		}

		return null;
	}
}
