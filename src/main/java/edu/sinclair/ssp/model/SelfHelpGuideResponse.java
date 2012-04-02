package edu.sinclair.ssp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.SelfHelpGuide;

@Entity
@Table(name = "SelfHelpGuideResponse", schema = "dbo")
public class SelfHelpGuideResponse {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private UUID id;

	@Column(name = "completed", nullable = false)
	private boolean completed;

	@Column(name = "cancelled", nullable = false)
	private boolean cancelled;

	@Column(name = "earlyAlertSent", nullable = false)
	private boolean earlyAlertSent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdDate", nullable = false, length = 23)
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId", nullable = false)
	private Person person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "selfHelpGuideId", nullable = false)
	private SelfHelpGuide selfHelpGuide;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "selfHelpGuideResponse")
	private Set<SelfHelpGuideQuestionResponse> selfHelpGuideQuestionResponses = new HashSet<SelfHelpGuideQuestionResponse>(0);

	public SelfHelpGuideResponse() {}

	public SelfHelpGuideResponse(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
