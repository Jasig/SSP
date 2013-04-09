package org.jasig.ssp.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="map_plan_course")
public class PlanCourse extends AbstractPlanCourse {

	private static final long serialVersionUID = -6316130725863888876L;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "plan_id", updatable = false, nullable = false)	
	private Plan plan;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	@Override
	protected PlanCourse clone() throws CloneNotSupportedException {
		PlanCourse clone = new PlanCourse();
		clone.setCourseCode(this.getCourseCode());
		clone.setCourseDescription(this.getCourseDescription());
		clone.setCourseTitle(this.getCourseTitle());
		clone.setCourseDescription(this.getCourseDescription());
		clone.setCreditHours(this.getCreditHours());
		clone.setFormattedCourse(this.getFormattedCourse());
		clone.setIsDev(this.isDev());
		clone.setOrderInTerm(this.getOrderInTerm());
		clone.setPerson(this.getPerson());
		clone.setTermCode(this.getTermCode());
		return clone;
	}
}
