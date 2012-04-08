package edu.sinclair.ssp.model;

import java.io.Serializable;
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

import edu.sinclair.ssp.model.reference.SelfHelpGuide;

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

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isEarlyAlertSent() {
		return earlyAlertSent;
	}

	public void setEarlyAlertSent(boolean earlyAlertSent) {
		this.earlyAlertSent = earlyAlertSent;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public SelfHelpGuide getSelfHelpGuide() {
		return selfHelpGuide;
	}

	public void setSelfHelpGuide(SelfHelpGuide selfHelpGuide) {
		this.selfHelpGuide = selfHelpGuide;
	}

	public Set<SelfHelpGuideQuestionResponse> getSelfHelpGuideQuestionResponses() {
		return selfHelpGuideQuestionResponses;
	}

	public void setSelfHelpGuideQuestionResponses(
			Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses) {
		this.selfHelpGuideQuestionResponses = selfHelpGuideQuestionResponses;
	}

}
