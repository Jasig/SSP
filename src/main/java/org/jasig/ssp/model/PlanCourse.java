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

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="map_plan_course")
public class PlanCourse extends AbstractPlanCourse<Plan> {

	//Hibernate calculated attributes only support native sql and not hql :( 
	//tokens in all caps refer to map_plan_course COLUMNS not member attributes.
	private static final String IS_TRANSCRIPT_FORMULA = " ( select count(*) from external_student_transcript_course estc " +
														" join  person p on p.school_id = estc.school_id " +
														" where p.id = PERSON_ID and estc.formatted_course = FORMATTED_COURSE ) ";
	//We test for if the transcript course and the terms are the same. then invert the logic combined with isTranscript to determine
	//if course is a duplicate.
	private static final String DUPLICATES_TRANSCRIPT_FORMULA = " ( select count(*) from external_student_transcript_course estc " +
			" join  person p on p.school_id = estc.school_id " +
			" where p.id = PERSON_ID and estc.formatted_course = FORMATTED_COURSE AND estc.term_code = TERM_CODE) ";
	
	private static final long serialVersionUID = -6316130725863888876L;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "plan_id", updatable = false, nullable = false)	
	private Plan plan;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;
	
	
	@Formula(IS_TRANSCRIPT_FORMULA)
	private Integer isTranscript;	
	
	@Formula(DUPLICATES_TRANSCRIPT_FORMULA)
	private Integer duplicateOfTranscript = 1;
	

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
		clone.setPerson(this.getPerson());
		clone.setIsTranscript(this.getIsTranscript());
		cloneCommonFields(clone);
		return clone;
	}



	@Override
	public Plan getParent() {
		return plan;
	}

	public Boolean getIsTranscript() {
		return isTranscript == null ? false : isTranscript > 0;
	}

	public void setIsTranscript(Boolean isTranscript) {
		this.isTranscript = (isTranscript == null || isTranscript == false) ? 0 : 1;
	}
	
	public Boolean getDuplicateOfTranscript() {
		return duplicateOfTranscript == 0 && isTranscript > 0 ? true : false;
	}

	public void setDuplicateOfTranscript(Boolean duplicateOfTranscript) {
		this.duplicateOfTranscript = duplicateOfTranscript == false ? 1 : 0;
	}
}
