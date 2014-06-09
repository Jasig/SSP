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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.ConfidentialityLevelDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.ConfidentialityLevelTOFactory;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelTO;

@Service
@Transactional(readOnly = true)
public class ConfidentialityLevelTOFactoryImpl
		extends
		AbstractReferenceTOFactory<ConfidentialityLevelTO, ConfidentialityLevel>
		implements ConfidentialityLevelTOFactory {

	public ConfidentialityLevelTOFactoryImpl() {
		super(ConfidentialityLevelTO.class, ConfidentialityLevel.class);
	}

	@Autowired
	private transient ConfidentialityLevelDao dao;

	@Override
	protected ConfidentialityLevelDao getDao() {
		return dao;
	}

	@Override
	public ConfidentialityLevel from(final ConfidentialityLevelTO tObject)
			throws ObjectNotFoundException {
		final ConfidentialityLevel model = super.from(tObject);

		model.setAcronym(tObject.getAcronym());
		model.setPermission(DataPermissions.valueOf(tObject.getPermission()));

		return model;
	}

}
