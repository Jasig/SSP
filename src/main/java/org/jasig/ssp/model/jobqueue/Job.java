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
package org.jasig.ssp.model.jobqueue;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.ssp.model.AbstractAuditable;
import org.jasig.ssp.model.Person;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "job_queue")
public class Job extends AbstractAuditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "owner_Id", updatable = false, nullable = false)
	private Person owner;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "run_as_id", updatable = false, nullable = false)
	private Person runAs;
	
	@NotNull
	@Size(max = 150)
	private String executionComponentName;

	private String executionSpec;

	private String executionState;
	
	
	private String workflowStatusDesc;

	private String scheduledByProcess;

	private Integer retryCount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private WorkflowStatus workflowStatus;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date schedulingStartedDate;		
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date executionStartedDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date workflowStoppedDate;	

	@Override
	protected int hashPrime() {
		return 13;
	}


	public String getExecutionComponentName() {
		return executionComponentName;
	}

	public void setExecutionComponentName(String executionComponentName) {
		this.executionComponentName = executionComponentName;
	}

	public String getExecutionSpec() {
		return executionSpec;
	}

	public void setExecutionSpec(String executionSpec) {
		this.executionSpec = executionSpec;
	}

	public String getExecutionState() {
		return executionState;
	}

	public void setExecutionState(String executionState) {
		this.executionState = executionState;
	}

	public String getWorkflowStatusDesc() {
		return workflowStatusDesc;
	}

	public void setWorkflowStatusDesc(String workflowStatusDesc) {
		this.workflowStatusDesc = workflowStatusDesc;
	}

	public String getScheduledByProcess() {
		return scheduledByProcess;
	}

	public void setScheduledByProcess(String scheduledByProcess) {
		this.scheduledByProcess = scheduledByProcess;
	}

	public WorkflowStatus getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(WorkflowStatus workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public Date getSchedulingStartedDate() {
		return schedulingStartedDate;
	}

	public void setSchedulingStartedDate(Date schedulingStartedDate) {
		this.schedulingStartedDate = schedulingStartedDate;
	}

	public Date getExecutionStartedDate() {
		return executionStartedDate;
	}

	public void setExecutionStartedDate(Date executionStartedDate) {
		this.executionStartedDate = executionStartedDate;
	}

	public Date getWorkflowStoppedDate() {
		return workflowStoppedDate;
	}

	public void setWorkflowStoppedDate(Date workflowStoppedDate) {
		this.workflowStoppedDate = workflowStoppedDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((executionComponentName == null) ? 0
						: executionComponentName.hashCode());
		result = prime * result
				+ ((executionSpec == null) ? 0 : executionSpec.hashCode());
		result = prime
				* result
				+ ((executionStartedDate == null) ? 0 : executionStartedDate
						.hashCode());
		result = prime * result
				+ ((executionState == null) ? 0 : executionState.hashCode());
		result = prime
				* result
				+ ((scheduledByProcess == null) ? 0 : scheduledByProcess
						.hashCode());
		result = prime
				* result
				+ ((schedulingStartedDate == null) ? 0 : schedulingStartedDate
						.hashCode());
		result = prime * result
				+ ((workflowStatus == null) ? 0 : workflowStatus.hashCode());
		result = prime
				* result
				+ ((workflowStatusDesc == null) ? 0 : workflowStatusDesc
						.hashCode());
		result = prime
				* result
				+ ((workflowStoppedDate == null) ? 0 : workflowStoppedDate
						.hashCode());
		return result;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Person getRunAs() {
		return runAs;
	}

	public void setRunAs(Person runAs) {
		this.runAs = runAs;
	}


	public Integer getRetryCount() {
		return retryCount;
	}


	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}


}
