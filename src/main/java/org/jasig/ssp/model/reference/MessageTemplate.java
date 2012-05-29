package org.jasig.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.Auditable;

/*
 * Allows e-mails and other messaging to be customized using the Velocity Framework
 */
@Entity
public class MessageTemplate
		extends AbstractReference
		implements Auditable {

	private static final long serialVersionUID = 2756578010766636880L;

	// Message Templates
	public static final UUID EMPTY_TEMPLATE_EMAIL_ID = UUID
			.fromString("7c945020-86b0-11e1-849a-0026b9e7ff4c");

	public static final UUID JOURNAL_NOTE_FOR_EARLY_ALERT_RESPONSE_ID = UUID
			.fromString("B528C1AC-6104-435B-AE62-08EB4F7EE2F9");

	public static final UUID EARLYALERT_MESSAGETOSTUDENT_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-08eb4f8ef40e");

	public static final UUID EARLYALERT_CONFIRMATIONTOADVISOR_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-08eb4f7ee3fc");

	public static final UUID EARLYALERT_CONFIRMATIONTOFACULTY_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-09eb5f8ef55f");

	public static final UUID EARLYALERT_RESPONSETOFACULTYFROMCOACH_ID = UUID
			.fromString("b528c1ac-6104-435b-ae62-09fb5f9ef680");

	// :TODO match MessageTemplate IDs to actual database values
	public static final UUID CUSTOM_ACTION_PLAN_TASK_ID = UUID
			.fromString("31CF8D8D-2BC9-44E0-AAD1-D8BA43530BB0");
	public static final UUID ACTION_PLAN_STEP_ID = UUID
			.fromString("AEC07252-1FF0-479D-A2EF-C0E017E1C05D");
	public static final UUID CONTACT_COACH_ID = UUID
			.fromString("0B7E484D-44E4-4F0D-8DB5-3518D015B495");
	public static final UUID ACTION_PLAN_EMAIL_ID = UUID
			.fromString("5D183F35-023D-40EA-B8D9-66FBE190FFFB");
	public static final UUID TASK_AUTO_CREATED_EMAIL_ID = UUID
			.fromString("919F6FF5-8F22-4684-8729-D615206A2644");
	public static final UUID NEW_STUDENT_INTAKE_TASK_EMAIL_ID = UUID
			.fromString("9D3CE5B1-E27D-40C8-8F45-ABCB1BCCF3B0");

	@Column(name = "subject", nullable = false, length = 250)
	private String subject;

	@Column(name = "body", nullable = false, columnDefinition = "text")
	private String body;

	/**
	 * Empty constructor
	 */
	public MessageTemplate() {
		super();
	}

	public MessageTemplate(final UUID id) {
		super(id);
	}

	public MessageTemplate(final UUID id, final String name) {
		super(id, name);
	}

	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject
	 *            Maximum length of 250 characters.
	 */
	public void setSubject(@NotNull final String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(@NotNull final String body) {
		this.body = body;
	}

	/**
	 * Get a cacheable id for the subject template
	 * 
	 * @return A cacheable id for the subject template
	 */
	public String subjectTemplateId() {
		return getId().toString() + "-s-" + getModifiedDate().getTime();
	}

	/**
	 * Get a cacheable id for the body template
	 * 
	 * @return A cacheable id for the body template
	 */
	public String bodyTemplateId() {
		return getId().toString() + "-b-" + getModifiedDate().getTime();
	}

	@Override
	protected int hashPrime() {
		return 113;
	}

	@Override
	public int hashCode() { // NOPMD by jon.adams on 5/3/12 11:48 AM
		int result = hashPrime();

		// AbstractAuditable properties
		result *= super.hashCode();

		result *= hashField("subject", subject);
		result *= hashField("body", body);

		return result;
	}
}