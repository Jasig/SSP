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
package org.jasig.ssp.transferobject.reference;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.transferobject.AbstractPlanOutputTO;
import org.jasig.ssp.transferobject.AbstractPlanTO;

public class AbstractMessageTemplateMapPrintParamsTO<TOO extends AbstractPlanOutputTO<T,TO>, T extends AbstractPlan,TO extends AbstractPlanTO<T>> {

	BigDecimal totalPlanCreditHours;
	List<TermCourses<T, TO>> termCourses;
	String institutionName;
	UUID messageTemplateId;
	Person owner;
	Person student;
	
	AbstractPlanOutputTO<T,TO> outputPlan;
	
	public AbstractPlanOutputTO<T, TO> getOutputPlan() {
		return outputPlan;
	}
	public void setOutputPlan(AbstractPlanOutputTO<T, TO> outputPlan) {
		this.outputPlan = outputPlan;
	}
	
	public BigDecimal getTotalPlanCreditHours() {
		return totalPlanCreditHours;
	}
	public void setTotalPlanCreditHours(BigDecimal totalPlanCreditHours) {
		this.totalPlanCreditHours = totalPlanCreditHours;
	}
	public List<TermCourses<T, TO>> getTermCourses() {
		return termCourses;
	}
	public void setTermCourses(List<TermCourses<T, TO>> termCourses) {
		this.termCourses = termCourses;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public UUID getMessageTemplateId() {
		return messageTemplateId;
	}
	public void setMessageTemplateId(UUID messageTemplateId) {
		this.messageTemplateId = messageTemplateId;
	}
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	public Person getStudent() {
		return student;
	}
	public void setStudent(Person student) {
		this.student = student;
	}
	
}
