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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "plan", orphanRemoval=true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@OrderBy("orderInTerm")	
	private List<PlanCourse> planCourses = new ArrayList<PlanCourse>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "plan", orphanRemoval=true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private List<TermNotes> termNotes = new ArrayList<TermNotes>(0);
	
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
			planCourseClone.setPerson(clone.getPerson());
			clone.getPlanCourses().add(planCourseClone);
		}
		return clone;
	}

	public List<TermNotes> getTermNotes() {
		return termNotes;
	}

	public void setTermNotes(List<TermNotes> termNotes) {
		this.termNotes = termNotes;
	}
	 
}
