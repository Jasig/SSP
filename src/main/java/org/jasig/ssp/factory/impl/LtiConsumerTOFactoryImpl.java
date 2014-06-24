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


import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.dao.security.lti.LtiConsumerDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.LtiConsumerTOFactory;
import org.jasig.ssp.model.security.lti.LtiConsumer;
import org.jasig.ssp.model.security.oauth2.OAuth2Client;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.LtiConsumerTO;
import org.jasig.ssp.transferobject.OAuth2ClientTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LtiConsumerTOFactoryImpl
		extends AbstractAuditableTOFactory<LtiConsumerTO, LtiConsumer>
		implements LtiConsumerTOFactory {

	@Autowired
	private transient LtiConsumerDao dao;

	public LtiConsumerTOFactoryImpl() {
		super(LtiConsumerTO.class, LtiConsumer.class);
	}

	@Override
	protected AuditableCrudDao<LtiConsumer> getDao() {
		return dao;
	}

	@Override
	public LtiConsumer from(final LtiConsumerTO tObject)
			throws ObjectNotFoundException {

		final LtiConsumer model = super.from(tObject);
		model.setConsumerKey(tObject.getConsumerKey());
		model.setLtiSectionCodeField(tObject.getLtiSectionCodeField());
		model.setLtiUserIdField(tObject.getLtiUserIdField());
		model.setSspUserIdField(tObject.getSspUserIdField());
		model.setName(tObject.getName());
		updateModelSecret(tObject, model);
		return model;

	}
	
	private void updateModelSecret(LtiConsumerTO tObject, LtiConsumer model) {
		if ( tObject.isSecretChange() ) {
			model.setSecret(tObject.getSecret());
		}
	}

	
}
