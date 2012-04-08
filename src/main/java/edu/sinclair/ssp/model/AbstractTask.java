package edu.sinclair.ssp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class AbstractTask extends Auditable {

	@Column(nullable = false, length = 64000)
	@Size(max = 64000)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date dueDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date completedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date reminderSentDate;

	@Column(length = 32, updatable = false)
	private String sessionId;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCompletedDate() {
		return completedDate == null ? null : new Date(completedDate.getTime());
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate == null ? null : new Date(
				completedDate.getTime());
		;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getReminderSentDate() {
		return reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public void setReminderSentDate(Date reminderSentDate) {
		this.reminderSentDate = dueDate == null ? null : new Date(
				reminderSentDate.getTime());
		;
	}

	public Date getDueDate() {
		return dueDate == null ? null : new Date(dueDate.getTime());
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
		;
	}
}
