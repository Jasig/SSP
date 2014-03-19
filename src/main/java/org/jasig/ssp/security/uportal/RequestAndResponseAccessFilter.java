/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.security.uportal;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Provides ThreadLocal-based access to the {@link HttpServletRequest} and
 * {@link HttpServletResponse} for APIs that need them.
 * 
 * @author awills
 */
public class RequestAndResponseAccessFilter implements Filter {

	private transient final ThreadLocal<HttpServletRequest> requests = new ThreadLocal<HttpServletRequest>();

	private transient final ThreadLocal<HttpServletResponse> responses = new ThreadLocal<HttpServletResponse>();

	@Override
	public void init(final FilterConfig config) {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {

		requests.set((HttpServletRequest) req);
		responses.set((HttpServletResponse) res);
		Object asyncManager = req.getAttribute("org.springframework.web.context.request.async.WebAsyncManager.WEB_ASYNC_MANAGER");
		req.removeAttribute("org.springframework.web.context.request.async.WebAsyncManager.WEB_ASYNC_MANAGER");
		
		chain.doFilter(req, res);

		requests.remove();
		responses.remove();

	}

	public HttpServletRequest getHttpServletRequest() {
		return requests.get();
	}

	public HttpServletResponse getHttpServletResponse() {
		return responses.get();
	}

}
