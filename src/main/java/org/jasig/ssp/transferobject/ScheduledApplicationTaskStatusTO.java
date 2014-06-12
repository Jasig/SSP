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
package org.jasig.ssp.transferobject;

import java.util.Date;

import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.jasig.ssp.model.ScheduledTaskStatus;

public class ScheduledApplicationTaskStatusTO extends
		AbstractAuditableTO<ScheduledApplicationTaskStatus> {
	


	private String taskName;

	private Date startDate;

	private Date completedDate;
	
 	private ScheduledTaskStatus status;
 	
 	public ScheduledApplicationTaskStatusTO(ScheduledApplicationTaskStatus model) {
		super();
		from(model);
	}

	/**
	 * Empty constructor.
	 */
	public ScheduledApplicationTaskStatusTO() {
		super();
	}

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
	
	@Override
	public void from(ScheduledApplicationTaskStatus model) {
		super.from(model);
		this.setCompletedDate(model.getCompletedDate());
		this.setStartDate(model.getStartDate());
		this.setStatus(model.getStatus());
		this.setTaskName(model.getTaskName());
	}
}
