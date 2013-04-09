package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.PlanCourse;

public class PlanCourseTO extends AbstractPlanCourseTO<PlanCourse> {

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
