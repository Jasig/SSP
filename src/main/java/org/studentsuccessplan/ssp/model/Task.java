package org.studentsuccessplan.ssp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task extends Auditable implements Serializable {

	public static final String CUSTOM_GROUP_NAME = "Custom";

	private static final long serialVersionUID = 1477217415946557983L;

	public static final String ACTION_PLAN_TASK = "ACT";
	public static final String CUSTOM_ACTION_PLAN_TASK = "CUS";
	public static final String SSP_ACTION_PLAN_TASK = "SSP";

	@Column(nullable = false, length = 100)
	private String name;

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
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "challenge_id", updatable = false, nullable = true)
	private Challenge challenge;

	@ManyToOne
	@JoinColumn(name = "challenge_referral_id", updatable = false, nullable = true)
	private ChallengeReferral challengeReferral;

	/**
	 * Constructor that only calls {@link Auditable#Auditable()}.
	 */
	public Task() {
		super();
	}

	public Task(String description, Date dueDate, Person person,
			Challenge challenge, ChallengeReferral challengeReferral) {
		this.description = description;
		this.dueDate = new Date(dueDate.getTime());
		this.person = person;
		this.challenge = challenge;
		this.challengeReferral = challengeReferral;
	}

	public String getType() {
		if (person.getId() != getCreatedBy().getId()) {
			return SSP_ACTION_PLAN_TASK;
		} else {
			if (challengeReferral == null) {
				return CUSTOM_ACTION_PLAN_TASK;
			} else {
				return ACTION_PLAN_TASK;
			}
		}
	}

	public String getGroup() {
		String group;

		final String type = getType();
		if (challenge != null) {
			group = challenge.getName();
		} else if (type.equals(CUSTOM_ACTION_PLAN_TASK)) {
			group = CUSTOM_GROUP_NAME;
		} else {
			group = "UNKNOWN";
		}

		return group;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}

	public ChallengeReferral getChallengeReferral() {
		return challengeReferral;
	}

	public void setChallengeReferral(ChallengeReferral challengeReferral) {
		this.challengeReferral = challengeReferral;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
		this.reminderSentDate = reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public Date getDueDate() {
		return dueDate == null ? null : new Date(dueDate.getTime());
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
	}

}
