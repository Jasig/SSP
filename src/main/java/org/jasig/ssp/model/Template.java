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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="map_template")
public class Template extends AbstractPlan implements Cloneable{

	private static final long serialVersionUID = 1308748010487627451L;
	
	@Column(length = 50)
	@Size(max = 50)
	private String programCode;
	
	@Column(length = 50)
	@Size(max = 50)
	private String divisionCode;
	
	@Column(length = 50)
	@Size(max = 50)
	private String departmentCode;
	
	@Column(nullable = false)
	private Boolean isPrivate = false;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "template", orphanRemoval=true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@OrderBy("orderInTerm")	
	private List<TemplateCourse> templateCourses = new ArrayList<TemplateCourse>(0);	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "template", orphanRemoval=true)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	private List<TermNote> termNotes = new ArrayList<TermNote>(0);
	
	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public List<TemplateCourse> getTemplateCourses() {
		return templateCourses;
	}

	public void setTemplateCourses(List<TemplateCourse> planCourses) {
		this.templateCourses = planCourses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Template clonePlan() throws CloneNotSupportedException {
		return this.clone();
	}
	
	@Override
	public Template clone() throws CloneNotSupportedException {
		Template clone = new Template();
		cloneCommonFields(clone);
		clone.setIsPrivate(this.getIsPrivate());
		clone.setIsImportant(this.getIsImportant());
		clone.setIsF1Visa(this.getIsF1Visa());
		clone.setDepartmentCode(this.getDepartmentCode());
		clone.setDivisionCode(this.getDepartmentCode());
		clone.setProgramCode(this.getProgramCode());
		List<TemplateCourse> planCourses = this.getTemplateCourses();
		for (TemplateCourse planCourse : planCourses) {
			TemplateCourse planCourseClone = planCourse.clone();
			planCourseClone.setTemplate(clone);
			clone.getTemplateCourses().add(planCourseClone);
		}
		List<TermNote> termNotes = this.getTermNotes();
		for (TermNote termNote : termNotes) {
			TermNote termNoteClone = termNote.clone();
			termNoteClone.setTemplate(clone);
			clone.getTermNotes().add(termNoteClone);
		}
		return clone;
	}

	public List<TermNote> getTermNotes() {
		return termNotes;
	}

	public void setTermNotes(List<TermNote> termNotes) {
		this.termNotes = termNotes;
	}


	@Override
	public List<? extends AbstractPlanCourse<?>> getCourses() {
		return templateCourses;
	}

	@Override
	public List<? extends TermNote> getNotes() {
		return termNotes;
	}

}
