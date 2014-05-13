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
package org.jasig.ssp.model; // NOPMD

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;


@Entity
@Table(name = "scheduled_application_task_status")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ScheduledApplicationTaskStatus extends AbstractAuditable implements Cloneable { // NOPMD

	/**
	 * 
	 */
	private static final long serialVersionUID = -6252790232803690329L;

	
	/**
	 * Task Name, required.
	 */
	@Column(nullable = false, length = 50)
	@Size(max = 50)
	private String taskName;
	
	/**
	 * Entity creation time stamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date startDate;
	
	/**
	 * Entity creation time stamp.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date completedDate;
	
	/**
	 * Status if null has not been run.
	 * 
	 * Maximum length of 10.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
 	private ScheduledTaskStatus status;

	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public ScheduledTaskStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduledTaskStatus status) {
		this.status = status;
	}

	protected int hashPrime() {
		return 3;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:25 PM
		int result = hashPrime();

		// Person
		result *= hashField("taskName", taskName);
		result *= hashField("status", status.toString());
		result *= hashField("startDate", startDate);
		result *= hashField("completedDate", completedDate);

		return result;
	}


}