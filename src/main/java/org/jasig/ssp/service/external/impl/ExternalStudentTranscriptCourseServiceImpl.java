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
package org.jasig.ssp.service.external.impl;

import java.util.List;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.dao.external.ExternalStudentTranscriptCourseDao;
import org.jasig.ssp.dao.external.ExternalStudentTranscriptDao;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExternalStudentTranscriptCourseServiceImpl extends
		AbstractExternalDataService<ExternalStudentTranscriptCourse> implements
		ExternalStudentTranscriptCourseService {

	@Autowired
	private transient ExternalStudentTranscriptCourseDao dao;
	
	@Override
	protected ExternalDataDao<ExternalStudentTranscriptCourse> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ExternalStudentTranscriptCourse> getTranscriptsBySchoolId(
			String schoolId) {
		return dao.getTranscriptsBySchoolId(schoolId);
	}
	
	public List<ExternalStudentTranscriptCourse> getTranscriptsBySchoolIdAndTermCode(String schoolId, String termCode){
		return dao.getTranscriptsBySchoolIdAndTermCode(schoolId, termCode);
	}

}
