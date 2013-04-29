package org.jasig.ssp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.transferobject.PlanCourseTO;

public class TermCourses {
	
	public static class TermCoursesTermDateComparator implements Comparator<TermCourses> {
		 @Override
		    public int compare(TermCourses o1, TermCourses o2) {
		        return o1.getTerm().getStartDate().compareTo( o2.getTerm().getStartDate());
		    }

	}

	public static final TermCoursesTermDateComparator TERM_START_DATE_COMPARATOR =
			new TermCoursesTermDateComparator();
	
	Term term;
	List<PlanCourseTO> courses;
	Float totalCreditHours;
	
	
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
	public List<PlanCourseTO> getCourses() {
		return courses;
	}
	/**
	 * @param courses the courses to set
	 */
	public void setCourses(List<PlanCourseTO> courses) {
		this.courses = courses;
		updateCreditHours();
	}

	public void addCourse(PlanCourseTO planCourse){
		courses.add(planCourse);
		updateCreditHours();
	}
	
	private void updateCreditHours(){
		totalCreditHours = new Float(0);
		for(PlanCourseTO course:courses){
			totalCreditHours = totalCreditHours + new Float(course.getCreditHours());
		}
	}
	/**
	 * @param term
	 */
	public TermCourses(Term term) {
		super();
		this.term = term;
		this.courses = new ArrayList<PlanCourseTO>();
	}
	
	/**
	 * @return the totalCreditHours
	 */
	public Float getTotalCreditHours() {
		return totalCreditHours;
	}
	
	/**
	 * @param totalCreditHours the totalCreditHours to set
	 */
	public void setTotalCreditHours(Float totalCreditHours) {
		this.totalCreditHours = totalCreditHours;
	}

}
