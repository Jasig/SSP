package org.jasig.ssp.security.uportal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

public class UPortalPreAuthenticatedProcessingFilter extends
		AbstractPreAuthenticatedProcessingFilter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UPortalPreAuthenticatedProcessingFilter.class);

	@Override
	protected Object getPreAuthenticatedPrincipal(
			final HttpServletRequest request) {

		final HttpSession session = request.getSession();

		if (session == null) {
			throw new PreAuthenticatedCredentialsNotFoundException(
					"No http session established");
		}

		final Authentication token =
				(Authentication) session
						.getAttribute(UPortalSecurityFilter.AUTHENTICATION_TOKEN_KEY);

		if (token == null) {
			LOGGER.debug("No Uportal AUTHENTICATION_TOKEN_KEY attribute found in http session");
			return null;
		}

		if (token.getPrincipal() == null) {
			throw new PreAuthenticatedCredentialsNotFoundException(
					"No Uportal principal found for AUTHENTICATION_TOKEN_KEY");
		}

		LOGGER.debug("UPortal AUTHENTICATION_TOKEN_KEY {}",
				token.getPrincipal());
		return token.getPrincipal();
	}

	@Override
	protected Object getPreAuthenticatedCredentials(
			final HttpServletRequest request) {
		// credentials aren't passed from uportal, just the principal
		return "N/A";
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request,
			final HttpServletResponse response, final Authentication authResult) {

		// pull the Authentication token from the portlet session
		final HttpSession session = request.getSession();
		final Authentication sessionToken = (Authentication) session
				.getAttribute(UPortalSecurityFilter.AUTHENTICATION_TOKEN_KEY);

		// Recreate the PreAuthenticatedAuthenticationToken and set authorities
		final PreAuthenticatedAuthenticationToken token =
				new PreAuthenticatedAuthenticationToken(
						authResult.getPrincipal(), authResult.getCredentials(),
						sessionToken.getAuthorities());
		token.setAuthenticated(authResult.isAuthenticated());
		token.setDetails(authResult.getDetails());

		super.successfulAuthentication(request, response, token);
	}

}
