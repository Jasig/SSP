package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
public class MessageTemplate extends AbstractReference {

	// :TODO match these to actual db values
	// Message Templates
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

	@Column(name = "body", nullable = false, columnDefinition="text")
	private String body;

	public MessageTemplate() {}

	public MessageTemplate(UUID id) {
		super(id);
	}

	public MessageTemplate(UUID id, String name) {
		super(id, name);
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}
