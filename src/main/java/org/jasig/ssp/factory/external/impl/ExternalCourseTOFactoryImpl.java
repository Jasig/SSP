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

import org.jasig.ssp.dao.external.ExternalCourseDao;
import org.jasig.ssp.factory.external.ExternalCourseTOFactory;
import org.jasig.ssp.model.external.ExternalCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalCourseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Term transfer object factory implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional(readOnly = true)
public class ExternalCourseTOFactoryImpl
		extends AbstractExternalDataTOFactory<ExternalCourseTO, ExternalCourse>
		implements ExternalCourseTOFactory {

	public ExternalCourseTOFactoryImpl() {
		super(ExternalCourseTO.class, ExternalCourse.class);
	}
 
	@Autowired
	private transient ExternalCourseDao dao;

	@Override
	protected ExternalCourseDao getDao() {
		return dao;
	}

	@Override
	public ExternalCourse from(final ExternalCourseTO tObject) throws ObjectNotFoundException {
		final ExternalCourse model = super.from(tObject);
		model.setCode(tObject.getCode());
		model.setDescription(tObject.getDescription());
		model.setFormattedCourse(tObject.getFormattedCourse());
		model.setMaxCreditHours(tObject.getMaxCreditHours());
		model.setMinCreditHours(tObject.getMinCreditHours());
		model.setSubjectAbbreviation(tObject.getSubjectAbbreviation());
		model.setTitle(tObject.getTitle());
		model.setIsDev(tObject.getIsDev());
		model.setAcademicLink(tObject.getAcademicLink());
		model.setMasterSyllabusLink(tObject.getMasterSyllabusLink());
		model.setDepartmentCode(tObject.getDepartmentCode());
		model.setDivisionCode(tObject.getDepartmentCode());
		model.setNumber(tObject.getNumber());
		return model;
	}
}