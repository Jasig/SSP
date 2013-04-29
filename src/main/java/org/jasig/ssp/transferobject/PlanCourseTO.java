package org.jasig.ssp.transferobject;

import java.util.Comparator;

import org.jasig.ssp.model.PlanCourse;

public class PlanCourseTO extends AbstractPlanCourseTO<PlanCourse> {

	public static class PlanCourseIndexComparator implements Comparator<PlanCourseTO> {
		 @Override
		    public int compare(PlanCourseTO o1, PlanCourseTO o2) {
		        return o1.getOrderInTerm().compareTo(o2.getOrderInTerm());
		    }

	}

	public static final PlanCourseIndexComparator TERM_ORDER_COMPARATOR =
			new PlanCourseIndexComparator();
	
	private String personId;
	
	

	public PlanCourseTO(PlanCourse planCourse) {
		super();
		from(planCourse);
	}

	/**
	 * Empty constructor.
	 */
	public PlanCourseTO() {
		super();
	}

	@Override
	public void from(PlanCourse model) {
		super.from(model);
		this.setPersonId(model.getPerson().getId().toString());
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
}
