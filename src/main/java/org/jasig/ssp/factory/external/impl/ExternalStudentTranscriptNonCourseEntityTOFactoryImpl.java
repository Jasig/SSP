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
import org.jasig.ssp.factory.external.ExternalStudentTranscriptNonCourseEntityTOFactory;
import org.jasig.ssp.model.external.ExternalStudentTranscriptNonCourseEntity;
import org.jasig.ssp.transferobject.external.ExternalStudentTranscriptNonCourseEntityTO;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ExternalStudentTranscriptNonCourseEntityTOFactoryImpl extends
		AbstractExternalDataTOFactory<ExternalStudentTranscriptNonCourseEntityTO, ExternalStudentTranscriptNonCourseEntity> implements
        ExternalStudentTranscriptNonCourseEntityTOFactory {


	public ExternalStudentTranscriptNonCourseEntityTOFactoryImpl () {
		super(ExternalStudentTranscriptNonCourseEntityTO.class, ExternalStudentTranscriptNonCourseEntity.class);
	}


    public ExternalStudentTranscriptNonCourseEntityTOFactoryImpl (Class<ExternalStudentTranscriptNonCourseEntityTO> toClass,
            Class<ExternalStudentTranscriptNonCourseEntity> mClass) {
		super(toClass, mClass);
		//TODO Auto-generated constructor stub
	}


	@Override
	public ExternalStudentTranscriptNonCourseEntity from(ExternalStudentTranscriptNonCourseEntityTO tObject)
			throws ObjectNotFoundException {
		final ExternalStudentTranscriptNonCourseEntity model = super.from(tObject);

		model.setSchoolId(tObject.getSchoolId());
        model.setTargetFormattedCourse(tObject.getTargetFormattedCourse());
        model.setTermCode(tObject.getTermCode());
        model.setNonCourseCode(tObject.getNonCourseCode());
        model.setTitle(tObject.getTitle());
        model.setDescription(tObject.getDescription());
        model.setGrade(tObject.getGrade());
        model.setCreditEarned(tObject.getCreditEarned());
		model.setCreditType(tObject.getCreditType());
		model.setStatusCode(tObject.getStatusCode());
		return model;
	}

	@Override
	protected ExternalDataDao<ExternalStudentTranscriptNonCourseEntity> getDao() {
		// TODO Auto-generated method stub
		return null;
	}
}
