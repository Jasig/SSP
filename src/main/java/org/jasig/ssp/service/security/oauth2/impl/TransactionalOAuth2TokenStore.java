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
package org.jasig.ssp.service.security.oauth2.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Exists purely to wrap transactions around a "physical" {@link TokenStore}.
 * Isolation level is {@link Isolation#REPEATABLE_READ} per the recommendation
 * <a href="https://github.com/SpringSource/spring-security-oauth/wiki/oAuth2">
 * here</a>. Not entirely sure that has the intended effect for all our use
 * cases, e.g. perhaps not for token revocation after a change to a critical
 * {@code OAuth2Client} field, but that's <em>probably</em> OK as long as the
 * "core" SpSec OAuth2 call paths, which should be separate stacks from our
 * service calls, get the proper isolation.
 */
@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class TransactionalOAuth2TokenStore implements TokenStore {

	@Autowired
	@Qualifier("oauth2PhysicalTokenStore")
	private TokenStore delegateTokenStore;

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		return delegateTokenStore.readAuthentication(token);
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		return delegateTokenStore.readAuthentication(token);
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		delegateTokenStore.storeAccessToken(token, authentication);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		return delegateTokenStore.readAccessToken(tokenValue);
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		delegateTokenStore.removeAccessToken(token);
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		delegateTokenStore.storeRefreshToken(refreshToken, authentication);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		return delegateTokenStore.readRefreshToken(tokenValue);
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		return delegateTokenStore.readAuthenticationForRefreshToken(token);
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		delegateTokenStore.removeRefreshToken(token);
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		delegateTokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		return delegateTokenStore.getAccessToken(authentication);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByUserName(String userName) {
		return delegateTokenStore.findTokensByUserName(userName);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		return delegateTokenStore.findTokensByClientId(clientId);
	}

}
