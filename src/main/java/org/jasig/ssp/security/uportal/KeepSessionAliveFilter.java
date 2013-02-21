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

import org.jasig.portlet.utils.rest.CrossContextRestApiInvoker;
import org.jasig.portlet.utils.rest.RestResponse;
import org.jasig.portlet.utils.rest.SimpleCrossContextRestApiInvoker;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        Long lastUpdate = (Long)httpServletRequest.getSession(false).getAttribute(SESSION_KEEP_ALIVE_ATTRIBUTE_KEY);
        if(lastUpdate != null)
        {
            long delta = System.currentTimeMillis() - lastUpdate.longValue();
            if(delta >= interval) {
                final CrossContextRestApiInvoker rest = new SimpleCrossContextRestApiInvoker();
                final Map<String, String[]> params = new HashMap<String, String[]>();
                final RestResponse rr = rest.invoke(httpServletRequest, httpServletResponse, "/ssp-platform/api/session.json", params);
                httpServletRequest.getSession(false).setAttribute(SESSION_KEEP_ALIVE_ATTRIBUTE_KEY,System.currentTimeMillis());
            }
        } else {
            httpServletRequest.getSession(false).setAttribute(SESSION_KEEP_ALIVE_ATTRIBUTE_KEY,System.currentTimeMillis());
        }

        chain.doFilter(request, response);
    }
}
