package com.encima.utils;

import org.restlet.Response;
import org.restlet.engine.header.Header;
import org.restlet.util.Series;

public class APITools {

	public static void addAccessToHeader(Response resp) {
		Series<Header> responseHeaders = (Series<Header>) resp.getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    resp.getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
	}
	
}
