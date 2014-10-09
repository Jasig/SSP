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

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.factory.external.ExternalStudentRiskIndicatorTOFactory;
import org.jasig.ssp.model.external.ExternalStudentRiskIndicator;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalStudentRiskIndicatorTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ExternalStudentRiskIndicatorTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentRiskIndicatorTO, ExternalStudentRiskIndicator> implements
		ExternalStudentRiskIndicatorTOFactory {

	public ExternalStudentRiskIndicatorTOFactoryImpl() {
		super(ExternalStudentRiskIndicatorTO.class, ExternalStudentRiskIndicator.class);
	}

	@Override
	public ExternalStudentRiskIndicator from(ExternalStudentRiskIndicatorTO tObject)
			throws ObjectNotFoundException {
		final ExternalStudentRiskIndicator model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
		model.setModelCode(tObject.getModelCode());
		model.setModelName(tObject.getModelName());
		model.setIndicatorCode(tObject.getIndicatorCode());
		model.setIndicatorName(tObject.getIndicatorName());
		model.setIndicatorValueDescription(tObject.getIndicatorValueDescription());
		model.setIndicatorValue(tObject.getIndicatorValue());
		return model;
	}

	@Override
	protected ExternalDataDao<ExternalStudentRiskIndicator> getDao() {
		return null;
	}
}
