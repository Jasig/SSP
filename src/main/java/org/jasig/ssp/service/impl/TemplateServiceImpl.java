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
import org.jasig.ssp.dao.TemplateCourseDao;
import org.jasig.ssp.dao.TemplateDao;
import org.jasig.ssp.dao.reference.TemplateElectiveCourseDao;
import org.jasig.ssp.model.AbstractPlanCourse;
import org.jasig.ssp.model.MapTemplateVisibility;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TemplateService;
import org.jasig.ssp.service.external.ExternalDepartmentService;
import org.jasig.ssp.service.external.ExternalDivisionService;
import org.jasig.ssp.service.external.ExternalProgramService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.TemplateOutputTO;
import org.jasig.ssp.transferobject.TemplateSearchTO;
import org.jasig.ssp.transferobject.TemplateTO;
import org.jasig.ssp.transferobject.reference.MessageTemplatePlanTemplatePrintParamsTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * 
 * @author tony.arland
 */
@Service
@Transactional
public  class TemplateServiceImpl extends AbstractPlanServiceImpl<Template,
TemplateTO,TemplateOutputTO, MessageTemplatePlanTemplatePrintParamsTO> implements TemplateService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TemplateServiceImpl.class);
	
	private static String ANONYMOUS_MAP_TEMPLATE_ACCESS="anonymous_map_template_access";

	@Autowired
	private transient TemplateDao dao;

	@Autowired
	private transient TemplateCourseDao templateCourseDao;

	@Autowired
	private PersonService personService;
	
	@Autowired
	private transient ExternalDepartmentService departmentService;
	
	@Autowired
	private transient ExternalDivisionService divisionService;
	
	@Autowired
	private transient ExternalProgramService programService;
	
	@Autowired
	private transient SecurityService securityService;
	
	@Autowired
	private transient ConfigService configService;
	
	@Override
	protected TemplateDao getDao() {
		return dao;
	}

	@Autowired
	private transient TemplateElectiveCourseDao templateElectiveCourseDao;

	@Override
	protected UUID getPersonIdPlannedFor(TemplateTO model) {
		// templates are not "planned for" anybody in particular
		return null;
	}
	
	@Override
	public Person getOwnerForPlan(UUID id) {
		return dao.getOwnerForPlan(id);
	}

	@Override
	@Transactional(readOnly=true)
	public SubjectAndBody createOutput(TemplateOutputTO templateOutputDataTO) throws ObjectNotFoundException {

		SubjectAndBody output = null;
		
		MessageTemplatePlanTemplatePrintParamsTO params = new MessageTemplatePlanTemplatePrintParamsTO();
		params.setMessageTemplateId(templateOutputDataTO.getMessageTemplateMatrixId());
		params.setOutputPlan(templateOutputDataTO);
		
		TemplateTO to = templateOutputDataTO.getNonOutputTO();
		if(StringUtils.isNotBlank(to.getDepartmentCode())) {
			try {
				params.setDepartmentName(departmentService.getByCode(to.getDepartmentCode()).getName());
			} catch ( ObjectNotFoundException e ) {
				LOGGER.info("Template {} has invalid department code {}", to.getId(), to.getDepartmentCode());
			}
		}

		if(StringUtils.isNotBlank(to.getDivisionCode())) {
			try {
				params.setDivisionName(divisionService.getByCode(to.getDivisionCode()).getName());
			} catch ( ObjectNotFoundException e ) {
				LOGGER.info("Template {} has invalid division code {}", to.getId(), to.getDivisionCode());
			}
		}

		if(StringUtils.isNotBlank(to.getProgramCode())) {
			try {
				params.setProgramName(programService.getByCode(to.getProgramCode()).getName());
			} catch ( ObjectNotFoundException e ) {
				LOGGER.info("Template {} has invalid program code {}", to.getId(), to.getProgramCode());
			}
		}
		
		params.setInstitutionName(getInstitutionName());
		
		if(StringUtils.isNotBlank(templateOutputDataTO.getPlan().getOwnerId())){
			params.setOwner(personService.get(
					UUID.fromString(templateOutputDataTO.getPlan().getOwnerId())));
		}

		params.setLastModifiedBy(personService.get(templateOutputDataTO.getPlan().getModifiedBy().getId()));

		if(templateOutputDataTO.getOutputFormat().equals(TemplateService.OUTPUT_FORMAT_MATRIX)) {
			output = createMatrixOutput(params, false);
		} else if (templateOutputDataTO.getOutputFormat().equals(TemplateService.OUTPUT_SHORT_MATRIX)){
			output = createMatrixOutput(params, true);
		} else{
			output = createFullOutput(templateOutputDataTO);
		}

		return output;
	}

	@Override
	public PagingWrapper<Template> getAll(
			SortingAndPaging createForSingleSortWithPaging,TemplateSearchTO searchTO) {
		return getDao().getAll(createForSingleSortWithPaging, searchTO);
	}
	
	@Override
	@Transactional(readOnly=true)
	public TemplateTO validate(TemplateTO model) throws ObjectNotFoundException{
		model = super.validate(model);
		if(model.getVisibility().equals(MapTemplateVisibility.PRIVATE))
			model.setIsPrivate(true);
		else
			model.setIsPrivate(false);
		return model;
	}
	
	private Boolean anonymousUsersAllowed() {
		return Boolean.parseBoolean(configService.getByName(ANONYMOUS_MAP_TEMPLATE_ACCESS).getValue().toLowerCase());
	}

	@Override
	public SubjectAndBody createMatrixOutput(
			MessageTemplatePlanTemplatePrintParamsTO outputPlan, boolean organizeTermsByReportYear)
			throws ObjectNotFoundException {
		List<TermCourses<Template,TemplateTO>> courses = collectTermCourses(outputPlan.getOutputPlan().getNonOutputTO());
		outputPlan.setTermCourses(courses);
		outputPlan.setTotalPlanCreditHours(calculateTotalPlanHours(courses));
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("departmentName", outputPlan.getDepartmentName());
		params.put("divisionName", outputPlan.getDivisionName());
		params.put("programName", outputPlan.getProgramName());

		if (organizeTermsByReportYear) {
			params.put("termCoursesByReportYear", organizeTermsCoursesByReportYear(courses));
		}

		SubjectAndBody subjectAndBody = getMessageTemplateService().createMapPlanMatrixOutput(outputPlan, params);
		return subjectAndBody;
	}

	@Override
	public TemplateCourse getTemplateCourse(UUID id) throws ObjectNotFoundException {
		return templateCourseDao.get(id);
	}

	@Override
	public List<TemplateCourse> getUniqueTemplateCourseList(UUID id) {
		return templateCourseDao.getAllCoursesForTemplate(id);
	}

	@Override
	public TemplateElectiveCourse getTemplateElectiveCourse(UUID id) throws ObjectNotFoundException {
		return templateElectiveCourseDao.get(id);
	}
	@Override
	public Template save(Template obj) {
		cleanUpOrphanedElectiveCourses(obj);
		return super.save(obj);
	}

	@Override
	public Template copyAndSave(Template model, Person newOwner) throws CloneNotSupportedException {
		cleanUpOrphanedElectiveCourses(model);
		return super.copyAndSave(model, newOwner);
	}

	private void cleanUpOrphanedElectiveCourses(Template template) {
		if (null!=template.getPlanElectiveCourses()) {
//			for (TemplateElectiveCourse templateElectiveCourse : template.getPlanElectiveCourses()) {
//				if (!hasFormattedCourse(templateElectiveCourse.getFormattedCourse(), template.getCourses())) {
//					template.getPlanElectiveCourses().remove(templateElectiveCourse);
//					templateElectiveCourseDao.delete(templateElectiveCourse);
//				}
//			}

			Iterator<TemplateElectiveCourse> iter = template.getPlanElectiveCourses().iterator();
			while (iter.hasNext()) {
				TemplateElectiveCourse templateElectiveCourse = iter.next();
				if (!hasFormattedCourse(templateElectiveCourse.getFormattedCourse(), template.getCourses())) {
					iter.remove();
					templateElectiveCourseDao.delete(templateElectiveCourse);
				}
			}
		}
	}
	private boolean hasFormattedCourse (String formattedCourse, List<? extends AbstractPlanCourse<?>> templateCourses) {
		if (null!=templateCourses) {
			for (AbstractPlanCourse templateCourse : templateCourses) {
				if (templateCourse.getFormattedCourse().equals((formattedCourse))) {
					return true;
				}
			}
		}
		return false;
	}
}