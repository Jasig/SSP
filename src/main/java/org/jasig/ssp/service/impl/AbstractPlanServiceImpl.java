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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.AbstractPlanDao;
import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.AbstractPlanCourse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.model.external.ExternalCourseRequisite;
import org.jasig.ssp.model.external.RequisiteCode;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.AbstractPlanService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.ExternalCourseRequisiteService;
import org.jasig.ssp.service.external.ExternalCourseService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.AbstractPlanCourseTO;
import org.jasig.ssp.transferobject.AbstractPlanOutputTO;
import org.jasig.ssp.transferobject.AbstractPlanTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.TermNoteTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

/**
 * 
 * @author tony.arland
 */
@Transactional
public  abstract class AbstractPlanServiceImpl<T extends AbstractPlan, TO extends AbstractPlanTO<T>> extends  AbstractAuditableCrudService<T> implements AbstractPlanService<T,TO> {

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private MessageTemplateService messageTemplateService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private ExternalCourseService courseService;
	
	@Autowired
	private ExternalCourseRequisiteService courseRequisiteService;
	
	@Override
	public T save(T obj) {
		return getDao().save(obj);
	}
	
	@Override
	public T get(@NotNull final UUID id) throws ObjectNotFoundException {
		final T obj = getDao().get(id);
		if(obj == null)
			throw new ObjectNotFoundException(id, this.getClass().getName());
		return obj;

	}
	
	@Override
	protected abstract AbstractPlanDao<T> getDao();

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@Override
	public T copyAndSave(T model, Person newOwner) throws CloneNotSupportedException {
		return getDao().cloneAndSave(model, newOwner);
	}
	
	@Override
	@Transactional(readOnly=true)
	public SubjectAndBody createMatirxOutput(final TO outputPlan, String institutionName) throws ObjectNotFoundException {
		
		//TODO eventually find a better  way to set the student when in context
		Person student = outputPlan instanceof PlanTO ?  personService.get(UUID.fromString(((PlanTO)outputPlan).getPersonId())) : null;
		Person owner = getPersonService().get(UUID.fromString(outputPlan.getOwnerId()));
		
		
		List<TermCourses<T,TO>> courses = collectTermCourses(outputPlan);
		Float totalPlanCreditHours = calculateTotalPlanHours(courses);
		 
		SubjectAndBody subjectAndBody = getMessageTemplateService().createMapPlanMatrixOutput(student, owner, outputPlan, totalPlanCreditHours, courses, institutionName);
		return subjectAndBody;
	}
	@Override
	public SubjectAndBody createFullOutput(AbstractPlanOutputTO<T,TO> planOutput, String institutionName) throws ObjectNotFoundException
	{
		TO plan = planOutput.getNonOutputTO();
		//TODO eventually find a better way to set the student when in context
		Person student = plan instanceof PlanTO ?  personService.get(UUID.fromString(((PlanTO)plan).getPersonId())) : null;
		Person owner = personService.get(UUID.fromString(plan.getOwnerId()));
		
		
		List<TermCourses<T,TO>> courses = collectTermCourses(plan);
		Float totalPlanCreditHours = calculateTotalPlanHours(courses);
		Float totalPlanDevHours = calculateTotalPlanDevHours(courses);
		
		SubjectAndBody subjectAndBody = messageTemplateService.createMapPlanFullOutput(student, owner, 
				planOutput, 
				totalPlanCreditHours, 
				totalPlanDevHours, 
				courses, 
				institutionName);
		return subjectAndBody;
	}
	
