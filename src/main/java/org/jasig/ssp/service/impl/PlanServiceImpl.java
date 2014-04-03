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
package org.jasig.ssp.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.PlanDao;
import org.jasig.ssp.factory.reference.PlanTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.AbstractPlanOutputTO;
import org.jasig.ssp.transferobject.PlanOutputTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.external.ExternalStudentFinancialAidTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTranscriptTO;
import org.jasig.ssp.transferobject.reference.AbstractMessageTemplateMapPrintParamsTO;
import org.jasig.ssp.transferobject.reference.MessageTemplatePlanPrintParams;
import org.jasig.ssp.transferobject.reports.MapPlanStatusReportCourse;
import org.jasig.ssp.transferobject.reports.PlanAdvisorCountTO;
import org.jasig.ssp.transferobject.reports.PlanCourseCountTO;
import org.jasig.ssp.transferobject.reports.MapStatusReportPerson;
import org.jasig.ssp.transferobject.reports.PlanStudentStatusTO;
import org.jasig.ssp.transferobject.reports.SearchPlanTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Person service implementation
 * 
 * @author tony.arland
 */
@Service
@Transactional
public class PlanServiceImpl extends AbstractPlanServiceImpl<Plan,PlanTO,PlanOutputTO,MessageTemplatePlanPrintParams>
		implements PlanService {

	@Autowired
	private PlanDao dao;
	
	@Autowired
	private PersonService personService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private ExternalStudentFinancialAidService externalStudentFinancialAidService;

	@Autowired
	private ExternalStudentTranscriptService externalStudentTranscriptService;

	
	@Autowired
	private PlanTOFactory planTOFactory;
	
	@Override
	protected PlanDao getDao() {
		return dao;
	}

	@Override
	protected UUID getPersonIdPlannedFor(PlanTO model) {
		String personId = model.getPersonId();
		return StringUtils.isBlank(personId) ? null : UUID.fromString(personId);
	}

	public void setDao(PlanDao dao) {
		this.dao = dao;
	}

	@Override
	public Plan getCurrentForStudent(UUID personId) {
		return dao.getActivePlanForStudent(personId);
	}
	
	@Override
	public Person getOwnerForPlan(UUID id) {
		return dao.getOwnerForPlan(id);
	}

	@Override
	@Transactional(readOnly=true)
	public SubjectAndBody createOutput(PlanOutputTO planOutputDataTO) throws ObjectNotFoundException {
		SubjectAndBody output = null;

		if(planOutputDataTO.getOutputFormat().equals(PlanService.OUTPUT_FORMAT_MATRIX)) {
			MessageTemplatePlanPrintParams params = new MessageTemplatePlanPrintParams();
			params.setMessageTemplateId(planOutputDataTO.getMessageTemplateMatrixId());
			params.setOutputPlan(planOutputDataTO);
			params.setInstitutionName(getInstitutionName());
			if(StringUtils.isNotBlank(planOutputDataTO.getPlan().getOwnerId())){
				params.setOwner(personService.get(
						UUID.fromString(planOutputDataTO.getPlan().getOwnerId())));
			}
			
			if(StringUtils.isNotBlank(planOutputDataTO.getPlan().getPersonId())){
				params.setStudent(personService.get(
						UUID.fromString(planOutputDataTO.getPlan().getPersonId())));
			}
			output = createMatrixOutput(params);
		} else{
			UUID personID = UUID.fromString(planOutputDataTO.getPlan().getPersonId());
			String schoolId = personService.get(personID).getSchoolId();
			planOutputDataTO.setFinancialAid(new ExternalStudentFinancialAidTO(externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(schoolId)));
			planOutputDataTO.setGpa(new ExternalStudentTranscriptTO(externalStudentTranscriptService.getRecordsBySchoolId(schoolId)));
			output = createFullOutput(planOutputDataTO);
		}

		return output;
	}
	


	@Override
	public PagingWrapper<Plan> getAllForStudent(
			SortingAndPaging sAndP,UUID personId) {
		return getDao().getAllForStudent(sAndP, personId);
	}
	
	@Override
	public Plan copyAndSave(Plan obj) throws CloneNotSupportedException {
		//If plan has been marked as active, we must mark all other plans as inactive
		Plan cloneAndSave = getDao().cloneAndSave(obj,getSecurityService().currentUser().getPerson());
		obj = null;
		if(ObjectStatus.ACTIVE.equals(cloneAndSave.getObjectStatus()))
		{
			getDao().markOldPlansAsInActive(cloneAndSave);
		}
		return cloneAndSave;
	}
	
	@Override
	public Plan save(Plan obj) {
		//If plan has been marked as active, we must mark all other plans as inactive
		if(ObjectStatus.ACTIVE.equals(obj.getObjectStatus()))
		{
			getDao().markOldPlansAsInActive(obj);
		}	
		return super.save(obj);
	}
	
	@Override
	public List<PlanAdvisorCountTO> getAdvisorsPlanCount(SearchPlanTO form){
		return dao.getAdvisorsPlanCount(form);
	}
	
	@Override
	public List<PlanCourseCountTO> getPlanCourseCount(SearchPlanTO form){
		return dao.getPlanCourseCount(form);
	}
	
	@Override
	public List<PlanStudentStatusTO> getPlanStudentStatusByCourse(SearchPlanTO form){
		return dao.getPlanStudentStatusByCourse(form);
	}

	@Override
	public List<MapStatusReportPerson> getAllActivePlanIds() {
		return dao.getAllActivePlanIds();
	}

	@Override
	public List<MapPlanStatusReportCourse> getAllPlanCoursesForStatusReport(
			UUID planId) {
		return dao.getAllPlanCoursesForStatusReport(planId);
	}

	@Override
	public SubjectAndBody createMatrixOutput(
			MessageTemplatePlanPrintParams outputPlan)
			throws ObjectNotFoundException {

			List<TermCourses<Plan,PlanTO>> courses = collectTermCourses(outputPlan.getOutputPlan().getNonOutputTO());
			outputPlan.setTermCourses(courses);
			outputPlan.setTotalPlanCreditHours(calculateTotalPlanHours(courses));
			 
			SubjectAndBody subjectAndBody = getMessageTemplateService().createMapPlanMatrixOutput(outputPlan, null);
			return subjectAndBody;
	}

}