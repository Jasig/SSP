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
package org.jasig.ssp.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.service.RequestTrustService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.reference.ConfigTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.util.IpAddressMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestTrustServiceImpl implements RequestTrustService {

	private static final String HIGHLY_TRUSTED_IPS_ENABLED_CONFIG_NAME = "highly_trusted_ips_enabled";
	private static final String HIGHLY_TRUSTED_IPS_CONFIG_NAME = "highly_trusted_ips";
	private static final String OBFUSCATION = "RESTRICTED";

	@Autowired
	private ConfigService configService;

	@Value("#{configProperties." + HIGHLY_TRUSTED_IPS_CONFIG_NAME + "}")
	private String highlyTrustedIps;


	@Override
	public void assertHighlyTrustedRequest(HttpServletRequest request) throws AccessDeniedException {
		if ( !(allowHighlyTrustedIps()) ) {
			throw new AccessDeniedException("High trust features have been disabled.");
		}
		assertRequestFromHighlyTrustedIps(request);
	}

	private void assertRequestFromHighlyTrustedIps(HttpServletRequest request) throws AccessDeniedException {
		final String trustedIpsStr = StringUtils.trimToNull(highlyTrustedIps);
		if ( trustedIpsStr == null ) {
			throw new AccessDeniedException("No highly trusted IPs have been configured.");
		}
		for ( String trustedIpStr : trustedIpsStr.split(",") ) {
			trustedIpStr = StringUtils.trimToNull(trustedIpStr.trim());
			if ( trustedIpsStr == null ) {
				continue;
			}
			IpAddressMatcher matcher = new IpAddressMatcher(trustedIpStr);
			if ( matcher.matches(request) ) {
				return;
			}
		}
		// empty trust lists treated as "trust no one"
		throw new AccessDeniedException("Request did not come from a highly"
				+ " trusted IP."); // intentionally try to keep a lid on the list of trusted IPs
	}

	private boolean allowHighlyTrustedIps() {
		final String enabledStr =
				StringUtils.trimToNull(configService.getByNameNullOrDefaultValue(HIGHLY_TRUSTED_IPS_ENABLED_CONFIG_NAME));
		return Boolean.parseBoolean(enabledStr.toLowerCase());
	}

	// No longer reading the IP list from the db, but leaving this here
	// as a precaution anyway
	@Override
	public void obfuscateSensitiveConfig(ConfigTO config) {
		if ( config == null ) {
			return;
		}
		if ( HIGHLY_TRUSTED_IPS_CONFIG_NAME.equals(config.getName()) ) {
			config.setValue(OBFUSCATION);
		}
	}

	public void setHighlyTrustedIps(String highlyTrustedIps) {
		this.highlyTrustedIps = highlyTrustedIps;
	}
}
