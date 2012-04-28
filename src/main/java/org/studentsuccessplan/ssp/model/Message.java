package org.studentsuccessplan.ssp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Message extends Auditable {

	@Column(nullable = false, length = 250)
	private String subject;

	@Column(nullable = false, columnDefinition = "text")
	private String body;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private Person sender;

	@ManyToOne
	@JoinColumn(name = "recipient_id")
	private Person recipient;

	@Column(length = 100)
	private String recipientEmailAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date sentDate;

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

	public Date getSentDate() {
		return sentDate == null ? null : new Date(sentDate.getTime());
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate == null ? null : new Date(sentDate.getTime());
	}

	@Override
	protected int hashPrime() {
		return 2;
	};
}
