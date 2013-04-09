package org.jasig.ssp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="map_plan")
public class Plan extends AbstractPlan  {

	
	private static final long serialVersionUID = -681245136521277249L;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "plan", orphanRemoval=true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@OrderBy("orderInTerm")	
	private List<PlanCourse> planCourses = new ArrayList<PlanCourse>(0);
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	public Plan(UUID id) {
		super();
		setId(id);
	}

	public Plan() {
		super();
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<PlanCourse> getPlanCourses() {
		return planCourses;
	}

	public void setPlanCourses(List<PlanCourse> planCourses) {
		this.planCourses = planCourses;
	}

	@Override
	public Plan clone() throws CloneNotSupportedException {
		Plan clone = new Plan();
		clone.setName(this.getName());
		//Copying person by should be changed if we're cloning on saving with a new advisor
		clone.setOwner(this.getOwner());
		clone.setPerson(this.getPerson());
		List<PlanCourse> planCourses = this.getPlanCourses();
		for (PlanCourse planCourse : planCourses) {
			PlanCourse planCourseClone = planCourse.clone();
			planCourseClone.setPlan(clone);
			clone.getPlanCourses().add(planCourseClone);
		}
		return clone;
	}
	 
}
