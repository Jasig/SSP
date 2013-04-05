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

import org.jasig.ssp.dao.external.ExternalFacultyCourseRosterDao;
import org.jasig.ssp.factory.external.ExternalFacultyCourseRosterTOFactory;
import org.jasig.ssp.model.external.ExternalFacultyCourseRoster;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalFacultyCourseRosterTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * FacultyCourse transfer object factory implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class ExternalFacultyCourseRosterTOFactoryImpl
		extends
		AbstractExternalDataTOFactory<ExternalFacultyCourseRosterTO, ExternalFacultyCourseRoster>
		implements ExternalFacultyCourseRosterTOFactory {

	public ExternalFacultyCourseRosterTOFactoryImpl() {
		super(ExternalFacultyCourseRosterTO.class, ExternalFacultyCourseRoster.class);
	}

	@Autowired
	private transient ExternalFacultyCourseRosterDao dao;

	@Override
	protected ExternalFacultyCourseRosterDao getDao() {
		return dao;
	}

	@Override
	public ExternalFacultyCourseRoster from(final ExternalFacultyCourseRosterTO tObject)
			throws ObjectNotFoundException {
		final ExternalFacultyCourseRoster model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
		model.setFirstName(tObject.getFirstName());
		model.setMiddleName(tObject.getMiddleName());
		model.setLastName(tObject.getLastName());
		model.setTermCode(tObject.getTermCode());
		model.setFacultySchoolId(tObject.getFacultySchoolId());
		model.setFormattedCourse(tObject.getFormattedCourse());
		model.setPrimaryEmailAddress(tObject.getPrimaryEmailAddress());
		model.setStatusCode(tObject.getStatusCode());
		model.setSectionCode(tObject.getSectionCode());
		model.setSectionNumber(tObject.getSectionNumber());

		return model;
	}
}