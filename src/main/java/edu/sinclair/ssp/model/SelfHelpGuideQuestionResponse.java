package edu.sinclair.ssp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import edu.sinclair.ssp.model.reference.SelfHelpGuideQuestion;

@Entity
@Table(name = "SelfHelpGuideQuestionResponse", schema = "dbo")
public class SelfHelpGuideQuestionResponse implements Serializable {
	private static final long serialVersionUID = -6385278568384602029L;

	@Id
	@GenericGenerator(name = "generator", strategy = "guid", parameters = {})
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "selfHelpGuideResponseId", nullable = false)
	private SelfHelpGuideResponse selfHelpGuideResponse;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "selfHelpGuideQuestionId", nullable = false)
	private SelfHelpGuideQuestion selfHelpGuideQuestion;

	@Column(name = "response", nullable = false)
	private Boolean response;

	@Column(name = "earlyAlertSent", nullable = false)
	private Boolean earlyAlertSent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdDate", nullable = false, length = 23)
	private Date createdDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SelfHelpGuideResponse getSelfHelpGuideResponse() {
		return selfHelpGuideResponse;
	}

	public void setSelfHelpGuideResponse(
			SelfHelpGuideResponse selfHelpGuideResponse) {
		this.selfHelpGuideResponse = selfHelpGuideResponse;
	}

	public SelfHelpGuideQuestion getSelfHelpGuideQuestion() {
		return selfHelpGuideQuestion;
	}

	public void setSelfHelpGuideQuestion(
			SelfHelpGuideQuestion selfHelpGuideQuestion) {
		this.selfHelpGuideQuestion = selfHelpGuideQuestion;
	}

	public Boolean getResponse() {
		return response;
	}

	public void setResponse(Boolean response) {
		this.response = response;
	}

	public Boolean getEarlyAlertSent() {
		return earlyAlertSent;
	}

	public void setEarlyAlertSent(Boolean earlyAlertSent) {
		this.earlyAlertSent = earlyAlertSent;
	}

	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate == null ? null : new Date(
				createdDate.getTime());
		;
	}

}
