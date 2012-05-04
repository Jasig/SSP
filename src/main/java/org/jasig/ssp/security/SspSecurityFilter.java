package org.jasig.ssp.security;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class SspSecurityFilter implements RenderFilter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig arg0) throws PortletException {}

	@Override
	public void doFilter(RenderRequest req, RenderResponse res, FilterChain chain) throws IOException, PortletException {

		String username = req.getRemoteUser();
		if (username != null) {

			// User is authenticated so we need a permissions colection...
			log.debug("Analyzing UserPermissions for user:  {}", username);

			UserPermissions perms = (UserPermissions) req.getPortletSession(true).getAttribute(SimplePermissionService.USER_PERMISSIONS_KEY);
			if (perms == null) {
				// First time, set it up...
				log.info("Creating new UserPermissions for user:  {}", username);
				perms = UserPermissions.createUserPermissions(req);
				req.getPortletSession(true).setAttribute(SimplePermissionService.USER_PERMISSIONS_KEY, perms, PortletSession.APPLICATION_SCOPE);
			}

		}
		
		chain.doFilter(req, res);

	}

}
