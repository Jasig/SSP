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
package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalPersonDao;
import org.jasig.ssp.factory.external.ExternalPersonLiteTOFactory;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalPersonLiteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalPersonLiteTOFactoryImpl
		extends
		AbstractExternalDataTOFactory<ExternalPersonLiteTO, ExternalPerson>
		implements ExternalPersonLiteTOFactory {

	public ExternalPersonLiteTOFactoryImpl() {
		super(ExternalPersonLiteTO.class, ExternalPerson.class);
	}

	@Autowired
	private transient ExternalPersonDao dao;

	@Override
	protected ExternalPersonDao getDao() {
		return dao;
	}

	@Override
	public ExternalPerson from(final ExternalPersonLiteTO tObject)
			throws ObjectNotFoundException {
		final ExternalPerson model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
		model.setFirstName(tObject.getFirstName());
		model.setMiddleName(tObject.getMiddleName());
		model.setLastName(tObject.getLastName());

		return model;
	}

}
