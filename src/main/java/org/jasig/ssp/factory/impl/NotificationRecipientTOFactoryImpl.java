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

import org.jasig.ssp.dao.NotificationRecipientDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.NotificationRecipientTOFactory;
import org.jasig.ssp.factory.PersonTOFactory;
import org.jasig.ssp.model.NotificationRecipient;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.NotificationRecipientTO;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Notification transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class NotificationRecipientTOFactoryImpl extends AbstractAuditableTOFactory<NotificationRecipientTO, NotificationRecipient>
		implements NotificationRecipientTOFactory {

	public NotificationRecipientTOFactoryImpl() {
		super(NotificationRecipientTO.class, NotificationRecipient.class);
	}

	@Autowired
	private transient NotificationRecipientDao dao;

    @Autowired
    private transient PersonTOFactory personTOFactory;

	protected NotificationRecipientDao getDao() {
		return dao;
	}

	@Override
	public NotificationRecipient from(final NotificationRecipientTO tObject) throws ObjectNotFoundException {
		final NotificationRecipient model = super.from(tObject);

		if (tObject.getRecipient() != null) {
			final PersonLiteTO personLiteTO = tObject.getRecipient();
			final Person personFromPersonLite = new Person();
			personFromPersonLite.setId(personLiteTO.getId());
			personFromPersonLite.setFirstName(personLiteTO.getFirstName());
			personFromPersonLite.setLastName(personLiteTO.getLastName());

			model.setPerson(personFromPersonLite);
		}

		if (tObject.getSspRole() != null) {
			model.setSspRole(tObject.getSspRole());
		}

		return model;
	}
}
