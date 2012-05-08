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

/**
 * SSP security filter
 */
public final class SspSecurityFilter implements RenderFilter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SspSecurityFilter.class);

	@Override
	public void destroy() {
		// nothing to do
	}

	@Override
	public void init(final FilterConfig arg0) throws PortletException {
		// nothing to do
	}

	@Override
	public void doFilter(final RenderRequest req, final RenderResponse res,
			final FilterChain chain) throws IOException, PortletException {

		final String username = req.getRemoteUser();
		if (username != null) {
			// User is authenticated so we need a permissions collection...
			LOGGER.debug("Analyzing UserPermissions for user:  {}", username);

			UserPermissions perms = (UserPermissions) req.getPortletSession(
					true).getAttribute(
					SimplePermissionService.USER_PERMISSIONS_KEY);
			if (perms == null) {
				// First time, set it up...
				LOGGER.info("Creating new UserPermissions for user:  {}",
						username);
				perms = UserPermissions.createUserPermissions(req);
				req.getPortletSession(true).setAttribute(
						SimplePermissionService.USER_PERMISSIONS_KEY, perms,
						PortletSession.APPLICATION_SCOPE);
			}

		}

		chain.doFilter(req, res);
	}
}
