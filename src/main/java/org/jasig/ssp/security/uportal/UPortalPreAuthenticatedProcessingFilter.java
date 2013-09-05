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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

/**
 * UPortal pre-authenticated processing filter.
 * <p>
 * Couldn't extend AbstractPreAuthenticatedProcessingFilter to the proper
 * degree, so pulling a bunch of code from it.
 * 
 * @see org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
 * @author daniel.bower
 */
public class UPortalPreAuthenticatedProcessingFilter extends GenericFilterBean
		implements InitializingBean, ApplicationEventPublisherAware {

	private ApplicationEventPublisher eventPublisher = null;

	private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

	private AuthenticationManager authenticationManager = null;

	private boolean continueFilterChainOnUnsuccessfulAuthentication = true;

	private boolean checkForPrincipalChanges;

	private boolean invalidateSessionOnPrincipalChange = true;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UPortalPreAuthenticatedProcessingFilter.class);

	@Override
	public void doFilter(final ServletRequest request,
			final ServletResponse response,
			final FilterChain chain)
			throws IOException, ServletException {

		if (logger.isDebugEnabled()) {
			logger.debug("Checking secure context token: "
					+ SecurityContextHolder.getContext().getAuthentication());
		}

		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		final PreAuthenticatedAuthenticationToken preAuthToken = getPreAuthenticatedToken(httpServletRequest);

		if (requiresAuthentication(httpServletRequest, preAuthToken)) {
			doAuthenticate(httpServletRequest, httpServletResponse,
					preAuthToken);
		}

		chain.doFilter(request, response);
	}

	private PreAuthenticatedAuthenticationToken getPreAuthenticatedToken(
			final HttpServletRequest request) {

		final HttpSession session = request.getSession(false);

		if (session == null) {
			LOGGER.debug("No HttpSession so skipping Uportal "
					+ "AUTHENTICATION_TOKEN_KEY attribute lookup");
			return null;
		}

		final PreAuthenticatedAuthenticationToken token =
				(PreAuthenticatedAuthenticationToken) session
						.getAttribute(UPortalSecurityFilter.AUTHENTICATION_TOKEN_KEY);

		if (token == null) {
			LOGGER.debug("No Uportal AUTHENTICATION_TOKEN_KEY attribute found in http session");
			return null;
		} else {
			return token;
		}
	}

	private boolean requiresAuthentication(final HttpServletRequest request,
			final Authentication preAuthToken) {

		final Authentication currentUser = SecurityContextHolder.getContext()
				.getAuthentication();

		if ((currentUser == null) || (preAuthToken == null)) {
			return true;
		}

		if (checkForPrincipalChanges &&
				!currentUser.getName().equalsIgnoreCase((String)preAuthToken.getPrincipal())) {
			logger.debug("Pre-authenticated principal has changed to "
					+ preAuthToken.getPrincipal()
					+ " and will be reauthenticated");

			if (invalidateSessionOnPrincipalChange) {
				final HttpSession session = request.getSession(false);

				if (session != null) {
					logger.debug("Invalidating existing session");
					session.invalidate();
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * Do the actual authentication for a pre-authenticated user.
	 */
	private void doAuthenticate(final HttpServletRequest request,
			final HttpServletResponse response,
			final PreAuthenticatedAuthenticationToken preAuthToken) {

		if (preAuthToken == null) {
			logger.debug("No preauth token found in session");
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("preAuthenticatedPrincipal = "
					+ preAuthToken.getPrincipal()
					+ ", trying to authenticate");
		}

		try {
			preAuthToken.setDetails(authenticationDetailsSource
					.buildDetails(request));
			final Authentication authResult = authenticationManager
					.authenticate(preAuthToken);
			successfulAuthentication(request, response, authResult);
		} catch (final AuthenticationException failed) {
			unsuccessfulAuthentication(request, response, failed);

			if (!continueFilterChainOnUnsuccessfulAuthentication) {
				throw failed;
			}
		}
	}

	/**
	 * Puts the <code>Authentication</code> instance returned by the
	 * authentication manager into the secure context.
	 */
	protected void successfulAuthentication(final HttpServletRequest request,
			final HttpServletResponse response, final Authentication authResult) {
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication success: " + authResult);
		}

		SecurityContextHolder.getContext().setAuthentication(authResult);
		// Fire event
		if (eventPublisher != null) {
			eventPublisher
					.publishEvent(new InteractiveAuthenticationSuccessEvent(
							authResult, this.getClass()));
		}
	}

	/**
	 * Ensures the authentication object in the secure context is set to null
	 * when authentication fails.
	 */
	protected void unsuccessfulAuthentication(final HttpServletRequest request,
			final HttpServletResponse response,
			final AuthenticationException failed) {
		SecurityContextHolder.clearContext();

		logger.warn("Cleared security context due to exception", failed);

		request.getSession().setAttribute(
				WebAttributes.AUTHENTICATION_EXCEPTION, failed);
	}

	/**
	 * @param anApplicationEventPublisher
	 *            The ApplicationEventPublisher to use
	 */
	@Override
	public void setApplicationEventPublisher(
			final ApplicationEventPublisher anApplicationEventPublisher) {
		eventPublisher = anApplicationEventPublisher;
	}

	/**
	 * @param authenticationDetailsSource
	 *            The AuthenticationDetailsSource to use
	 */
	public void setAuthenticationDetailsSource(
			final AuthenticationDetailsSource authenticationDetailsSource) {
		Assert.notNull(authenticationDetailsSource,
				"AuthenticationDetailsSource required");
		this.authenticationDetailsSource = authenticationDetailsSource;
	}

	/**
	 * @param authenticationManager
	 *            The AuthenticationManager to use
	 */
	public void setAuthenticationManager(
			final AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public boolean isContinueFilterChainOnUnsuccessfulAuthentication() {
		return continueFilterChainOnUnsuccessfulAuthentication;
	}

	public void setContinueFilterChainOnUnsuccessfulAuthentication(
			final boolean shouldContinue) {
		continueFilterChainOnUnsuccessfulAuthentication = shouldContinue;
	}

	public boolean isCheckForPrincipalChanges() {
		return checkForPrincipalChanges;
	}

	/**
	 * If set, the pre-authenticated principal will be checked on each request
	 * and compared against the name of the current <tt>Authentication</tt>
	 * object. If a change is detected, the user will be reauthenticated.
	 * 
	 * @param checkForPrincipalChanges
	 *            should principal changes be checked or not
	 */
	public void setCheckForPrincipalChanges(
			final boolean checkForPrincipalChanges) {
		this.checkForPrincipalChanges = checkForPrincipalChanges;
	}

	public boolean isInvalidateSessionOnPrincipalChange() {
		return invalidateSessionOnPrincipalChange;
	}

	/**
	 * If <tt>checkForPrincipalChanges</tt> is set, and a change of principal is
	 * detected, determines whether any existing session should be invalidated
	 * before proceeding to authenticate the new principal.
	 * 
	 * @param invalidateSessionOnPrincipalChange
	 *            <tt>false</tt> to retain the existing session. Defaults to
	 *            <tt>true</tt>.
	 */
	public void setInvalidateSessionOnPrincipalChange(
			final boolean invalidateSessionOnPrincipalChange) {
		this.invalidateSessionOnPrincipalChange = invalidateSessionOnPrincipalChange;
	}
}