	@Override
	@Transactional(readOnly=true)
	public TO validate(TO model) throws ObjectNotFoundException{
		List<? extends AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> courses = model.getCourses();
		Map<String, List<String>> coursesByTerm = new HashMap<String, List<String>>();
		Map<String, AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> courseCodeCourse = new HashMap<String,AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>>();
		for(AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>  course: courses){
			course.setIsValidInTerm(true);//Set all courses valid in term
			courseCodeCourse.put(course.getCourseCode(), course);
			if(coursesByTerm.containsKey(course.getTermCode())){
				coursesByTerm.get(course.getTermCode()).add(course.getCourseCode());
			}else {
				List<String> termCourses = Lists.newArrayList(course.getCourseCode());
				coursesByTerm.put(course.getTermCode(), termCourses);
			}
		}
		for(String termCode: coursesByTerm.keySet()){
			List<String> validCourseCodes = getCourseService().getValidCourseCodesForTerm(termCode, coursesByTerm.get(termCode));
			List<String> courseCodesTerm = coursesByTerm.get(termCode);
			for(String courseCode:courseCodesTerm){
				if(validCourseCodes.contains(courseCode)){
					continue;
				}else{
					  AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> course = courseCodeCourse.get(courseCode);
					  course.setIsValidInTerm(false);
					  model.setIsValid(false);
					  course.setInvalidReasons("Course: " + course.getFormattedCourse() + " is not current offered in term.");
				}
			}
		}
		model = validatePrerequisites(model);
		return model;
	}
	
	public TO validatePrerequisites(TO model) throws ObjectNotFoundException{
		List<? extends AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> courses = model.getCourses();
		List<String> requiringCourseCodes = new ArrayList<String>();
		for(AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>  course: courses){
			requiringCourseCodes.add(course.getCourseCode());
		}
		Map<String, Term> termCodeTerm = new HashMap<String, Term>();
		Map<String, String> courseCodeTermCode = new HashMap<String,String>();
		Map<String, AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> courseCodeCourse = new HashMap<String,AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>>();
		List<ExternalCourseRequisite> requisiteCourses = getCourseRequisiteService().getRequisitesForCourses(requiringCourseCodes);
		for(AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>  course: courses){
			
			if(!termCodeTerm.containsKey(course.getTermCode())){
				termCodeTerm.put(course.getTermCode(), getTermService().getByCode(course.getTermCode()));
			}
			courseCodeTermCode.put(course.getCourseCode(), course.getTermCode());
			courseCodeCourse.put(course.getCourseCode(), course);
		}
		
		for(ExternalCourseRequisite  requisiteCourse: requisiteCourses){
			AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> requiringCourse = courseCodeCourse.get(requisiteCourse.getRequiredCourseCode());
			AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> requiredCourse = courseCodeCourse.get(requisiteCourse.getRequiredCourseCode());
			if(courseCodeTermCode.containsKey(requisiteCourse.getRequiredCourseCode())){
				String requiredTermCode = courseCodeTermCode.get(requisiteCourse.getRequiredCourseCode());
				String requireingTermCode = courseCodeTermCode.get(requisiteCourse.getRequiredCourseCode());
				Term requiredTerm = termCodeTerm.get(requiredTermCode);
				Term requireingTerm = termCodeTerm.get(requireingTermCode);
				requiringCourse.setInvalidReasons(null);
				if(requisiteCourse.getRequisiteCode().equals(RequisiteCode.CO)){
					if(!requiredTerm.getCode().equals(requireingTerm.getCode())){
						requiringCourse.setHasCorequisites(false);
						requiringCourse.setInvalidReasons("Corequisite" + requiredCourse.getFormattedCourse() + " is not in same term.");
					}		
				}else if(requisiteCourse.getRequisiteCode().equals(RequisiteCode.PRE_CO)){
					if(requiredTerm.getCode().equals(requireingTerm.getCode())){
						continue;
					}
					if(requireingTerm.getStartDate().before(requiredTerm.getStartDate())){
						requiringCourse.setHasCorequisites(false);
						requiringCourse.setInvalidReasons("Pre/Corequisite " + requiredCourse.getFormattedCourse() + " is not in before term.");
					}
				}else if(requisiteCourse.getRequisiteCode().equals(RequisiteCode.PRE)){
					if(requiredTerm.getCode().equals(requireingTerm.getCode())){
						requiringCourse.setHasCorequisites(false);
						requiringCourse.setInvalidReasons("Prerequisite " + requiredCourse.getFormattedCourse() + " is in same term.");
					}
					if(requireingTerm.getStartDate().before(requiredTerm.getStartDate())){
						requiringCourse.setHasCorequisites(false);
						requiringCourse.setInvalidReasons("Prerequisite " + requiredCourse.getFormattedCourse() + " is not in before term.");
					}
				}
				
			}else{
				requiringCourse.setHasCorequisites(false);
				requiringCourse.setHasPrerequisites(false);
				requiringCourse.setInvalidReasons("Pre/co requisite is missing");
			}
		}
		
		return model;
	}
	
