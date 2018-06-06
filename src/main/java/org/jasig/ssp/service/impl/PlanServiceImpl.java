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
package org.jasig.ssp.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.PlanDao;
import org.jasig.ssp.dao.PlanElectiveCourseDao;
import org.jasig.ssp.factory.reference.PlanTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.PlanElectiveCourse;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.model.external.ExternalStudentFinancialAid;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.external.ExternalProgramService;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.PersonLiteTO;
import org.jasig.ssp.transferobject.PlanOutputTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.external.ExternalStudentFinancialAidTO;
import org.jasig.ssp.transferobject.external.ExternalStudentTranscriptTO;
import org.jasig.ssp.transferobject.reference.MessageTemplatePlanPrintParams;
import org.jasig.ssp.transferobject.reports.*;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
	private transient ExternalProgramService externalProgramService;

	@Autowired
	private PlanTOFactory planTOFactory;
	
	@Override
	protected PlanDao getDao() {
		return dao;
	}

	@Autowired
	private PlanElectiveCourseDao planElectiveCourseDao;

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
	public List<Plan> getCurrentPlansForStudents(List<UUID> personIds) {
		return getDao().getActivePlanForStudents(personIds);
	}
	
	@Override
	public Person getOwnerForPlan(UUID id) {
		return dao.getOwnerForPlan(id);
	}

	@Override
	@Transactional(readOnly=true)
	public SubjectAndBody createOutput(PlanOutputTO planOutputDataTO) throws ObjectNotFoundException {
		SubjectAndBody output = null;

		if(planOutputDataTO.getOutputFormat().equals(PlanService.OUTPUT_FORMAT_MATRIX) || planOutputDataTO.getOutputFormat().equals(PlanService.OUTPUT_SHORT_MATRIX)) {
			MessageTemplatePlanPrintParams params = new MessageTemplatePlanPrintParams();
			params.setOutputPlan(planOutputDataTO);
			params.setInstitutionName(getInstitutionName());
			if (StringUtils.isNotBlank(planOutputDataTO.getPlan().getOwnerId())) {
				params.setOwner(personService.get(
						UUID.fromString(planOutputDataTO.getPlan().getOwnerId())));
			}

			if (StringUtils.isNotBlank(planOutputDataTO.getPlan().getPersonId())) {
				params.setStudent(personService.get(
						UUID.fromString(planOutputDataTO.getPlan().getPersonId())));
			}
			params.setLastModifiedBy(personService.get(planOutputDataTO.getPlan().getModifiedBy().getId()));
			if(planOutputDataTO.getOutputFormat().equals(PlanService.OUTPUT_FORMAT_MATRIX)) {
				params.setMessageTemplateId(planOutputDataTO.getMessageTemplateMatrixId());
				output = createMatrixOutput(params, false);
			} else if (planOutputDataTO.getOutputFormat().equals(PlanService.OUTPUT_SHORT_MATRIX)) {
				params.setMessageTemplateId(planOutputDataTO.getMessageTemplateShortMatrixId());
				output = createMatrixOutput(params, true);
			}
		} else {
			UUID personID = UUID.fromString(planOutputDataTO.getPlan().getPersonId());
			String schoolId = personService.get(personID).getSchoolId();
			ExternalStudentFinancialAid fa = externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(schoolId);
			
			if(fa != null)
				planOutputDataTO.setFinancialAid(new ExternalStudentFinancialAidTO(fa));
			
			ExternalStudentTranscript st = externalStudentTranscriptService.getRecordsBySchoolId(schoolId);
			if(st != null)
				planOutputDataTO.setGpa(new ExternalStudentTranscriptTO(st));
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
	public List<PlanAdvisorCountTO> getPlanCountByOwner(SearchPlanTO form){
		return dao.getPlanCountByOwner(form);
	}
	
	@Override
	public List<PlanCourseCountTO> getPlanCountByCourse(SearchPlanTO form){
		return dao.getPlanCountByCourse(form);
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
			MessageTemplatePlanPrintParams outputPlan, boolean organizeTermsByReportYear)
			throws ObjectNotFoundException {

		List<TermCourses<Plan,PlanTO>> courses = collectTermCourses(outputPlan.getOutputPlan().getNonOutputTO());
		outputPlan.setTermCourses(courses);
		outputPlan.setTotalPlanCreditHours(calculateTotalPlanHours(courses));

		Map<String,Object> params = new HashMap<String,Object>();
		String programCode = outputPlan.getOutputPlan().getNonOutputTO().getProgramCode();
		if (programCode != null && programCode.trim() != "") {
			params.put("programName", getExternalProgramName(programCode));
		}

		if (organizeTermsByReportYear) {
			params.put("termCoursesByReportYear", organizeTermsCoursesByReportYear(courses));
		}

		SubjectAndBody subjectAndBody = getMessageTemplateService().createMapPlanMatrixOutput(outputPlan, params);
			return subjectAndBody;
	}

	@Override
	public PlanElectiveCourse getPlanElectiveCourse(UUID id) throws ObjectNotFoundException {
		return planElectiveCourseDao.get(id);
	}

	@Override
	public List<PersonLiteTO> getAllPlanOwners() {
		return dao.getAllPlanOwners();
	}

	@Override
	public List<MapTransferGoalReportTO> getTransferGoalReport(List<UUID> transferGoalIds, List<UUID> planOwnerIds,
                                                        UUID programStatus, String planExists, String catalogYear,
														Date modifiedDateFrom, Date modifiedDateTo){
		return dao.getTransferGoalReport(transferGoalIds, planOwnerIds, programStatus, planExists, catalogYear,
				modifiedDateFrom, modifiedDateTo);
	}
}
