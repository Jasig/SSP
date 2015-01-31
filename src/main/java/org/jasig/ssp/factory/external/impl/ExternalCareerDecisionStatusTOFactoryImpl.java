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
package org.jasig.ssp.factory.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalCareerDecisionStatusTOFactory;
import org.jasig.ssp.model.external.ExternalCareerDecisionStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalCareerDecisionStatusTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalCareerDecisionStatusTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalCareerDecisionStatusTO, ExternalCareerDecisionStatus> implements
		ExternalCareerDecisionStatusTOFactory {


	public ExternalCareerDecisionStatusTOFactoryImpl () {
		super(ExternalCareerDecisionStatusTO.class, ExternalCareerDecisionStatus.class);
	}

	@Override
	public ExternalCareerDecisionStatus from(ExternalCareerDecisionStatusTO tObject)
			throws ObjectNotFoundException {
		final ExternalCareerDecisionStatus model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
		model.setCode(tObject.getCode());

		return model;
	}

	@Override
	protected ExternalDataDao<ExternalCareerDecisionStatus> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
