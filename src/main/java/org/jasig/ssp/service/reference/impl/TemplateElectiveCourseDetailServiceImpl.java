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

import org.jasig.ssp.dao.reference.TemplateElectiveCourseDetailDao;
import org.jasig.ssp.dao.reference.TemplateElectiveCourseElectiveDetailDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.TemplateService;
import org.jasig.ssp.service.reference.TemplateElectiveCourseDetailService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
	transient private TemplateElectiveCourseDetailDao templateElectiveCourseDetailDao;

	@Autowired
	transient private TemplateElectiveCourseElectiveDetailDao templateElectiveCourseElectiveDetailDao;

	@Autowired
	transient private TemplateService templateService;

	protected void setDao(final TemplateElectiveCourseDetailDao dao) {
		this.templateElectiveCourseDetailDao = dao;
	}

	@Override
	protected TemplateElectiveCourseDetailDao getDao() {
		return templateElectiveCourseDetailDao;
	}

	@Override
	public PagingWrapper<TemplateElectiveCourse> getAllForTemplate(
			final Template template,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
//		List<TemplateElectiveCourse> details = new ArrayList<TemplateElectiveCourse>();
//		return dao.getAllForTemplateId(templateId);
//		List<JournalStepJournalStepDetail> journalStepJournalStepDetails = journalStep.getJournalStepJournalStepDetails();
//		for (JournalStepJournalStepDetail journalStepJournalStepDetail : journalStepJournalStepDetails) {
//			if(sAndP.getStatus() == null || journalStepJournalStepDetail.getObjectStatus().equals(sAndP.getStatus()))
//			{
//				JournalStepDetail journalStepDetail = journalStepJournalStepDetail.getJournalStepDetail();
//				journalStepDetail.setSortOrder(journalStepJournalStepDetail.getSortOrder());
//				details.add(journalStepDetail);
//			}
//		}
		return new PagingWrapper<TemplateElectiveCourse>(templateElectiveCourseDetailDao.getAllForTemplate(template));
	}

	@Override
	public PagingWrapper<TemplateElectiveCourseElective> getElectiveCourseAssociationsForElectiveCourse (
			final UUID electiveCourseId,
			final SortingAndPaging sAndP) throws ObjectNotFoundException {
		return new PagingWrapper<TemplateElectiveCourseElective>(templateElectiveCourseElectiveDetailDao.getAllElectivesForElectiveCourse(templateElectiveCourseDetailDao.get(electiveCourseId)));
	}

	@Override
	public TemplateElectiveCourse save(TemplateElectiveCourse obj) throws ObjectNotFoundException, ValidationException{
		if(!obj.getObjectStatus().equals(ObjectStatus.ACTIVE) && !obj.getObjectStatus().equals(ObjectStatus.ALL))
			templateElectiveCourseDetailDao.softDeleteReferencingAssociations(obj.getId());
		return templateElectiveCourseDetailDao.save(obj);
	}
	
	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		super.delete(id);
		templateElectiveCourseDetailDao.softDeleteReferencingAssociations(id);
	}
}