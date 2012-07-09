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
