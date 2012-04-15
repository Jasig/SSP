package org.studentsuccessplan.ssp.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.model.Message;
import org.studentsuccessplan.ssp.model.Person;

public interface MessageService {

	void setBcc(String bcc);

	@Transactional(readOnly = false)
	void createMessage(Person to, String subject, String body)
			throws Exception;

	void createMessage(String to, String subject, String body)
			throws Exception;

	void createMessageFromTemplate(String emailAddress, UUID messageTemplateId,
			Map<String, Object> templateParameters) throws Exception;

	@Transactional(readOnly = false)
	void sendQueuedMessages();

	boolean sendMessage(Message message);

}