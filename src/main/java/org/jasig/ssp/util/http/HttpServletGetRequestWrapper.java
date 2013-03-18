package org.jasig.ssp.util.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpServletGetRequestWrapper extends HttpServletRequestWrapper {
	
	public HttpServletGetRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public String getMethod(){
		return "GET";
	}
}
