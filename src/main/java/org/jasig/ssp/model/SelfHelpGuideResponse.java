package org.jasig.ssp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.jasig.ssp.model.reference.SelfHelpGuide;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class SelfHelpGuideResponse extends Auditable implements Serializable {

	private static final long serialVersionUID = -1245736694871363293L;

	@Column(nullable = false)
	private boolean completed;

	@Column(nullable = false)
	private boolean cancelled;

	@Column(nullable = false)
	private boolean earlyAlertSent;

	/**
	 * Associated person. Changes to this Person are not persisted.
	 */
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "self_help_guide_id", nullable = false)
	private SelfHelpGuide selfHelpGuide;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "selfHelpGuideResponse")
	private Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses = new HashSet<SelfHelpGuideQuestionResponse>(
			0);

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(final boolean completed) {
		this.completed = completed;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isEarlyAlertSent() {
		return earlyAlertSent;
	}

	public void setEarlyAlertSent(final boolean earlyAlertSent) {
		this.earlyAlertSent = earlyAlertSent;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(final Person person) {
		this.person = person;
	}

	public SelfHelpGuide getSelfHelpGuide() {
		return selfHelpGuide;
	}

	public void setSelfHelpGuide(final SelfHelpGuide selfHelpGuide) {
		this.selfHelpGuide = selfHelpGuide;
	}

	public Set<SelfHelpGuideQuestionResponse> getSelfHelpGuideQuestionResponses() {
		return selfHelpGuideQuestionResponses;
	}

	public void setSelfHelpGuideQuestionResponses(
			final Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses) {
		this.selfHelpGuideQuestionResponses = selfHelpGuideQuestionResponses;
	}

	@Override
	protected int hashPrime() {
		return 31;
	};

	@Override
	final public int hashCode() { // NOPMD by jon.adams on 5/9/12 7:25 PM
		int result = hashPrime();

		// Auditable properties
		result *= getId() == null ? "id".hashCode() : getId().hashCode();
		result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
				.hashCode();

		// SelfHelpGuideResponse
		result *= completed ? 3 : 5;
		result *= cancelled ? 7 : 11;
		result *= earlyAlertSent ? 13 : 17;
		result *= (person == null) || (person.getId() == null) ? "person"
				.hashCode() : person.getId().hashCode();
		result *= selfHelpGuide == null ? "selfHelpGuide"
				.hashCode() : selfHelpGuide.hashCode();
		result *= selfHelpGuideQuestionResponses == null ? "selfHelpGuideQuestionResponses"
				.hashCode()
				: selfHelpGuideQuestionResponses.hashCode();

		return result;
	}

	public static SelfHelpGuideResponse createDefaultForSelfHelpGuideAndPerson(
			final SelfHelpGuide guide, final Person person) {

		final SelfHelpGuideResponse response = new SelfHelpGuideResponse();
		response.setCancelled(false);
		response.setCompleted(false);
		response.setCreatedDate(new Date());
		response.setEarlyAlertSent(false);
		response.setPerson(person);
		response.setSelfHelpGuide(guide);

		return response;
	}
}
