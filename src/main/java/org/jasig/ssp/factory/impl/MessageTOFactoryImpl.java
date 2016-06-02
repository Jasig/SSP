/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.factory.impl;

import org.jasig.ssp.dao.MessageDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.MessageTOFactory;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.MessageTO;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Message transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class MessageTOFactoryImpl extends
        AbstractAuditableTOFactory<MessageTO, Message>
		implements MessageTOFactory {

	public MessageTOFactoryImpl () {
		super(MessageTO.class, Message.class);
	}

	@Autowired
	private transient MessageDao dao;

    @Autowired
    private transient PersonTOFactory personTOFactory;

	protected MessageDao getDao() {
		return dao;
	}

	@Override
	public Message from(final MessageTO tObject) throws ObjectNotFoundException {
		final Message model = super.from(tObject);

		model.setSubject(tObject.getSubject());
		model.setBody(tObject.getBody());
        model.setCarbonCopy(tObject.getCarbonCopy());
        model.setRecipientEmailAddress(tObject.getRecipientEmailAddress());

        if (tObject.getSender() != null) {
            final PersonLiteTO personLiteTO = tObject.getSender();
            final Person personFromPersonLite = new Person();
            personFromPersonLite.setId(personLiteTO.getId());
            personFromPersonLite.setFirstName(personLiteTO.getFirstName());
            personFromPersonLite.setLastName(personLiteTO.getLastName());

            model.setSender(personFromPersonLite);
        }

		return model;
	}
}
