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
package org.jasig.ssp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.transferobject.AbstractPlanCourseTO;
import org.jasig.ssp.transferobject.AbstractPlanTO;
import org.jasig.ssp.transferobject.PlanCourseTO;

public class TermCourses<T extends AbstractPlan, TO extends AbstractPlanTO<T>> {
	
	@SuppressWarnings("rawtypes")
	public static class TermCoursesTermDateComparator implements Comparator<TermCourses> {
		 @Override
		    public int compare(TermCourses o1, TermCourses o2) {
		        return o1.getTerm().getStartDate().compareTo( o2.getTerm().getStartDate());
		    }

	}

	public static final TermCoursesTermDateComparator TERM_START_DATE_COMPARATOR =
			new TermCoursesTermDateComparator();
	
	private Term term;
	private List<AbstractPlanCourseTO<T,? extends AbstractPlanCourse<T>>> courses;
	private BigDecimal totalCreditHours;
	private BigDecimal totalDevCreditHours;
	private String contactNotes;
	private String studentNotes;
	private Boolean isImportant;
	private Boolean isElective;
	
	
	/**
	 * @return the term
	 */
	public Term getTerm() {
		return term;
	}
	/**
	 * @param term the term to set
	 */
	public void setTerm(Term term) {
		this.term = term;
	}
	/**
	 * @return the courses
	 */
	public List<AbstractPlanCourseTO<T,? extends AbstractPlanCourse<T>>> getCourses() {
		return courses;
	}
	/**
	 * @param courses the courses to set
	 */
	public void setCourses(List<AbstractPlanCourseTO<T,? extends AbstractPlanCourse<T>>> courses) {
		this.courses = courses;
		updateCreditHours();
	}

	public void addCourse(AbstractPlanCourseTO<T,? extends AbstractPlanCourse<T>> planCourse){
		courses.add(planCourse);
		updateCreditHours();
	}
	
	private void updateCreditHours(){
		totalCreditHours = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
		totalDevCreditHours = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
		for(AbstractPlanCourseTO<T,? extends AbstractPlanCourse<T>> course:courses){
			totalCreditHours = totalCreditHours.add(course.getCreditHours());
			if(course.isDev())
				totalDevCreditHours = totalDevCreditHours.add(course.getCreditHours());
		}
	}
	/**
	 * @param term The term model object
	 */
	public TermCourses(Term term) {
		super();
		this.term = term;
		this.courses = new ArrayList<AbstractPlanCourseTO<T,? extends AbstractPlanCourse<T>>>();
	}
	
	/**
	 * @return the totalCreditHours
	 */
	public BigDecimal getTotalCreditHours() {
		return totalCreditHours;
	}
	
	/**
	 * @param totalDevCreditHours the totalDevCreditHours to set
	 */
	public void setTotalDevHours(BigDecimal totalDevCreditHours) {
		this.totalDevCreditHours = totalDevCreditHours;
	}
	
	/**
	 * @return the totalDevCreditHours
	 */
	public BigDecimal getTotalDevCreditHours() {
		return totalDevCreditHours;
	}
	
	/**
	 * @param totalCreditHours the totalCreditHours to set
	 */
	public void setTotalCreditHours(BigDecimal totalCreditHours) {
		this.totalCreditHours = totalCreditHours;
	}
	/**
	 * @return the contactNotes
	 */
	public String getContactNotes() {
		return contactNotes;
	}
	/**
	 * @param contactNotes the contactNotes to set
	 */
	public void setContactNotes(String contactNotes) {
		this.contactNotes = contactNotes;
	}
	/**
	 * @return the studentNotes
	 */
	public String getStudentNotes() {
		return studentNotes;
	}
	/**
	 * @param studentNotes the studentNotes to set
	 */
	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}
	/**
	 * @return the isImportant
	 */
	public Boolean getIsImportant() {
		return isImportant;
	}
	/**
	 * @param isImportant the isImportant to set
	 */
	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}
	/**
	 * @return the isElective
	 */
	public Boolean getIsElective() {
		return isElective;
	}
	/**
	 * @param isElective the isElective to set
	 */
	public void setIsElective(Boolean isElective) {
		this.isElective = isElective;
	}
	/**
	 * @param totalDevCreditHours the totalDevCreditHours to set
	 */
	public void setTotalDevCreditHours(BigDecimal totalDevCreditHours) {
		this.totalDevCreditHours = totalDevCreditHours;
	}

}
