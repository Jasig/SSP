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
package org.jasig.ssp.util.security.lti;


import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasig.ssp.security.exception.UserNotAuthorizedException;
import org.jasig.ssp.security.exception.UserNotEnabledException;
import org.jasig.ssp.service.impl.PersonAttributesSearchException;
import org.jasig.ssp.service.security.lti.ConsumerDetailsDisabledException;
import org.jasig.ssp.service.security.lti.ConsumerDetailsNotFoundException;
import org.jasig.ssp.transferobject.LtiConsumerTestTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth.common.OAuthConsumerParameter;
import org.springframework.security.oauth.common.signature.UnsupportedSignatureMethodException;
import org.springframework.security.oauth.provider.InvalidOAuthParametersException;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Centralized error handling for both the LTI OAuth filter and processing that occurs downstream.
 * Has to extend {@link OAuthProcessingFilterEntryPoint} to satisfy {@link OAuthProviderProcessingFilter}.
 * Implementing {@link AuthenticationEntryPoint} is not sufficient.
 */
@Service("ltiLaunchErrorHandler")
public class LtiLaunchErrorHandler extends OAuthProcessingFilterEntryPoint {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LtiLaunchErrorHandler.class);

	public static enum LaunchMode {
		LIVE, TEST
	}

	private static final String LTI_LAUNCH_PRESENTATION_RETURN_URL = "lti_launch_presentation_return_url";
	private static final String LTI_ERROR_MSG = "lti_errormsg";
	private static final String LIVE_LAUNCH_URI_PATH_FRAGMENT = "/lti/launch/live";
	private static final String TEST_LAUNCH_URI_PATH_FRAGMENT = "/lti/launch/test";

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		handleLaunchError(request, response, authException, null);
	}

	public void handleLaunchError(HttpServletRequest request, HttpServletResponse response, Exception launchError, LaunchMode launchMode) throws IOException, ServletException {

		final ErrorResponse errorResponse = buildLaunchErrorResponse(request, response, launchError);

		if ( launchMode == null ) {
			launchMode = detectLaunchMode(request);
		}

		logErrorResponse(errorResponse, launchMode);

		switch ( launchMode ) {
			case TEST:
				respondTest(request, response, errorResponse);
				break;
			case LIVE:
			default:
				respondLive(request, response, errorResponse);
				break;
		}

	}

	private void respondLive(HttpServletRequest request, HttpServletResponse response, ErrorResponse errorResponse) throws IOException {
		final String returnUrl = request.getParameter(LTI_LAUNCH_PRESENTATION_RETURN_URL);
		if (StringUtils.isNotBlank(returnUrl)){
			final StringBuilder sb = new StringBuilder(returnUrl)
					.append("?")
					.append(LTI_ERROR_MSG)
					.append("=")
					.append(URLEncoder.encode(errorResponse.endUserMessage, "UTF-8"));
			response.sendRedirect(sb.toString());
		} else {
			response.setStatus(errorResponse.statusCode);
			final PrintWriter out = response.getWriter();
			final StringBuilder sb = new StringBuilder("<html><body><p>").append(errorResponse.endUserMessage).append("</p></body></html>");
			out.println(sb.toString());
		}
	}

	private void respondTest(HttpServletRequest request, HttpServletResponse response, ErrorResponse errorResponse) throws IOException {
		final LtiConsumerTestTO testResult = new LtiConsumerTestTO();
		testResult.setResult_code("FAILURE"); // D2L spec requires OK or FAILURE
		testResult.setResult_description(errorResponse.loggableMessage);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(response.getOutputStream(), testResult);
		response.getOutputStream().flush();
	}

	private void logErrorResponse(ErrorResponse errorResponse, LaunchMode launchMode) {
		if ( launchMode == LaunchMode.TEST || errorResponse.statusCode >= 500 ) {
			LOGGER.error(errorResponse.loggableMessage, errorResponse.launchError);
		} else {
			LOGGER.warn(errorResponse.loggableMessage, errorResponse.launchError);
		}
	}

	private ErrorResponse buildLaunchErrorResponse(HttpServletRequest request, HttpServletResponse response, Exception launchError) {
		final UUID errorId = UUID.randomUUID();
		final ErrorResponse errResponse = new ErrorResponse(errorId, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, launchError);

		if ( launchError instanceof UserNotEnabledException) {
			// This means the launching user's account just couldn't be found.
			// This is as opposed to some other system-level error either
			// in the OAuth infrastructure (including signature validation failures)
			// or in the calls to Platform
			errResponse.statusCode = HttpServletResponse.SC_FORBIDDEN;
			errResponse.endUserMessage = endUserErrorMessageWithId("Your user account was not found.", errorId);
			errResponse.loggableMessage = loggableErrorMessageWithId("User account not found in Platform.", errorId);
		} else if ( launchError instanceof ConsumerDetailsNotFoundException ) {
			// The LtiConsumer record couldn't be found *during OAuth validation*
			// This is as opposed to that record going missing during downstream
			// processing, which is represented by UserNotAuthorizedException (below)
			errResponse.statusCode = HttpServletResponse.SC_FORBIDDEN;
			errResponse.endUserMessage = endUserErrorMessageWithId("Could not authenticate this launch request. " +
					"This is likely a configuration problem that will require your system administrator's attention.", errorId);
			errResponse.loggableMessage = loggableErrorMessageWithId("LTI Consumer record could not be found during the launch request's OAuth validation step.", errorId);
		} else if ( launchError instanceof ConsumerDetailsDisabledException) {
			// The LtiConsumer record was found during OAuth validation but was
			// disabled in one way or another.
			errResponse.statusCode = HttpServletResponse.SC_FORBIDDEN;
			errResponse.endUserMessage = endUserErrorMessageWithId("Could not authenticate this launch request. " +
					"This is likely a configuration problem that will require your system administrator's attention.", errorId);
			errResponse.loggableMessage = loggableErrorMessageWithId("LTI Consumer record is disabled - usually because of a soft-deletion or a deleted secret.", errorId);
		} else if ( launchError instanceof UserNotAuthorizedException ) {
			// This means there was some sort of infrastructural problem with the
			// LTI authentication context - either the LTI consumer was not
			// set properly into the context after validation or there was some concurrent
			// modification to that consumer record which is now wreaking havoc.
			// Chances are, it was a server-side problem sp we leave the 500-series
			// status code alone
			errResponse.endUserMessage = endUserErrorMessageWithId("Could not authenticate this launch request. " +
							"This is likely a configuration problem that will require your system administrator's attention.", errorId);
			errResponse.loggableMessage = loggableErrorMessageWithId("Authentication context became invalid downstream from OAuth validation.", errorId);
		} else if ( launchError instanceof PersonAttributesSearchException ) {
			// This means there was a system-level faulure looking up the end
			// user in Platform. Not that the user doesn't exist - that there
			// was a technical issue with the lookup itself. This is fundamentally
			// a server-side problem so we leave the 500-series status code alone.
			errResponse.endUserMessage = endUserErrorMessageWithId("Encountered technical difficulties looking up your user account. " +
							"This is likely a configuration problem that will require your system administrator's attention.", errorId);
			errResponse.loggableMessage = loggableErrorMessageWithId("System failure trying to look up launching user account in Platform.", errorId);
		} else if (launchError instanceof AuthenticationException) {
			// OAuthException sometimes just wraps AuthenticationServiceException, which means
			// it should be a 500-series. Else an OAuth failure would always be a 400 series
			if (launchError instanceof AuthenticationServiceException ||
					launchError.getCause() instanceof AuthenticationServiceException) {
				errResponse.endUserMessage = endUserErrorMessageWithId("Encountered technical difficulties validating this request. " +
						"This is likely a configuration problem that will require your system administrator's attention.", errorId);
				errResponse.loggableMessage = loggableErrorMessageWithId("System failure trying to validate OAuth parameters.", errorId);
			} else if (launchError instanceof InvalidOAuthParametersException) { // special case called out in spec
				errResponse.statusCode = HttpServletResponse.SC_BAD_REQUEST;
				errResponse.endUserMessage = endUserErrorMessageWithId("Encountered technical difficulties validating this request. " +
						"This is likely a configuration problem that will require your system administrator's attention.", errorId);
				errResponse.loggableMessage = loggableErrorMessageWithId("Request was missing OAuth parameters or OAuth parameters were malformed.", errorId);
			} else if (launchError.getCause() instanceof UnsupportedSignatureMethodException) { // special case called out in spec
				errResponse.statusCode = HttpServletResponse.SC_BAD_REQUEST;
				errResponse.endUserMessage = endUserErrorMessageWithId("Encountered technical difficulties validating this request. " +
						"This is likely a configuration problem that will require your system administrator's attention.", errorId);
				final String sigMethod = request.getParameter(OAuthConsumerParameter.oauth_signature_method.toString());
				errResponse.loggableMessage = loggableErrorMessageWithId("Requested OAuth signature method [" + sigMethod+ "] is not supported.", errorId);
			} else {
				errResponse.statusCode = HttpServletResponse.SC_FORBIDDEN;
				errResponse.endUserMessage = endUserErrorMessageWithId("Could not authenticate this launch request. " +
						"This is likely a configuration problem that will require your system administrator's attention.", errorId);
				errResponse.loggableMessage = loggableErrorMessageWithId("OAuth request did not validate. This is usually a violation of signature validation rules.", errorId);
			}
		} else {
			errResponse.endUserMessage = endUserErrorMessageWithId("Encountered technical difficulties handling this launch request. " +
							"This is likely a configuration problem that will require your system administrator's attention.", errorId);
			errResponse.loggableMessage = loggableErrorMessageWithId("Generic system failure.", errorId);
		}
		return errResponse;
	}

	private String endUserErrorMessageWithId(String msg, UUID id) {
		return new StringBuilder(msg).append(" Contact your system administrator with this error ID: ").append(id).toString();
	}

	private String loggableErrorMessageWithId(String msg, UUID id) {
		return new StringBuilder("LTI Error ID: ").append(id).append(" :: ").append(msg).toString();
	}

	private LaunchMode detectLaunchMode(HttpServletRequest request) {
		final String pathInfo = request.getPathInfo();
		if ( StringUtils.isBlank(pathInfo) ) {
			return LaunchMode.LIVE;
		}
		if ( pathInfo.contains(TEST_LAUNCH_URI_PATH_FRAGMENT) ) {
			return LaunchMode.TEST;
		}
		return LaunchMode.LIVE;
	}

	private static final class ErrorResponse {
		UUID errorId;
		int statusCode;
		String loggableMessage;
		String endUserMessage;
		Exception launchError;
		ErrorResponse() {}
		ErrorResponse(UUID errorId, int statusCode, Exception launchError) {
			this.errorId = errorId;
			this.statusCode = statusCode;
			this.launchError = launchError;
		}
	}

}
