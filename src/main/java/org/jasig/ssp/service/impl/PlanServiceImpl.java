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

import java.util.UUID;

import org.jasig.ssp.dao.PlanDao;
import org.jasig.ssp.factory.reference.PlanTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.transferobject.PlanTO;
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
public  class PlanServiceImpl extends AbstractPlanServiceImpl<Plan,PlanTO> implements PlanService {

	@Autowired
	private PlanDao dao;
	


	
	@Autowired
	private PlanTOFactory planTOFactory;
	
	@Override
	protected PlanDao getDao() {
		return dao;
	}

	public void setDao(PlanDao dao) {
		this.dao = dao;
	}

	@Override
	public Plan getCurrentForStudent(UUID personId) {
		return dao.getActivePlanForStudent(personId);
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
	   
//	@Override
//	@Transactional(readOnly=true)
//	public SubjectAndBody createMapPlanMatirxOutput(final PlanTO plan, String institutionName) throws ObjectNotFoundException {
//		Person student = personService.get(UUID.fromString(plan.getPersonId()));
//		Person owner = personService.get(UUID.fromString(plan.getOwnerId()));
//		
//		
//		List<TermCourses> courses = collectTermCourses(plan);
//		Float totalPlanCreditHours = calculateTotalPlanHours(courses);
//		
//		SubjectAndBody subjectAndBody = messageTemplateService.createMapPlanMatrixOutput(student, owner, plan, totalPlanCreditHours, courses, institutionName);
//		return subjectAndBody;
//	}
//	
//	@Override
//	public SubjectAndBody createMapPlanFullOutput(PlanOutputTO planOutput, String institutionName) throws ObjectNotFoundException
//	{
//		PlanTO plan = planOutput.getPlan();
//		
//		Person student = personService.get(UUID.fromString(plan.getPersonId()));
//		Person owner = personService.get(UUID.fromString(plan.getOwnerId()));
//		
//		
//		List<TermCourses> courses = collectTermCourses(plan);
//		Float totalPlanCreditHours = calculateTotalPlanHours(courses);
//		Float totalPlanDevHours = calculateTotalPlanDevHours(courses);
//		
//		SubjectAndBody subjectAndBody = messageTemplateService.createMapPlanFullOutput(student, owner, 
//				planOutput, 
//				totalPlanCreditHours, 
//				totalPlanDevHours, 
//				courses, 
//				institutionName);
//		return subjectAndBody;
//	}
//	
//	private List<TermCourses> collectTermCourses(PlanTO plan) throws ObjectNotFoundException{
//		Map<String,TermCourses> semesterCourses = new HashMap<String, TermCourses>();
//		List<TermNoteTO> termNotes = plan.getTermNotes();
//		List<Term> futureTerms = termService.getCurrentAndFutureTerms().subList(0, 6);
//		for(PlanCourseTO course : plan.getPlanCourses()){				
//			if(!semesterCourses.containsKey(course.getTermCode())){
//				Term term = termService.getByCode(course.getTermCode());
//				
//				
//				TermCourses termCourses = new TermCourses(term);
//				int termNoteIndex = termNotes.indexOf(term);
//				if(termNoteIndex > -1){
//					TermNoteTO termNote = termNotes.get(termNoteIndex);
//					termCourses.setContactNotes(termNote.getContactNotes());
//					termCourses.setStudentNotes(termNote.getStudentNotes());
//					termCourses.setIsImportant(termNote.getIsImportant());
//				}
//				course.setPlanToOffer(getPlanToOfferTerms(course, futureTerms));
//				termCourses.addCourse(course);
//				semesterCourses.put(term.getCode(), termCourses);
//			}else{
//				semesterCourses.get(course.getTermCode()).addCourse(course);
//			}
//		}
//		List<TermCourses> courses =  Lists.newArrayList(semesterCourses.values());
//		Collections.sort(courses, TermCourses.TERM_START_DATE_COMPARATOR);
//		return courses;
//	}
//	
//	private String getPlanToOfferTerms(PlanCourseTO course, List<Term> futureTerms) throws ObjectNotFoundException{
//		
//		String planToOffer = "";
//		Integer offeredTerms = 0;
//		for(Term offeredTerm:futureTerms){
//			if(courseService.validateCourseForTerm(course.getCourseCode(), offeredTerm.getCode())){
//				planToOffer = planToOffer + offeredTerm.getName() + " ";
//				offeredTerms = offeredTerms + 1;
//			}
//			if(offeredTerms >= 4)
//				break;
//		}
//		return planToOffer;
//	}
//	
//	private Float calculateTotalPlanHours(List<TermCourses> courses){
//		Float totalPlanCreditHours = new Float(0);
//		for(TermCourses termCourses : courses){
//			totalPlanCreditHours = totalPlanCreditHours + termCourses.getTotalCreditHours();
//		}
//		return totalPlanCreditHours;
//	}
//	
//	private Float calculateTotalPlanDevHours(List<TermCourses> courses){
//		Float totalPlanCreditHours = new Float(0);
//		for(TermCourses termCourses : courses){
//			totalPlanCreditHours = totalPlanCreditHours +  termCourses.getTotalDevCreditHours();
//		}
//		return totalPlanCreditHours;
//	}
//	

	
	@Override
	public Plan save(Plan obj) {
		//If plan has been marked as active, we must mark all other plans as inactive
		if(ObjectStatus.ACTIVE.equals(obj.getObjectStatus()))
		{
			getDao().markOldPlansAsInActive(obj);
		}	
		return super.save(obj);
	}

}