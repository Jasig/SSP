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

import org.jasig.ssp.dao.StrengthDao;
import org.jasig.ssp.factory.AbstractAuditableTOFactory;
import org.jasig.ssp.factory.StrengthTOFactory;
import org.jasig.ssp.model.Strength;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.transferobject.StrengthTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Strength transfer object factory implementation
 */
@Service
@Transactional(readOnly = true)
public class StrengthTOFactoryImpl
		extends AbstractAuditableTOFactory<StrengthTO, Strength>
		implements StrengthTOFactory {

	/**
	 * Construct an instance with the specific classes needed by super class
	 * methods.
	 */
	public StrengthTOFactoryImpl() {
		super(StrengthTO.class, Strength.class);
	}

	@Autowired
	private transient StrengthDao dao;

	@Autowired
	private transient ConfidentialityLevelService confidentialityLevelService;

	@Autowired
	private transient PersonService personService;

	@Override
	protected StrengthDao getDao() {
		return dao;
	}

	@Override
	public Strength from(final StrengthTO tObject) throws ObjectNotFoundException {
		final Strength model = super.from(tObject);

		model.setName(tObject.getName());
		model.setDescription(tObject.getDescription());

		if (tObject.getPersonId() != null) {
			model.setPerson(personService.get(tObject.getPersonId()));
		}

		if ((tObject.getConfidentialityLevel() == null)
				|| (tObject.getConfidentialityLevel().getId() == null)) {
			model.setConfidentialityLevel(null);
		} else {
			model.setConfidentialityLevel(confidentialityLevelService
					.get(tObject.getConfidentialityLevel().getId()));
		}

		return model;
	}
}