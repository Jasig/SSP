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

import org.apache.commons.lang.StringUtils;
import org.jasig.portlet.utils.rest.CrossContextRestApiInvoker;
import org.jasig.portlet.utils.rest.RestResponse;
import org.jasig.portlet.utils.rest.SimpleCrossContextRestApiInvoker;
import org.jasig.ssp.util.SspStringUtils;
import org.jasig.ssp.util.http.HttpServletGetRequestWrapper;
import org.springframework.web.context.request.async.WebAsyncUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Keeps the current session alive. Useful for circumstances where servlet/api
 * calls are being made with few, or no, portlet calls.
 *
 * @author Chris Waymire <chris@waymire.net>
 */
public class KeepSessionAliveFilter implements Filter {
    private final String SESSION_KEEP_ALIVE_ATTRIBUTE_KEY = KeepSessionAliveFilter.class.getName() +"_SESSION_KEEP_ALIVE";
    private int interval = 10 * 60 * 1000; // DEFAULT TO 10 minutes (in milliseconds)
    public static final String OVERRIDE_INTERVAL_PARAM = "force_platform_session_keep_alive";

    /**
     * Set per-session interval in minutes after which the next request hitting
     * this filter will attempt to touch the uPortal session. To disable, set
     * to a value less than zero. To run on every request set to zero. Default
     * is 10 minutes.
     *
     * @param interval
     */
    public void setInterval(int interval)
    {
        this.interval = interval * 60 * 1000;
    }

    @Override
    public void init(final FilterConfig config) {
        // nothing to do
    }

    @Override
    public void destroy() {
        // nothing to do
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        boolean overrideInterval = isIntervalOverridden(request);
        if ( !(overrideInterval) && interval < 0 ) {
            chain.doFilter(request, response);
            return;
        }

        final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        final HttpSession session = httpServletRequest.getSession(false);
        if ( session == null ) {
            chain.doFilter(request, response);
            return;
        }

        Long lastUpdate = (Long)session.getAttribute(SESSION_KEEP_ALIVE_ATTRIBUTE_KEY);
        if( overrideInterval || lastUpdate != null)
        {
            if(overrideInterval || ((System.currentTimeMillis() - lastUpdate.longValue()) >= interval)) {
                final CrossContextRestApiInvoker rest = new SimpleCrossContextRestApiInvoker();
                //ensures request is GET going into REST Invoker
                HttpServletGetRequestWrapper wrap = new HttpServletGetRequestWrapper(httpServletRequest);
                final Object origWebAsyncManager = wrap.getAttribute(WebAsyncUtils.WEB_ASYNC_MANAGER_ATTRIBUTE);
                request.removeAttribute(WebAsyncUtils.WEB_ASYNC_MANAGER_ATTRIBUTE);
                try {
                    final Map<String, String[]> params = new HashMap<String, String[]>();
                    final RestResponse rr = rest.invoke(wrap, httpServletResponse, "/ssp-platform/api/session.json", params);
                    session.setAttribute(SESSION_KEEP_ALIVE_ATTRIBUTE_KEY,System.currentTimeMillis());
                } finally {
                    request.setAttribute(WebAsyncUtils.WEB_ASYNC_MANAGER_ATTRIBUTE, origWebAsyncManager);
                }
            }
        } else {
            session.setAttribute(SESSION_KEEP_ALIVE_ATTRIBUTE_KEY,System.currentTimeMillis());

        }

        chain.doFilter(request, response);
    }

    protected boolean isIntervalOverridden(ServletRequest request) {
        final String paramVal = StringUtils.trimToNull(request.getParameter(OVERRIDE_INTERVAL_PARAM));
        return paramVal != null && SspStringUtils.booleanFromString(paramVal);
    }
}
