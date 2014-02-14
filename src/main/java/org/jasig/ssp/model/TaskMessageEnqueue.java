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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Students may have zero or multiple Challenges in their way to success.
 * 
 * The PersonChallenge entity is an associative mapping between a student
 * (Person) and any Challenges they face.
 * 
 * Non-student users should never have any assigned Challenges.
 * 
 * @author jon.adams
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class TaskMessageEnqueue
		extends AbstractAuditable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7249184212920015137L;


	/**
	 * Associated task.
	 * 
	 * <p>
	 * This association should never be changed after creation.
	 */
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "task_id", updatable = false, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Task task;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date messageEnqueueDate;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date taskDueDate;
	
	@NotNull
	private Integer daysBefore;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "message_id", updatable = false, nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Message message;

	public TaskMessageEnqueue() {
		super();
	}

	public TaskMessageEnqueue(@NotNull final Task task, final Date messageEnqueueDate, @NotNull final Date taskDueDate, @NotNull final Integer daysBefore, Message message) {
		super();
		this.task = task;
		this.messageEnqueueDate = messageEnqueueDate;
		this.taskDueDate = taskDueDate;
		this.daysBefore = daysBefore;
		this.message = message;
	}
	
	public TaskMessageEnqueue(@NotNull final Task task, Message message, final Integer daysBefore) {
		super();
		this.task = task;
		this.daysBefore = daysBefore;
		this.message = message;
		this.messageEnqueueDate = new Date();
		this.taskDueDate = task.getDueDate();
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
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

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	protected int hashPrime() {
		return 137;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:15 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// TaskSentMessage
		result *= hashField("sentDate", messageEnqueueDate);
		result *= hashField("taskDueDate", taskDueDate);
		result *= hashField("daysBefore", daysBefore);
		result *= hashField("task", task);

		return result;
	}
}