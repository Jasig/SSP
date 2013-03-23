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
import org.jasig.ssp.factory.external.ExternalStudentTranscriptCourseTOFactory;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalStudentTranscriptCourseTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExternalStudentTranscriptCourseTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentTranscriptCourseTO, ExternalStudentTranscriptCourse> implements
		ExternalStudentTranscriptCourseTOFactory {

	public ExternalStudentTranscriptCourseTOFactoryImpl()
	{
		super(ExternalStudentTranscriptCourseTO.class,ExternalStudentTranscriptCourse.class);
	}
	public ExternalStudentTranscriptCourseTOFactoryImpl(
			Class<ExternalStudentTranscriptCourseTO> toClass,
			Class<ExternalStudentTranscriptCourse> mClass) {
		super(toClass, mClass);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ExternalStudentTranscriptCourse from(ExternalStudentTranscriptCourseTO tObject) 
			throws ObjectNotFoundException {
		final ExternalStudentTranscriptCourse model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
		model.setAudited(tObject.getAudited());
		model.setCreditEarned(tObject.getCreditEarned());
		model.setCreditType(tObject.getCreditType());
		model.setDescription(tObject.getDescription());
		model.setFirstName(tObject.getFirstName());
		model.setFormattedCourse(tObject.getFormattedCourse());
		model.setGrade(tObject.getGrade());
		model.setLastName(tObject.getLastName());
		model.setMiddleName(tObject.getMiddleName());
		model.setNumber(tObject.getNumber());
		model.setSectionCode(tObject.getSectionNumber());
		model.setSectionNumber(tObject.getSectionNumber());
		model.setStatusCode(tObject.getStatusCode());
		model.setSubjectAbbreviation(tObject.getSubjectAbbreviation());
		model.setTermCode(tObject.getTermCode());
		model.setTitle(tObject.getTitle());
		return model;
	}

	@Override
	protected ExternalDataDao<ExternalStudentTranscriptCourse> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