	private Float calculateTotalPlanDevHours(List<TermCourses<T, TO>> courses) {
		Float totalDevCreditHours = new Float(0);
		for(TermCourses<T,TO> termCourses : courses){
			totalDevCreditHours = totalDevCreditHours + termCourses.getTotalDevCreditHours();
		}
		return totalDevCreditHours;
	}

	private Float calculateTotalPlanHours(List<TermCourses<T,TO>> courses) {
		Float totalPlanCreditHours = new Float(0);
		for(TermCourses<T,TO> termCourses : courses){
			totalPlanCreditHours = totalPlanCreditHours + termCourses.getTotalCreditHours();
		}
		return totalPlanCreditHours;
	}

	private List<TermCourses<T,TO>> collectTermCourses(TO plan) throws ObjectNotFoundException {
		Map<String,TermCourses<T,TO>> semesterCourses = new HashMap<String, TermCourses<T,TO>>();
		List<TermNoteTO> termNotes = plan.getTermNotes();
		List<Term> futureTerms = getTermService().getCurrentAndFutureTerms().subList(0, 6);
		for(AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> course : plan.getCourses()){				
			if(!semesterCourses.containsKey(course.getTermCode())){
				Term term = getTermService().getByCode(course.getTermCode());
				
				
				TermCourses<T,TO> termCourses = new TermCourses<T,TO>(term);
				for(TermNoteTO termNote:termNotes){
					if(termNote.getTermCode().equals(term.getCode())){
						termCourses.setContactNotes(termNote.getContactNotes());
						termCourses.setStudentNotes(termNote.getStudentNotes());
						termCourses.setIsImportant(termNote.getIsImportant());
					}
				}
				course.setPlanToOffer(getPlanToOfferTerms(course, futureTerms));
				termCourses.addCourse(course);
				semesterCourses.put(term.getCode(), termCourses);
			}else{
				semesterCourses.get(course.getTermCode()).addCourse(course);
			}
		}
		List<TermCourses<T,TO>> courses =  Lists.newArrayList(semesterCourses.values());
	
		return courses;
	}
	
	

	private String getPlanToOfferTerms(
			AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> course,
			List<Term> futureTerms) {
		String planToOffer = "";
		Integer offeredTerms = 0;
		for(Term offeredTerm:futureTerms){
			if(getCourseService().validateCourseForTerm(course.getCourseCode(), offeredTerm.getCode())){
				planToOffer = planToOffer + offeredTerm.getName() + " ";
				offeredTerms = offeredTerms + 1;
			}
			if(offeredTerms >= 4)
				break;
		}
		return planToOffer;	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public MessageTemplateService getMessageTemplateService() {
		return messageTemplateService;
	}

	public void setMessageTemplateService(MessageTemplateService messageTemplateService) {
		this.messageTemplateService = messageTemplateService;
	}

	public TermService getTermService() {
		return termService;
	}

	public void setTermService(TermService termService) {
		this.termService = termService;
	}

	public ExternalCourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(ExternalCourseService courseService) {
		this.courseService = courseService;
	}
	
	public ExternalCourseRequisiteService getCourseRequisiteService() {
		return courseRequisiteService;
	}

	public void setCourseService(ExternalCourseRequisiteService courseRequisiteService) {
		this.courseRequisiteService = courseRequisiteService;
	}
}