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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyDeserializer;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlySerializer;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelTO;

/**
 * Task transfer object
 */
public class TaskTO
		extends AbstractAuditableTO<Task>
		implements TransferObject<Task>, Serializable, NamedTO {

	private static final long serialVersionUID = 5796302591576434925L;

	private String type;

	private String name, description, link;

	private boolean deletable;

	@JsonSerialize(using = DateOnlySerializer.class)
	@JsonDeserialize(using = DateOnlyDeserializer.class)
	private Date dueDate;

	private Date completedDate;

	private Date reminderSentDate;

	private UUID personId;

	private UUID challengeId;

	private UUID challengeReferralId;
	
	private UUID confidentialityLevelId;


	private ConfidentialityLevelTO confidentialityLevel;

	public TaskTO() {
		super();
	}

	public TaskTO(final Task task) {
		super();
		from(task);
	}

	@Override
	public final void from(final Task task) {
		super.from(task);

		type = task.getType();
		deletable = task.isDeletable();
		dueDate = task.getDueDate();
		completedDate = task.getCompletedDate();
		reminderSentDate = task.getReminderSentDate();
		link = task.getLink();
		name = task.getName();
		description = task.getDescription() == null ? null : task
				.getDescription().replaceAll("\\<.*?>", "");
		if (task.getChallenge() != null) {
			challengeId = task.getChallenge().getId();
		}

		if (task.getChallengeReferral() != null) {
			challengeReferralId = task.getChallengeReferral().getId();
		}
		
		confidentialityLevel = new ConfidentialityLevelTO(task.getConfidentialityLevel());
	}

	/**
	 * Converts a list of models to equivalent transfer objects.
	 * 
	 * @param models
	 *            model tasks to convert to equivalent transfer objects
	 * @return List of equivalent transfer objects, or empty List if null or
	 *         empty.
	 */
	public static List<TaskTO> toTOList(final Collection<Task> models) {
		final List<TaskTO> taskTOs = new ArrayList<TaskTO>();
		if ((models != null) && !models.isEmpty()) {
			for (final Task task : models) {
				taskTOs.add(new TaskTO(task)); // NOPMD
			}
		}

		return taskTOs;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
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

	/**
	 * Has this Task been completed?
	 * 
	 * <p>
	 * Generated field that will always be true if completedDate is non-null.
	 * 
	 * @return true if Task has been completed, determined by a non-null
	 *         completedDate
	 */
	public boolean isCompleted() {
		return completedDate != null;
	}

	/**
	 * Side affect: Sets or nulls the completedDate if necessary.
	 * 
	 * <p>
	 * It is advised to use {@link #setCompletedDate(Date)} explicitly instead
	 * of changing the value via this setter.
	 * 
	 * @param completed
	 *            Is this task completed or not?
	 */
	public void setCompleted(final boolean completed) {
		if (completed && completedDate == null) {
			// Completed date not already set, so set it to now.
			completedDate = new Date();
		} else if (!completed) {
			// Ensure completed date is not set.
			completedDate = null; // NOPMD
		}
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(final boolean deletable) {
		this.deletable = deletable;
	}

	public UUID getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(final UUID challengeId) {
		this.challengeId = challengeId;
	}

	public UUID getChallengeReferralId() {
		return challengeReferralId;
	}

	public void setChallengeReferralId(final UUID challengeReferralId) {
		this.challengeReferralId = challengeReferralId;
	}

	public Date getCompletedDate() {
		return completedDate == null ? null : new Date(completedDate.getTime());
	}

	/**
	 * Completed date. Can not be un-set. In other words, null is ignored if not
	 * null already.
	 * 
	 * <p>
	 * Side affect: the result of {@link #isCompleted()} is generated based on
	 * this value.
	 * 
	 * @param completedDate
	 *            Completed date; silently ignored if null but current value is
	 *            not null. See discussion in issue <a
	 *            href="https://issues.jasig.org/browse/SSP-127">SSP-127</a>.
	 */
	public void setCompletedDate(final Date completedDate) {
		if (this.completedDate == null && completedDate != null) {
			this.completedDate = new Date(completedDate.getTime());
		}
	}

	public Date getReminderSentDate() {
		return reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public void setReminderSentDate(final Date reminderSentDate) {
		this.reminderSentDate = reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public ConfidentialityLevelTO getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			final ConfidentialityLevelTO confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public UUID getConfidentialityLevelId() {
		return confidentialityLevelId;
	}

	public void setConfidentialityLevelId(UUID confidentialityLevelId) {
		this.confidentialityLevelId = confidentialityLevelId;
	}
}