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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.TaskMessageEnqueue;

import com.google.common.collect.Lists;

public class TaskMessageEnqueueTO
		extends AbstractAuditableTO<TaskMessageEnqueue>
		implements TransferObject<TaskMessageEnqueue> {



	/**
	 * Associated task.
	 * 
	 * <p>
	 * This association should never be changed after creation.
	 */
	@NotNull
	private UUID taskId;
	
	@NotNull
	private Date taskDueDate;
	
	@NotNull
	private Integer daysBefore;
	
	private Date messageEnqueueDate;
	
	private UUID messageId;


	public TaskMessageEnqueueTO() {
		super();
	}

	public TaskMessageEnqueueTO(final TaskMessageEnqueue model) {
		super();
		from(model);
	}

	@Override
	public final void from(final TaskMessageEnqueue model) {
		super.from(model);
		setTaskId(model.getTask().getId());
		setMessageEnqueueDate(model.getMessageEnqueueDate());
		setTaskDueDate(model.getTaskDueDate());
		setDaysBefore(model.getDaysBefore());
	}

	public static List<TaskMessageEnqueueTO> toTOList(
			final Collection<TaskMessageEnqueue> models) {
		final List<TaskMessageEnqueueTO> tos = Lists.newArrayList();
		for (final TaskMessageEnqueue model : models) {
			tos.add(new TaskMessageEnqueueTO(model)); // NOPMD
		}

		return tos;
	}

	public UUID getTaskId() {
		return taskId;
	}

	public void setTaskId(UUID taskId) {
		this.taskId = taskId;
	}

	public Date getMessageEnqueueDate() {
		return messageEnqueueDate;
	}

	public void setMessageEnqueueDate(Date sentDate) {
		this.messageEnqueueDate = sentDate;
	}

	public Date getTaskDueDate() {
		return taskDueDate;
	}

	public void setTaskDueDate(Date taskDueDate) {
		this.taskDueDate = taskDueDate;
	}

	public Integer getDaysBefore() {
		return daysBefore;
	}

	public void setDaysBefore(Integer daysBefore) {
		this.daysBefore = daysBefore;
	}

	public UUID getMessageId() {
		return messageId;
	}

	public void setMessageId(UUID messageId) {
		this.messageId = messageId;
	}

	
}