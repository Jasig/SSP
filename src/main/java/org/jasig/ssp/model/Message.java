package org.jasig.ssp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Email (Message) model
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
final public class Message
		extends AbstractAuditable
		implements Auditable {

	private static final long serialVersionUID = -7643811408668209143L;

	@Column(nullable = false, length = 250)
	private String subject;

	@Column(nullable = false, columnDefinition = "text")
	private String body;

	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private Person sender;

	@ManyToOne()
	@JoinColumn(name = "recipient_id", nullable = false)
	private Person recipient;

	@Column(length = 100, nullable = false)
	private String recipientEmailAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	private Date sentDate;

	/**
	 * Empty constructor
	 */
	public Message() {
		super();
	}

	/**
	 * Construct a new message with the required attributes.
	 * 
	 * @param subject
	 *            Message subject
	 * @param body
	 *            Message body
	 * @param sender
	 *            Message sender
	 * @param recipient
	 *            Message recipient
	 * @param recipientEmailAddress
	 *            Recipient e-mail address
	 */
	public Message(final String subject, final String body,
			final Person sender,
			final Person recipient, final String recipientEmailAddress) {
		super();
		setObjectStatus(ObjectStatus.ACTIVE);
		this.subject = subject;
		this.body = body;
		this.sender = sender;
		this.recipient = recipient;
		this.recipientEmailAddress = recipientEmailAddress;
	}

	/**
	 * Gets the subject
	 * 
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the email subject; maximum of 250 characters
	 * 
	 * @param subject
	 *            E-mail subject; maximum of 250 characters, can not be null
	 */
	public void setSubject(@NotNull final String subject) {
		this.subject = subject;
	}

	public Person getSender() {
		return sender;
	}

	public void setSender(@NotNull final Person sender) {
		this.sender = sender;
	}

	public Person getRecipient() {
		return recipient;
	}

	public void setRecipient(@NotNull final Person recipient) {
		this.recipient = recipient;
		recipientEmailAddress = recipient.getPrimaryEmailAddress();
	}

	public String getRecipientEmailAddress() {
		return recipientEmailAddress;
	}

	/**
	 * Sets the recipient email address; maximum of 100 characters
	 * 
	 * @param recipientEmailAddress
	 *            Recipient email address; maximum of 100 characters; can not be
	 *            null
	 */
	public void setRecipientEmailAddress(
			@NotNull final String recipientEmailAddress) {
		this.recipientEmailAddress = recipientEmailAddress;
	}

	public String getBody() {
		return body;
	}

	public void setBody(@NotNull final String body) {
		this.body = body;
	}

	public Date getSentDate() {
		return sentDate == null ? null : new Date(sentDate.getTime());
	}

	public void setSentDate(final Date sentDate) {
		this.sentDate = sentDate == null ? null : new Date(sentDate.getTime());
	}

	@Override
	protected int hashPrime() {
		return 181;
	};

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:46 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		// Message
		result *= hashField("subject", subject);
		result *= hashField("body", body);
		result *= hashField("sender", sender);
		result *= hashField("recipient", recipient);
		result *= hashField("recipientEmailAddress", recipientEmailAddress);
		result *= hashField("sentDate", sentDate);

		return result;
	}
}