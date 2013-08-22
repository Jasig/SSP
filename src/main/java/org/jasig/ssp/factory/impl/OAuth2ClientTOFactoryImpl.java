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
package org.jasig.ssp.factory.impl;


import java.util.Set;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.dao.security.oauth2.OAuth2ClientDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.OAuth2ClientTOFactory;
import org.jasig.ssp.model.security.PersistentGrantedAuthority;
import org.jasig.ssp.model.security.oauth2.OAuth2Client;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.OAuth2ClientTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Service
@Transactional(readOnly = true)
public class OAuth2ClientTOFactoryImpl
		extends AbstractAuditableTOFactory<OAuth2ClientTO, OAuth2Client>
		implements OAuth2ClientTOFactory {

	@Autowired
	private transient OAuth2ClientDao dao;

	public OAuth2ClientTOFactoryImpl() {
		super(OAuth2ClientTO.class, OAuth2Client.class);
	}

	@Override
	protected AuditableCrudDao<OAuth2Client> getDao() {
		return dao;
	}

	@Override
	public OAuth2Client from(final OAuth2ClientTO tObject)
			throws ObjectNotFoundException {

		final OAuth2Client model = super.from(tObject);
		model.setClientId(tObject.getClientId());
		model.setAccessTokenValiditySeconds(tObject.getAccessTokenValiditySeconds());
		model.setPrimaryEmailAddress(tObject.getPrimaryEmailAddress());
		model.setFirstName(tObject.getFirstName());
		model.setLastName(tObject.getLastName());

		updateModelAuthorities(tObject, model);
		updateModelSecret(tObject, model);

		return model;

	}

	private void updateModelSecret(OAuth2ClientTO tObject, OAuth2Client model) {
		if ( tObject.isSecretChange() ) {
			model.setSecret(tObject.getSecret());
		}
	}

	private void updateModelAuthorities(OAuth2ClientTO from, OAuth2Client to) {
		boolean areFromAuthorities = hasAuthorities(from);
		boolean areToAuthorities = hasAuthorities(to);
		if ( !(areFromAuthorities) && !(areToAuthorities) ) {
			return;
		}

		if ( !(areToAuthorities) && to.getAuthorities() == null ) {
			to.setAuthorities(Lists.<PersistentGrantedAuthority>
					newArrayListWithCapacity(countAuthoritiesIn(from)));
		}

		to.getAuthorities().clear();
		to.getAuthorities().addAll(uniquePersistentGrantedAuthoritiesFrom(from));
	}

	private Set<PersistentGrantedAuthority> uniquePersistentGrantedAuthoritiesFrom(OAuth2ClientTO from) {
		if ( from.getAuthorities() == null ) {
			return Sets.newHashSet();
		}
		Set<PersistentGrantedAuthority> pgas = Sets.newHashSet();
		for ( String authStr : from.getAuthorities() ) {
			pgas.add(new PersistentGrantedAuthority(authStr));
		}
		return pgas;
	}

	private int countAuthoritiesIn(OAuth2ClientTO tObject) {
		return tObject.getAuthorities() == null ? 0 : tObject.getAuthorities().size();
	}

	private boolean hasAuthorities(OAuth2Client to) {
		return to.getAuthorities() != null && !(to.getAuthorities().isEmpty());
	}

	private boolean hasAuthorities(OAuth2ClientTO from) {
		return from.getAuthorities() != null && !(from.getAuthorities().isEmpty());
	}
}
