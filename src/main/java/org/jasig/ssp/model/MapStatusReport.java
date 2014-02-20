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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.jasig.ssp.model.external.PlanStatus;
import org.springframework.context.annotation.Lazy;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class MapStatusReport extends AbstractAuditable implements PersonAssocAuditable {
	
	private static final long serialVersionUID = -246347869310612338L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", orphanRemoval=true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private List<MapStatusReportCourseDetails> courseDetails = new ArrayList<MapStatusReportCourseDetails>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "report", orphanRemoval=true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private List<MapStatusReportTermDetails> termDetails = new ArrayList<MapStatusReportTermDetails>(0);
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@NotNull
	@ManyToOne()
	@LazyToOne(value = LazyToOneOption.PROXY)
	@JoinColumn(name = "plan_id", updatable = false, nullable = false)
	private Plan plan;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PlanStatus planStatus;
	
	@Column(length = 500)
	@Size(max = 500)
	private String planNote;
	
	public MapStatusReport(UUID id) {
		super();
		setId(id);
	}

	public MapStatusReport() {
		super();
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	protected int hashPrime() {
		return 0;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public List<MapStatusReportCourseDetails> getCourseDetails() {
		return courseDetails;
	}

	public void setCourseDetails(List<MapStatusReportCourseDetails> courseDetails) {
		this.courseDetails = courseDetails;
	}

	public List<MapStatusReportTermDetails> getTermDetails() {
		return termDetails;
	}

	public void setTermDetails(List<MapStatusReportTermDetails> termDetails) {
		this.termDetails = termDetails;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public String getPlanNote() {
		return planNote;
	}

	public void setPlanNote(String planNote) {
		this.planNote = planNote;
	}

	public PlanStatus getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(PlanStatus planStatus) {
		this.planStatus = planStatus;
	}


}
