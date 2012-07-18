package org.jasig.ssp.security.uportal;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import org.jasig.portal.api.permissions.Assignment;
import org.jasig.portal.api.permissions.PermissionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * This Security Filter, for use with Spring Security, implements
 * {@link RenderFilter} When SSP is used with uportal, this class populates the
 * session with the user's username and authorities for consumption by the http
 * servlet api.
 */
public final class UPortalSecurityFilter implements RenderFilter {

	public static final String AUTHENTICATION_TOKEN_KEY =
			UPortalSecurityFilter.class.getName() + ".GRANTED_AUTHORITIES_KEY";
	private static final Object SSP_OWNER = "SSP";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UPortalSecurityFilter.class);

	@Override
	public void doFilter(final RenderRequest req, final RenderResponse res,
			final FilterChain chain) throws IOException, PortletException {

		final String principal = req.getRemoteUser();

		if (principal != null) { // User is authenticated

			final PortletSession session = req.getPortletSession();

			@SuppressWarnings("unchecked")
			final Set<GrantedAuthority> authorities = (Set<GrantedAuthority>)
					session.getAttribute(AUTHENTICATION_TOKEN_KEY);

			if (authorities == null) {
				populateAuthorites(req, principal);
			}
		}

		chain.doFilter(req, res);
	}

	private void populateAuthorites(final RenderRequest req, final String principal) {

		// But the user's access has not yet been established...
		final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		PortletContext ctx = req.getPortletSession(true).getPortletContext();
		PermissionsService permissionsService = (PermissionsService) 
				ctx.getAttribute(PermissionsService.PORTLET_CONTEXT_ATTRIBUTE_NAME);

		Set<Assignment> assignments = permissionsService.getAssignmentsForPerson(principal, true);

		// Find SSP-related permissions in the assignments collection
		for (Assignment a : assignments) {
			if (a.getOwner().getKey().equals(SSP_OWNER)) {
				// This one pertains to us...
				String activity = a.getActivity().getKey();
				authorities.add(new GrantedAuthorityImpl("ROLE_" + activity));
			}
		}

		LOGGER.debug("Setting up GrantedAutorities for user '{}' -- {}",
				principal, authorities.toString());

		// Create Authentication Token
		final PreAuthenticatedAuthenticationToken token =
				new PreAuthenticatedAuthenticationToken(
						principal,
						"credentials",
						Collections.unmodifiableSet(authorities));

		// Add Authentication Token to Session
		final PortletSession session = req.getPortletSession();
		session.setAttribute(AUTHENTICATION_TOKEN_KEY, token,
				PortletSession.APPLICATION_SCOPE);
	}

	@Override
	public void init(final FilterConfig config) throws PortletException {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}

}
