package edu.sinclair.ssp.model;

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

import org.hibernate.annotations.ForeignKey;

import edu.sinclair.ssp.model.Person;

@Entity
@Table(name = "Message", schema = "dbo")
public class Message {

	@Id
	@GeneratedValue(generator="gen_id")
	@org.hibernate.annotations.GenericGenerator(name="gen_id", strategy="guid")
	@Column(name = "id", unique = true, nullable = false, length = 36)
	private String id;

	@Column(name = "subject", nullable = false, length = 250)
	private String subject;

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_Message_Person_0")
	@JoinColumn(name = "senderPersonId", nullable = false)
	private Person sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_Message_Person_1")
	@JoinColumn(name = "recipientPersonId")
	private Person recipient;

	@Column(name = "recipientEmailAddress", length = 100)
	private String recipientEmailAddress;

	@Column(name = "body", nullable = false, columnDefinition = "text")
	private String body;

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "FK_Message_Person_2")
	@JoinColumn(name = "createdByPersonId", nullable = false)
	private Person createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdDate", nullable = false, updatable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sentDate")
	private Date sentDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Person getSender() {
		return sender;
	}

	public void setSender(Person sender) {
		this.sender = sender;
	}

	public Person getRecipient() {
		return recipient;
	}

	public void setRecipient(Person recipient) {
		this.recipient = recipient;
	}

	public String getRecipientEmailAddress() {
		return recipientEmailAddress;
	}

	public void setRecipientEmailAddress(String recipientEmailAddress) {
		this.recipientEmailAddress = recipientEmailAddress;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Person getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

}
