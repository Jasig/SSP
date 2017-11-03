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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.dao.AbstractPlanDao;
import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.AbstractPlanCourse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.model.external.ExternalCourseRequisite;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;
import org.jasig.ssp.model.external.ExternalStudentTranscriptNonCourseEntity;
import org.jasig.ssp.model.external.RequisiteCode;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.Elective;
import org.jasig.ssp.service.AbstractAuditableCrudService;
import org.jasig.ssp.service.AbstractPlanService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.ExternalCourseRequisiteService;
import org.jasig.ssp.service.external.ExternalCourseService;
import org.jasig.ssp.service.external.ExternalProgramService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptCourseService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptNonCourseEntityService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.reference.ElectiveService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.transferobject.AbstractPlanCourseTO;
import org.jasig.ssp.transferobject.AbstractPlanOutputTO;
import org.jasig.ssp.transferobject.AbstractPlanTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.TermNoteTO;
import org.jasig.ssp.transferobject.reference.AbstractMessageTemplateMapPrintParamsTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * 
 * @author tony.arland
 */
@Transactional
public  abstract class AbstractPlanServiceImpl<T extends AbstractPlan,
		TO extends AbstractPlanTO<T>, TOO extends AbstractPlanOutputTO<T,TO>,
		TOOMT extends AbstractMessageTemplateMapPrintParamsTO<TOO, T,TO>>
	extends  AbstractAuditableCrudService<T>
	implements AbstractPlanService<T,TO, TOO, TOOMT> {

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
	
	@Autowired
	private ExternalStudentTranscriptCourseService studentTranscriptService;

	@Autowired
	private transient ElectiveService electiveService;

	@Autowired
	private transient ExternalProgramService externalProgramService;

	@Autowired
    private transient ExternalStudentTranscriptNonCourseEntityService externalStudentTranscriptNonCourseEntityService;

	@Autowired
	private transient ConfigService configService;



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

	protected String getInstitutionName() {
		return configService.getByNameNullOrDefaultValue("inst_name");
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public SubjectAndBody createFullOutput(TOO planOutput) throws ObjectNotFoundException
	{
		TO plan = planOutput.getNonOutputTO();
		//TODO eventually find a better way to set the student when in context
		Person student = plan instanceof PlanTO ?  personService.get(UUID.fromString(((PlanTO)plan).getPersonId())) : null;
		Person owner = personService.get(UUID.fromString(plan.getOwnerId()));
		Person modifiedBy = personService.get(planOutput.getNonOutputTO().getModifiedBy().getId());
		
		List<TermCourses<T,TO>> courses = collectTermCourses(plan);
		BigDecimal totalPlanCreditHours = calculateTotalPlanHours(courses);
		BigDecimal totalPlanDevHours = calculateTotalPlanDevHours(courses);

		Map<String,Object> params = new HashMap<String,Object>();
		String programCode = planOutput.getNonOutputTO().getProgramCode();
		if (programCode != null && programCode.trim() != "") {
			params.put("programName", getExternalProgramName(programCode));
		}
		
		SubjectAndBody subjectAndBody = messageTemplateService.createMapPlanFullOutput(student, owner, modifiedBy,
				planOutput, 
				totalPlanCreditHours, 
				totalPlanDevHours, 
				courses,
				getInstitutionName(),
				params);
		return subjectAndBody;
	}

	protected String getExternalProgramName(String programCode) {
		try {
			return externalProgramService.getByCode(programCode).getName();
		} catch (ObjectNotFoundException onfe) {
			return programCode;
		}
	}

	@Override
	@Transactional(readOnly=true)
	public TO validate(TO model) throws ObjectNotFoundException{
		model.setIsValid(true); // SSP-2638. See validatePrerequisites()
		List<? extends AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> courses = model.getCourses();
		Map<String, List<AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>>> coursesByTerm = new HashMap<String, List<AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>>>();
		for(AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>  course: courses){
			course.setIsValidInTerm(true);//Set all courses valid in term
			course.setInvalidReasons(null);
			if(coursesByTerm.containsKey(course.getTermCode())){
				coursesByTerm.get(course.getTermCode()).add(course);
			}else {
				List<AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> termCourses =
						new ArrayList<AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>>();
				termCourses.add(course);
				coursesByTerm.put(course.getTermCode(), termCourses);
			}
		}
		final Boolean areAnyCourseTerms = courseService.hasCourseTerms();
		if ( areAnyCourseTerms != null && areAnyCourseTerms ) {
			for(Map.Entry<String, List<AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>>> entry: coursesByTerm.entrySet()){
				final String termCode = entry.getKey();
				final List<AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> coursesInTerm = entry.getValue();
				final List<String> courseCodesInTerm = Lists.newArrayListWithCapacity(coursesInTerm.size());
				for ( AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> course : coursesInTerm ) {
					courseCodesInTerm.add(course.getCourseCode());
				}
				final List<String> validCourseCodes = getCourseService().getValidCourseCodesForTerm(termCode, courseCodesInTerm);
				for ( AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> course : coursesInTerm ) {
					if(validCourseCodes.contains(course.getCourseCode())){
						continue;
					}else{
						  course.setIsValidInTerm(false);
						  model.setIsValid(false);
						  course.setInvalidReasons("Course: " + course.getFormattedCourse() + " is not currently offered in the selected term.");
					}
				}
			}
		}
		model = validatePrerequisites(model);
		return model;
	}
	
	private TO validatePrerequisites(TO model) throws ObjectNotFoundException{

	    final List<? extends AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> courses = model.getCourses();
		final List<String> requiringCourseCodes = new ArrayList<String>();
		for (AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>  course: courses) {
			requiringCourseCodes.add(course.getCourseCode());
			// Only clear validation fields this method controls exclusively. Fields that might be set by validate(),
			// e.g. plan.isValid and course.isValidInTerm, need to be cleared in that method.
			course.setHasCorequisites(true);
			course.setHasPrerequisites(true);
			course.setDuplicateOfTranscript(false);
		}
		
		final List<String> transcriptedCourseCodeCourse = new ArrayList<String>();
		final Map<String, ExternalStudentTranscriptCourse> transcriptedCoursesByFormattedCourseCode = new HashMap<String,ExternalStudentTranscriptCourse>();
		final String studentSchoolId = getSchoolIdPlannedFor(model);
		if (StringUtils.isNotBlank(studentSchoolId)) {
			final List<ExternalStudentTranscriptCourse> transcriptedCourses = studentTranscriptService.getTranscriptsBySchoolId(studentSchoolId);
			for (ExternalStudentTranscriptCourse transcriptedCourse:transcriptedCourses) {
				transcriptedCourseCodeCourse.add(transcriptedCourse.getFormattedCourse());
				transcriptedCoursesByFormattedCourseCode.put(transcriptedCourse.getFormattedCourse(), transcriptedCourse);
			}
		}

		final Set<String> passingGrades = new HashSet<String>(Arrays.asList(configService.getByNameNullOrDefaultValue("map_plan_status_passing_grades").replaceAll(" ", "").split(",")));
		final Map<String, Term> termCodeTerm = new HashMap<String, Term>();
		final Map<String, String> courseCodeTermCode = new HashMap<String,String>();
		final Map<String, AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>> courseCodeCourse = new HashMap<String,AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>>();
		final List<ExternalCourseRequisite> requisiteCourses = getCourseRequisiteService().getRequisitesForCourses(requiringCourseCodes);
		for (AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>  course: courses){
			
			if (!termCodeTerm.containsKey(course.getTermCode())) {
				try {
					termCodeTerm.put(course.getTermCode(), getTermService().getByCode(course.getTermCode()));
				} catch ( ObjectNotFoundException e ) {
					// nothing to be done
					// (this sort of failed lookup has been in the field, e.g. where you're looking up an existing
					// MAP while the back-end ETL job had truncated external_term and hadn't gotten around to
					// filling it again
				}
			}
			courseCodeTermCode.put(course.getCourseCode(), course.getTermCode());
			courseCodeCourse.put(course.getCourseCode(), course);

			// This section sets isTranscript and duplicateOfTranscript since course may be from client.
			if (transcriptedCoursesByFormattedCourseCode.containsKey(course.getFormattedCourse())) {
				final ExternalStudentTranscriptCourse transcriptCourse = transcriptedCoursesByFormattedCourseCode.get(course.getFormattedCourse());
                if (passingGrades.contains(transcriptCourse.getGrade())) {
					course.setIsTranscript(true);
					if (!transcriptCourse.getTermCode().equals(course.getTermCode())) {
						course.setDuplicateOfTranscript(true);
					}
				} else {
					course.setIsTranscript(false);
					course.setDuplicateOfTranscript(false);
				}
			}
		}

        final Collection<ExternalStudentTranscriptNonCourseEntity> nonCourses = externalStudentTranscriptNonCourseEntityService.getNonCourseTranscriptsBySchoolId(studentSchoolId);
        final Map<String, String> nonCourseByFormattedCourses = Maps.newHashMap();
        for (ExternalStudentTranscriptNonCourseEntity index : nonCourses) {
            nonCourseByFormattedCourses.put(index.getTargetFormattedCourse().trim(), index.getNonCourseCode()); //load non course by formatted course map
        }
        for (ExternalCourseRequisite  requisiteCourse: requisiteCourses) {
			if (transcriptedCourseCodeCourse.contains(requisiteCourse.getRequiredFormattedCourse())) {
				continue;
			}

			final AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> requiringCourse = courseCodeCourse.get(requisiteCourse.getRequiringCourseCode());
			final AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> requiredCourse = courseCodeCourse.get(requisiteCourse.getRequiredCourseCode());
			if (nonCourseByFormattedCourses.containsKey(requisiteCourse.getRequiredFormattedCourse().trim())) {
			    continue; //matching non-course bypasses requisite check below and should give no warnings

            } else if (courseCodeTermCode.containsKey(requisiteCourse.getRequiredCourseCode())) {
				final String requiredTermCode = courseCodeTermCode.get(requisiteCourse.getRequiredCourseCode());
				final String requireingTermCode = courseCodeTermCode.get(requisiteCourse.getRequiringCourseCode());
				final Term requiredTerm = termCodeTerm.get(requiredTermCode);
				final Term requireingTerm = termCodeTerm.get(requireingTermCode);

				if (requisiteCourse.getRequisiteCode().equals(RequisiteCode.CO)) {
					if (!requiredTermCode.equals(requireingTermCode)) {
						requiringCourse.setHasCorequisites(false);
						model.setIsValid(false);
						requiringCourse.setInvalidReasons(" Corequisite " + requiredCourse.getFormattedCourse() + " is not in same term.");
					}
				} else if (requisiteCourse.getRequisiteCode().equals(RequisiteCode.PRE_CO)) {
					if (requiredTermCode.equals(requireingTermCode)) {
						continue;
					}
					if (requiredTerm == null || requireingTerm == null) {
						// no guarantee that we can resolve term codes
						continue;
					}
					if (requireingTerm.getStartDate().before(requiredTerm.getStartDate())) {
						requiringCourse.setHasCorequisites(false);
						requiringCourse.setHasPrerequisites(false);
						model.setIsValid(false);
						requiringCourse.setInvalidReasons(" Pre/Corequisite " + requiredCourse.getFormattedCourse() + " is not in previous term.");
					}
				} else if (requisiteCourse.getRequisiteCode().equals(RequisiteCode.PRE)) {
					if (requiredTermCode.equals(requireingTermCode)) {
						requiringCourse.setHasPrerequisites(false);
						model.setIsValid(false);
						requiringCourse.setInvalidReasons(" Prerequisite " + requiredCourse.getFormattedCourse() + " is in same term.");
					} else if (requireingTerm.getStartDate().before(requiredTerm.getStartDate())) {
						requiringCourse.setHasPrerequisites(false);
						model.setIsValid(false);
						requiringCourse.setInvalidReasons(" Prerequisite " + requiredCourse.getFormattedCourse() + " is not in previous term.");
					}
				}
			} else {
				requiringCourse.setHasCorequisites(false);
				requiringCourse.setHasPrerequisites(false);
				model.setIsValid(false);
				requiringCourse.setInvalidReasons(" Pre/co requisite + " + requisiteCourse.getRequiredFormattedCourse() + " is missing.");
			}
		}
		
		return model;
	}

	protected String getSchoolIdPlannedFor(TO model) throws ObjectNotFoundException {
		UUID personId = getPersonIdPlannedFor(model);
		return personId == null ? null : personService.getSchoolIdForPersonId(personId);
	}

	protected abstract UUID getPersonIdPlannedFor(TO model);

	private BigDecimal calculateTotalPlanDevHours(List<TermCourses<T, TO>> courses) {
		BigDecimal totalDevCreditHours = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
		for(TermCourses<T,TO> termCourses : courses){
			totalDevCreditHours = totalDevCreditHours.add(termCourses.getTotalDevCreditHours());
		}
		return totalDevCreditHours;
	}

	protected BigDecimal calculateTotalPlanHours(List<TermCourses<T,TO>> courses) {
		BigDecimal totalPlanCreditHours = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
		for(TermCourses<T,TO> termCourses : courses){
			totalPlanCreditHours = totalPlanCreditHours.add(termCourses.getTotalCreditHours());
		}
		return totalPlanCreditHours;
	}

	protected List<TermCourses<T,TO>> collectTermCourses(TO plan) throws ObjectNotFoundException {
		Map<String,TermCourses<T,TO>> semesterCourses = new HashMap<String, TermCourses<T,TO>>();
		List<TermNoteTO> termNotes = plan.getTermNotes();
		List<Term> currentAndFutureTerms = getTermService().getCurrentAndFutureTerms();
		List<Term> futureTerms = currentAndFutureTerms.subList(0, Math.min(6,currentAndFutureTerms.size()));
		for(AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> course : plan.getCourses()){
			if (course.getElectiveId()!=null) {
				Elective elective = electiveService.get(course.getElectiveId());
				course.setElectiveCode(elective.getCode());
				course.setElectiveName(elective.getName());
			}
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
			
		// Order terms in map for printing
		Collections.sort(courses, new Comparator<TermCourses<T,TO>>() {
		    public int compare(TermCourses<T,TO> a, TermCourses<T,TO> b) {
		       return a.getTerm().getStartDate().compareTo(b.getTerm().getStartDate());
		    }
		});
				
		// Order courses in terms for printing
		for(TermCourses<T, TO> course: semesterCourses.values())
			Collections.sort(course.getCourses(), new Comparator<AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>>>() {
			    public int compare(AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> a, AbstractPlanCourseTO<T, ? extends AbstractPlanCourse<T>> b) {
			       return a.getOrderInTerm().compareTo(b.getOrderInTerm());
			    }
		});
		
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

	protected List<List<TermCourses<T,TO>>> organizeTermsCoursesByReportYear(List<TermCourses<T,TO>> termCoursesList) {
		List<List<TermCourses<T,TO>>> termCoursesByReportYear = new ArrayList<>();

		List<TermCourses<T,TO>> newTermCourses = new ArrayList();
		int previousReportYear = 0;
		for (TermCourses<T,TO> termCourses : termCoursesList) {
			if (previousReportYear != 0 && termCourses.getTerm().getReportYear() != previousReportYear) {
				termCoursesByReportYear.add(newTermCourses);
				newTermCourses = new ArrayList();
			}
			newTermCourses.add(termCourses);
			previousReportYear = termCourses.getTerm().getReportYear();
		}
		termCoursesByReportYear.add(newTermCourses);

		return  termCoursesByReportYear;
	}
}