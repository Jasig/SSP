package org.jasig.ssp.model;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.model.reference.ConfidentialityLevel;

/**
 * Task
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Task // NOPMD by jon.adams
		extends AbstractAuditable implements RestrictedPersonAssocAuditable {

	private static final long serialVersionUID = 1477217415946557983L;

	public static final String CUSTOM_GROUP_NAME = "Custom";

	public static final String ACTION_PLAN_TASK = "ACT";

	public static final String CUSTOM_ACTION_PLAN_TASK = "CUS";

	public static final String SSP_ACTION_PLAN_TASK = "SSP";

	@NotNull
	@Column(nullable = false, length = 100)
	private String name;

	@NotNull
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

	private boolean deletable;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id", updatable = false, nullable = false)
	private Person person;

	@ManyToOne
	@JoinColumn(name = "challenge_id", updatable = false, nullable = true)
	private Challenge challenge;

	@ManyToOne
	@JoinColumn(name = "challenge_referral_id", updatable = false, nullable = true)
	private ChallengeReferral challengeReferral;

	@Nullable()
	@ManyToOne()
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "confidentiality_level_id", nullable = false)
	private ConfidentialityLevel confidentialityLevel;

	/**
	 * Constructor that only calls the
	 * {@link AbstractAuditable#AbstractAuditable()} constructor.
	 */
	public Task() {
		super();
	}

	public Task(@NotNull final String name, @NotNull final String description,
			final Date dueDate,
			@NotNull final Person person, final Challenge challenge,
			final ChallengeReferral challengeReferral) {
		super();
		this.name = name;
		this.description = description;
		this.dueDate = new Date(dueDate.getTime());
		this.person = person;
		this.challenge = challenge;
		this.challengeReferral = challengeReferral;
	}

	public String getType() {
		if (person == null) {
			return null;
		}

		if (person.getId() == getCreatedBy().getId()) {
			if (challengeReferral == null) {
				return CUSTOM_ACTION_PLAN_TASK;
			} else {
				return ACTION_PLAN_TASK;
			}
		}

		return SSP_ACTION_PLAN_TASK;
	}

	public String getGroup() {
		if (challenge != null) { // NOPMD by jon.adams on 5/24/12 3:40 PM
			return challenge.getName();
		} else if (getType().equals(CUSTOM_ACTION_PLAN_TASK)) {
			return CUSTOM_GROUP_NAME;
		}

		return "UNKNOWN";
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(final Challenge challenge) {
		this.challenge = challenge;
	}

	public ChallengeReferral getChallengeReferral() {
		return challengeReferral;
	}

	public void setChallengeReferral(final ChallengeReferral challengeReferral) {
		this.challengeReferral = challengeReferral;
	}

	public String getName() {
		return name;
	}

	public void setName(@NotNull final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(@NotNull final String description) {
		this.description = description;
	}

	public Date getCompletedDate() {
		return completedDate == null ? null : new Date(completedDate.getTime());
	}

	public void setCompletedDate(final Date completedDate) {
		this.completedDate = completedDate == null ? null : new Date(
				completedDate.getTime());
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public Person getPerson() {
		return person;
	}

	@Override
	public void setPerson(@NotNull final Person person) {
		this.person = person;
	}

	public Date getReminderSentDate() {
		return reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public void setReminderSentDate(final Date reminderSentDate) {
		this.reminderSentDate = reminderSentDate == null ? null : new Date(
				reminderSentDate.getTime());
	}

	public Date getDueDate() {
		return dueDate == null ? null : new Date(dueDate.getTime());
	}

	public void setDueDate(final Date dueDate) {
		this.dueDate = dueDate == null ? null : new Date(dueDate.getTime());
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(final boolean deletable) {
		this.deletable = deletable;
	}

	/**
	 * @return the confidentialityLevel
	 */
	@Override
	public ConfidentialityLevel getConfidentialityLevel() {
		return confidentialityLevel;
	}

	/**
	 * @param confidentialityLevel
	 *            the confidentialityLevel to set
	 */
	public void setConfidentialityLevel(
			@NotNull final ConfidentialityLevel confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	@Override
	protected int hashPrime() {
		return 37;
	}

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:06 PM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// Task
		result *= hashField("name", name);
		result *= hashField("description", description);
		result *= hashField("dueDate", dueDate);
		result *= hashField("completedDate", completedDate);
		result *= hashField("reminderSentDate", reminderSentDate);
		result *= hashField("sessionId", sessionId);
		result *= deletable ? 3 : 5;
		result *= hashField("person", person);
		result *= hashField("challenge", challenge);
		result *= hashField("challengeReferral", challengeReferral);
		result *= hashField("confidentialityLevel", confidentialityLevel);

		return result;
	}
}