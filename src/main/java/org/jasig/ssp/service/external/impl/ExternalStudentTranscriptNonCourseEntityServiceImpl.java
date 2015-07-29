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
package org.jasig.ssp.service.external.impl;


import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentTranscriptNonCourseDao;
import org.jasig.ssp.model.external.ExternalStudentTranscriptNonCourseEntity;
import org.jasig.ssp.service.external.ExternalStudentTranscriptNonCourseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional
public class ExternalStudentTranscriptNonCourseEntityServiceImpl extends
		AbstractExternalDataService<ExternalStudentTranscriptNonCourseEntity> implements
		ExternalStudentTranscriptNonCourseEntityService {

	@Autowired
	private transient ExternalStudentTranscriptNonCourseDao dao;


	@Override
	protected ExternalDataDao<ExternalStudentTranscriptNonCourseEntity> getDao() {
		// TODO Auto-generated method stub
		return null;
	}


    @Override
    public List<ExternalStudentTranscriptNonCourseEntity> getAllNonCourseTranscripts () {
        return dao.getAllNonCourseTranscripts();
    }

    @Override
	public List<ExternalStudentTranscriptNonCourseEntity> getNonCourseTranscriptsBySchoolId(
			String schoolId) {
		return dao.getNonCourseTranscriptsBySchoolId(schoolId);
	}

    @Override
	public List<ExternalStudentTranscriptNonCourseEntity> getNonCourseTranscriptsBySchoolIdAndTermCode(String schoolId, String termCode){
		return dao.getNonCourseTranscriptsBySchoolIdAndTermCode(schoolId, termCode);
	}
}
