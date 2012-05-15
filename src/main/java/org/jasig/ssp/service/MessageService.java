package org.jasig.ssp.service;

import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.Person;
import org.springframework.transaction.annotation.Transactional;

public interface MessageService {

	@Transactional(readOnly = false)
	void createMessage(Person to, UUID messageTemplateId,
			final Map<String, Object> templateParameters)
			throws Exception;

	void createMessage(String to, UUID messageTemplateId,
			Map<String, Object> templateParameters)
			throws Exception;

	@Transactional(readOnly = false)
	void sendQueuedMessages();

	boolean sendMessage(Message message);

}