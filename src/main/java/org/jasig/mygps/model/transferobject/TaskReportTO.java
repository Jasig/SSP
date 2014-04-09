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
package org.jasig.mygps.model.transferobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.AuditPerson;
import org.jasig.ssp.model.Task;

public class TaskReportTO implements Comparable<TaskReportTO>, Serializable {

	private static final long serialVersionUID = 914070219523802658L;

	private String type;
	private String challengeName;
	private String challengeReferralName;
	private String description;
	private AuditPerson createdBy;
	private Date dueDate;

	/**
	 * Empty constructor
	 */
	public TaskReportTO() {
	}

	public TaskReportTO(final Task task) {
		challengeName = task.getChallenge() == null ? null : task.getChallenge().getName();
		challengeReferralName = task.getChallengeReferral() == null ? null : task.getChallengeReferral().getName();
		description = task.getChallengeReferral() == null ? null : task.getChallengeReferral().getPublicDescription();
		// SSP-822... fallback to descr if no publicDescr
		description = (task.getChallengeReferral() != null && (description == null || description.trim().isEmpty())) ? task.getChallengeReferral().getDescription() : description;

		challengeName = (StringUtils.isBlank(challengeName) && Task.CUSTOM_ACTION_PLAN_TASK.equals(task.getType())) ? "Custom Action Plan Task" : challengeName;
		challengeReferralName = StringUtils.isBlank(challengeReferralName) ? task.getName() : challengeReferralName;
		description = StringUtils.isBlank(description) ? task.getDescription() : description;
		
		dueDate = task.getDueDate();
		createdBy = task.getCreatedBy();
		type = task.getType();
	}

	public static List<TaskReportTO> tasksToTaskReportTOs(final List<Task> tasks) {
		final List<TaskReportTO> taskReportTOs = new ArrayList<TaskReportTO>();
		for (Task task : tasks) {
			taskReportTOs.add(new TaskReportTO(task));
		}
		return taskReportTOs;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getChallengeName() {
		return challengeName;
	}

	public void setChallengeName(final String challengeName) {
		this.challengeName = challengeName;
	}

	public String getChallengeReferralName() {
		return challengeReferralName;
	}

	public void setChallengeReferralName(final String challengeReferralName) {
		this.challengeReferralName = challengeReferralName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate == null ? null : new Date(dueDate.getTime());
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
	}

	public AuditPerson getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final AuditPerson createdBy) {
		this.createdBy = createdBy;
	}

	protected int hashPrime() {
		return 61;
	}

	@Override
	public int hashCode() {
		int result = hashPrime();

		result *= StringUtils.isEmpty(type) ? "type".hashCode() : type
				.hashCode();
		result *= StringUtils.isEmpty(challengeName) ? "challengeName"
				.hashCode() : challengeName.hashCode();
		result *= StringUtils.isEmpty(challengeReferralName) ? "challengeReferralName"
				.hashCode()
				: challengeReferralName.hashCode();
		result *= StringUtils.isEmpty(description) ? "description".hashCode()
				: description.hashCode();
		result *= createdBy == null ? "createdBy".hashCode() : createdBy
				.hashCode();
		result *= dueDate == null ? "dueDate".hashCode() : dueDate.hashCode();

		return result;
	}

	@Override
	final public boolean equals(final Object obj) {
		if (this == obj) {
			// exact references that point to the same place in memory are
			// always equal
			return true;
		}

		if (!(TaskReportTO.class.isInstance(obj))
				|| !(getClass().equals(obj.getClass()))) {
			return false;
		}

		final TaskReportTO other = (TaskReportTO) obj;

		return hasSameDomainSignature(other);
	}

	/**
	 * This method MUST be implemented for each class and must compare to all
	 * properties that define an equal instance for business rule comparison
	 * purposes.
	 * 
	 * @param other
	 *            The object to compare
	 * @return True if properties for business equality are all equal.
	 */
	private boolean hasSameDomainSignature(final Object other) {
		return hashCode() == other.hashCode();
	}

	@Override
	public String toString() {
		return challengeName == null ? super.toString() : challengeName;
	}

	@Override
	public int compareTo(final TaskReportTO taskReportTO) {
		return challengeName.compareTo(taskReportTO.getChallengeName());
	}
}
