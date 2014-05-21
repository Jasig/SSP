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

package org.jasig.ssp.util.http;

import org.jasig.portlet.utils.rest.CrossContextRestApiInvoker;
import org.jasig.portlet.utils.rest.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Addresses <a href="https://issues.jasig.org/browse/SSP-2451">SSP-2451</a>.
 *
 * This is a straight copy paste of {@code org.jasig.portlet.utils.rest.SimpleCrossContextRestApiInvoker} v1.0.0-M3,
 * except for fixes to URI path component encoding in {@link #parseUriTuple(String, java.util.Map)}. No other way to
 * work around that problem since those methods are private.
 */
public class PatchedSimpleCrossContextRestApiInvoker implements CrossContextRestApiInvoker {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public RestResponse invoke(HttpServletRequest req, HttpServletResponse res, String uri) {
		final Map<String, String[]> params = Collections.emptyMap();
		return invoke(req, res, uri, params);
	}

	@Override
	public RestResponse invoke(HttpServletRequest req, HttpServletResponse res, String uri, Map<String, String[]> params) {

		// Assertions.
		if (req == null) {
			final String msg = "Argument 'req' cannot be null";
			throw new IllegalArgumentException(msg);
		}
		if (res == null) {
			final String msg = "Argument 'res' cannot be null";
			throw new IllegalArgumentException(msg);
		}
		if (uri == null) {
			final String msg = "Argument 'uri' cannot be null";
			throw new IllegalArgumentException(msg);
		}
		if (!uri.startsWith("/") || uri.indexOf("/", 1) == -1) {
			final String msg = "Argument 'uri' must begin with a '/' character, " +
					"followed by the contextName, followed by another '/' then " +
					"the URI within the specified context";
			throw new IllegalArgumentException(msg);
		}
		if (params == null) {
			final String msg = "Argument 'params' cannot be null";
			throw new IllegalArgumentException(msg);
		}

		log.debug("Invoking REST API at URI (before applying parameters):  {}", uri);

		try {
			final UriTuple uriTuple = parseUriTuple(uri, params);
			log.debug("Invoking REST API where contextName={} and URI={}",
					uriTuple.getContextName(), uriTuple.getUri());
			return doInvoke(req, res, uriTuple);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * Implementation
	 */

	private UriTuple parseUriTuple(String uri, Map<String, String[]> params) throws UnsupportedEncodingException {

		String requestUri = null;
		String queryString = "";  // default

		final int queryStringBegin = uri.indexOf("?");
		if (queryStringBegin == -1) {
			// Simple case -- no querystring
			requestUri = uri;
		} else {
			// Complex case -- uri+querystring
			requestUri = uri.substring(0, queryStringBegin);
			queryString = uri.substring(queryStringBegin);  // will already contain the '?' character
		}

		// Inject requestUri params
		for (Map.Entry<String, String[]> y : params.entrySet()) {
			final String token = "{" + y.getKey() + "}";
			final String[] values = y.getValue();
			switch (values.length) {
				case 0:
					// Strange -- I guess we omit it?
					while(requestUri.contains(token)) {
						// Don't use String.replaceAll b/c token looks like an illegal regex
						requestUri = requestUri.replace(token, "");
					}
					break;
				case 1:
					// This is healthy & normal
					final String inject = UriUtils.encodePathSegment(values[0], "UTF-8");
					while(requestUri.contains(token)) {
						// Don't use String.replaceAll b/c token looks like an illegal regex
						requestUri = requestUri.replace(token, inject);
					}
					break;
				default:
					// OOPS! --  can't have more than 1 value for a requestUri token
					final String msg = "Can't support multiple values for non-querystring URI token:  " + token;
					throw new IllegalArgumentException(msg);
			}
		}

		// Inject queryString params
		for (Map.Entry<String, String[]> y : params.entrySet()) {
			final String paramName = y.getKey();
			final String token = "{" + paramName + "}";
			if (queryString.contains(token)) {
				final StringBuilder value = new StringBuilder();
				for (String s : y.getValue()) {
					final String inject = URLEncoder.encode(s, "UTF-8");
					value.append(value.length() != 0 ? "&" : "");
					value.append(paramName).append("=").append(inject);
				}
				queryString = queryString.replace(token, value);
			}
		}

		// Split into contextName+uri
		final int contextSeparatorPos = requestUri.indexOf("/", 1);  // A valid input starts with a slash, followed by the contextName
		final String contextName = requestUri.substring(0, contextSeparatorPos);  // Includes leading '/'
		requestUri = requestUri.substring(contextSeparatorPos);  // Includes leading '/'

		return new UriTuple(contextName, requestUri + queryString);

	}

	private RestResponse doInvoke(HttpServletRequest req, HttpServletResponse res, UriTuple tuple) {

		try {
			ServletContext ctx = req.getSession().getServletContext()
					.getContext(tuple.getContextName());
			RequestDispatcher rd = ctx.getRequestDispatcher(tuple.getUri());
			HttpServletResponseWrapperImpl responseWrapper = new HttpServletResponseWrapperImpl(res);
			rd.include(req, responseWrapper);
			RestResponse rslt = new RestResponse(
					responseWrapper.getOutputAsString(),
					responseWrapper.getContentType());
			return rslt;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Nested Types
	 */

	private static final class HttpServletResponseWrapperImpl extends HttpServletResponseWrapper {

		private StringWriter writer = null;
		private ServletOutputStreamImpl outputStream = null;

		public HttpServletResponseWrapperImpl(HttpServletResponse res) {
			super(res);
		}

		@Override
		public ServletOutputStream getOutputStream() {
			if (writer != null) {
				final String msg = "The method HttpServletResponse.getWriter has " +
						"already been called;  call either getOutputStream or " +
						"getWriter, but not both";
				throw new IllegalStateException(msg);
			}
			outputStream = new ServletOutputStreamImpl();
			return outputStream;
		}

		@Override
		public PrintWriter getWriter() {
			if (outputStream != null) {
				final String msg = "The method HttpServletResponse.getOutputStream has " +
						"already been called;  call either getOutputStream or " +
						"getWriter, but not both";
				throw new IllegalStateException(msg);
			}
			writer = new StringWriter();
			return new PrintWriter(writer);
		}

		public String getOutputAsString() {
			if (writer == null && outputStream == null) {
				final String msg = "Neither HttpServletResponse.getWriter nor " +
						"HttpServletResponse.getOutputStream has not been called";
				throw new IllegalStateException(msg);
			}
			return writer != null
					? writer.toString()
					: outputStream.toString();
		}

		/*
		 * The methods above this line are implemented so the HttpServletResponseWrapperImpl
		 * can serve it's purpose.
		 *
		 * The methods below this line are implemented to prevent the underlying
		 * ServletResponse from being altered in unexpected/unwanted ways as a
		 * side effect of invoking the cross-context API.
		 */

		@Override
		public void flushBuffer() throws IOException {
			// JavaDoc:  "Forces any content in the buffer to be written to the
			// client. A call to this method automatically commits the response,
			// meaning the status code and headers will be written."
			//
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void reset() {
			// JavaDoc:  "Clears any data that exists in the buffer as well as
			// the status code and headers. If the response has been committed,
			// this method throws an IllegalStateException."
			//
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void resetBuffer() {
			// JavaDoc:  "Clears the content of the underlying buffer in the
			// response without clearing headers or status code. If the response
			// has been committed, this method throws an IllegalStateException."
			//
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void setBufferSize(int arg0) {
			// JavaDoc:  "Sets the preferred buffer size for the body of the
			// response. The servlet container will use a buffer at least as
			// large as the size requested.  [...]"
			//
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void setCharacterEncoding(String arg0) {
			/*
			 * There's some chance something should be done with this method,
			 * but I'm not sure what atm.
			 */
		}

		@Override
		public void setContentLength(int arg0) {
			// JavaDoc:  "Sets the length of the content body in the response
			// In HTTP servlets, this method sets the HTTP Content-Length
			// header."
			//
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void setContentType(String arg0) {
			/*
			 * There's some chance something should be done with this method,
			 * but I'm not sure what atm.
			 */
		}

		@Override
		public void setLocale(Locale arg0) {
			// JavaDoc:  "Sets the locale of the response, if the response has
			// not been committed yet. It also sets the response's character
			// encoding appropriately for the locale, if the character encoding
			// has not been explicitly set [...]"
			//
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void addCookie(Cookie arg0) {
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void addDateHeader(String arg0, long arg1) {
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void addHeader(String arg0, String arg1) {
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void addIntHeader(String arg0, int arg1) {
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void sendError(int arg0) throws IOException {
			/*
			 * There's some chance something should be done with this method,
			 * but I'm not sure what atm.
			 */
		}

		@Override
		public void sendError(int arg0, String arg1) throws IOException {
			/*
			 * There's some chance something should be done with this method,
			 * but I'm not sure what atm.
			 */
		}

		@Override
		public void sendRedirect(String arg0) throws IOException {
			/*
			 * There's some chance something should be done with this method,
			 * but I'm not sure what atm.
			 */
		}

		@Override
		public void setDateHeader(String arg0, long arg1) {
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void setHeader(String arg0, String arg1) {
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void setIntHeader(String arg0, int arg1) {
			// It seems we should potentially prevent this behavior on the
			// underlying response since it's effect outside the API invoker
			// could be unwanted... but how best to do that?  Leaving it as a
			// no-op for now.
		}

		@Override
		public void setStatus(int arg0) {
			/*
			 * There's some chance something should be done with this method,
			 * but I'm not sure what atm.
			 */
		}

		@Override
		public void setStatus(int arg0, String arg1) {
			/*
			 * There's some chance something should be done with this method,
			 * but I'm not sure what atm.
			 */
		}

	}

	private static final class ServletOutputStreamImpl extends ServletOutputStream {

		private final ByteArrayOutputStream enclosed = new ByteArrayOutputStream();

		@Override
		public void write(int b) throws IOException {
			enclosed.write(b);
		}

		@Override
		public String toString() {
			return enclosed.toString();
		}

	}

	private static final class UriTuple {
		private final String contextName;
		private final String uri;

		public UriTuple(String contextName, String uri) {
			this.contextName = contextName;
			this.uri = uri;
		}

		public String getContextName() {
			return contextName;
		}

		public String getUri() {
			return uri;
		}

	}

}

