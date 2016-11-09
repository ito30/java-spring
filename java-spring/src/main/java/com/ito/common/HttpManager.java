package com.ito.common;


import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.snail.core.request.AbstractHttpRequest;

public class HttpManager {

	public static final int TIMEOUT = 150 * 1000;

	private CloseableHttpClient httpClient;
	public static final String username = "lum-customer-tiket-zone-gen-country-us";
    public static final String password = "8263841daaae";
    public static final int port = 22225;
    public static HttpHost super_proxy;    

	// GET HTTP CLIENT
	public CloseableHttpClient getHttpClient() {
		if (httpClient == null) {
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					createRegistry());
			connManager.setDefaultMaxPerRoute(200);
			connManager.setMaxTotal(400);

			httpClient = HttpClients.custom()
					.setConnectionManager(connManager)
					.build();
		}

		return httpClient;
	}

	// CREATE REGISTRY
	public Registry<ConnectionSocketFactory> createRegistry() {
		SSLContext sslcontext;
		X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();

		SSLContextBuilder sslcb = new SSLContextBuilder();
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			sslcb.loadTrustMaterial(keyStore, new TrustStrategy() {

				@Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1)
						throws CertificateException {

					return true;
				}
			});
			sslcontext = sslcb.build();
		} catch (Exception e) {
			sslcontext = SSLContexts.createSystemDefault();
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register(
						"https",
						new SSLConnectionSocketFactory(sslcontext,
								new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"},
						        null,
						        hostnameVerifier)).build();

		return socketFactoryRegistry;

	}

	// RUN
	public CloseableHttpResponse run(HttpRequestBase request,
			CookieStore cookieStore) throws Exception {

		// CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpClient client = getHttpClient();

		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(TIMEOUT)
				.setConnectTimeout(TIMEOUT)
				.setConnectionRequestTimeout(TIMEOUT)
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();

		HttpClientContext localContext = HttpClientContext.create();
		localContext.setRequestConfig(config);

		if (cookieStore != null) {
			localContext.setCookieStore(cookieStore);
		}

		CloseableHttpResponse response = client.execute(request, localContext);
		return response;

	}
	
	// RUN
	// timeout per second
	public CloseableHttpResponse run(HttpRequestBase request,
			CookieStore cookieStore, int timeoutt) throws Exception {

		// CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpClient client = getHttpClient();
		int timeout = timeoutt * 1000;
		
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout)
//				.setStaleConnectionCheckEnabled(true) //testing Garuda
				.setSocketTimeout(timeout)
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();

		HttpClientContext localContext = HttpClientContext.create();
		localContext.setRequestConfig(config);

		if (cookieStore != null) {
			localContext.setCookieStore(cookieStore);
		}

		CloseableHttpResponse response = client.execute(request, localContext);
		return response;

	}
	

	// RUN
	public CloseableHttpResponse run(AbstractHttpRequest request,
			CookieStore cookieStore) throws Exception {
		CloseableHttpClient client = getHttpClient();

		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(TIMEOUT)
				.setConnectTimeout(TIMEOUT)
				.setConnectionRequestTimeout(TIMEOUT)
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();

		HttpClientContext localContext = HttpClientContext.create();
		localContext.setRequestConfig(config);

		if (cookieStore != null) {

			List<Cookie> listCookie = request.getListCookie();
			if (listCookie != null) {
				for (Cookie cookie : listCookie) {
					cookieStore.addCookie(cookie);
				}
			}

			localContext.setAttribute(HttpClientContext.COOKIE_STORE,
					cookieStore);
		}

		HttpRequestBase httpRequestBase = request.generate();
		CloseableHttpResponse response = client.execute(httpRequestBase,
				localContext);
		return response;
	}

	// CLEAN QUITELY
	public void cleanQuitely() {

		if (httpClient != null) {
			try {
				httpClient.close();
			} catch (IOException e) {
			}
		}
	}

	// ** SINGLETON **

	private static class SingletonHolder {
		public static HttpManager INSTANCE = new HttpManager();
	}

	public static HttpManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

}

