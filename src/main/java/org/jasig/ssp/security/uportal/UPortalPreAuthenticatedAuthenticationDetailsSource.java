package org.jasig.ssp.security.uportal;

import java.util.Collection;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.j2ee.AbstractPreAuthenticatedAuthenticationDetailsSource;

import com.google.common.collect.Lists;

/**
 * This class may not be necessary. As near as I can tell it is not being used,
 * but I will leave it in place a little longer until I am sure the security
 * implementation is working (This class is currently not "wired in" to
 * anything)
 * 
 */
public class UPortalPreAuthenticatedAuthenticationDetailsSource extends
		AbstractPreAuthenticatedAuthenticationDetailsSource {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UPortalPreAuthenticatedAuthenticationDetailsSource.class);

	@Override
	protected Collection<String> getUserRoles(final Object context,
			final Set<String> mappableRoles) {

		final Authentication token = getTokenFromContext(context);

		final Collection<String> userRoles = Lists.newArrayList();
		for (GrantedAuthority auth : token.getAuthorities()) {
			userRoles.add(auth.getAuthority());
		}

		return userRoles;
	}

	private Authentication getTokenFromContext(
			final Object context) {

		Authentication token = null;

		if (context instanceof HttpServletRequest) {

			final HttpServletRequest request = (HttpServletRequest) context;
			final HttpSession session = request.getSession();
			token = (Authentication) session
					.getAttribute(UPortalSecurityFilter.AUTHENTICATION_TOKEN_KEY);

		} else if (context instanceof PortletRequest) {

			final PortletRequest request = (PortletRequest) context;
			final PortletSession session = request.getPortletSession();
			token = (Authentication) session
					.getAttribute(UPortalSecurityFilter.AUTHENTICATION_TOKEN_KEY);

		} else {
			LOGGER.error("Unsupported Context Type");
		}

		return token;
	}

}
