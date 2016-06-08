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
package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.TemplateElectiveCourseDao;
import org.jasig.ssp.dao.reference.TemplateElectiveCourseElectiveDao;
import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.TemplateService;
import org.jasig.ssp.service.reference.TemplateElectiveCourseDetailService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * JournalStepDetail implementation service
 * 
 * @author daniel.bower
 * 
 */
@Service
@Transactional
public class TemplateElectiveCourseDetailServiceImpl extends
		AbstractAuditableCrudService<TemplateElectiveCourse> implements
		TemplateElectiveCourseDetailService {

	@Autowired
	transient private TemplateElectiveCourseDao templateElectiveCourseDao;

	@Autowired
	transient private TemplateElectiveCourseElectiveDao templateElectiveCourseElectiveDao;

	@Autowired
	transient private TemplateService templateService;

	protected void setDao(final TemplateElectiveCourseDao dao) {
		this.templateElectiveCourseDao = dao;
	}

	@Override
	protected TemplateElectiveCourseDao getDao() {
		return templateElectiveCourseDao;
	}

	@Override
	public PagingWrapper<TemplateElectiveCourseElective> getElectiveCourseAssociationsForElectiveCourse (
			final UUID electiveCourseId) throws ObjectNotFoundException {
		return new PagingWrapper<TemplateElectiveCourseElective>(templateElectiveCourseElectiveDao.getAllElectivesForElectiveCourse(templateElectiveCourseDao.get(electiveCourseId)));
	}

	@Override
	public TemplateElectiveCourse save(TemplateElectiveCourse obj) throws ObjectNotFoundException, ValidationException{
		return templateElectiveCourseDao.save(obj);
	}
	
	@Override
	public void deleteAssociatedElective(UUID id) throws ObjectNotFoundException {
		TemplateElectiveCourseElective tece = templateElectiveCourseElectiveDao.get(id);
		templateElectiveCourseElectiveDao.delete(tece);
	}

	@Override
	public TemplateElectiveCourse createTemplateElectiveCourse(TemplateElectiveCourse templateElectiveCourse) {
		return templateElectiveCourseDao.save(templateElectiveCourse);
	}

	@Override
	public TemplateElectiveCourseElective createTemplateElectiveCourseElective(TemplateElectiveCourseElective templateElectiveCourseElective){
		return templateElectiveCourseElectiveDao.save(templateElectiveCourseElective);
	}
	@Override
	public void delete(TemplateElectiveCourse templateElectiveCourse) {
		templateElectiveCourseDao.delete(templateElectiveCourse);
	}

	@Override
	public TemplateElectiveCourse get(String formattedCourse) {
		return templateElectiveCourseDao.get(formattedCourse);
	}
}