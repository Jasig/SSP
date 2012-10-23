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
package org.jasig.ssp.factory.reference.impl;

import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.MessageTemplateTOFactory;
import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.MessageTemplateTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MessageTemplate transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class MessageTemplateTOFactoryImpl extends
		AbstractReferenceTOFactory<MessageTemplateTO, MessageTemplate>
		implements MessageTemplateTOFactory {

	public MessageTemplateTOFactoryImpl() {
		super(MessageTemplateTO.class, MessageTemplate.class);
	}

	@Autowired
	private transient MessageTemplateDao dao;

	@Override
	protected MessageTemplateDao getDao() {
		return dao;
	}

	@Override
	public MessageTemplate from(final MessageTemplateTO tObject)
			throws ObjectNotFoundException {
		final MessageTemplate model = super.from(tObject);

		model.setSubject(tObject.getSubject());
		model.setBody(tObject.getBody());

		return model;
	}
}