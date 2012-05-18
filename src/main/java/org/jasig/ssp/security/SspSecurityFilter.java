package org.jasig.ssp.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

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

		final String principal = req.getRemoteUser();
		final SecurityContext ctx = SecurityContextHolder.getContext();

		if ((principal != null) && (ctx.getAuthentication() == null)) {

			// User is authenticated, but we haven't created the Spring context
			// yet
			LOGGER.debug("Setting up Spring Security context for user:  {}",
					principal);

			final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			for (AccessType y : AccessType.values()) {
				if (req.isUserInRole(y.getRoleName())) {
					authorities.add(new GrantedAuthorityImpl(y.getRoleName()));
				}
			}
			for (Role r : Role.values()) {
				if (req.isUserInRole(r.getRoleName())) {
					authorities.add(new GrantedAuthorityImpl(r.getRoleName()));
				}
			}

			final String credentials = req.getAuthType();
			final PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(
					principal, credentials, authorities);
			ctx.setAuthentication(token);

		}

		chain.doFilter(req, res);
	}
}
