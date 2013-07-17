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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.util.IpAddressMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestTrustServiceImpl implements RequestTrustService {

	@Autowired
	private ConfigService configService;


	@Override
	public void assertHighlyTrustedRequest(HttpServletRequest request) throws AccessDeniedException {
		if ( !(allowHighlyTrustedIps()) ) {
			throw new AccessDeniedException("High trust features have been disabled.");
		}
		assertRequestFromHighlyTrustedIps(request);
	}

	private void assertRequestFromHighlyTrustedIps(HttpServletRequest request) throws AccessDeniedException {
		final String trustedIpsStr =
				StringUtils.trimToNull(configService.getByNameNullOrDefaultValue("highly_trusted_ips"));
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
				StringUtils.trimToNull(configService.getByNameNullOrDefaultValue("highly_trusted_ips_enabled"));
		return Boolean.parseBoolean(enabledStr.toLowerCase());
	}
}
