package org.studentsuccessplan.ssp.service;

import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.model.Message;
import org.studentsuccessplan.ssp.model.Person;

public interface MessageService {

	public void setBcc(String bcc);

	@Transactional(readOnly = false)
	public void createMessage(Person to, String subject, String body)
			throws Exception;

	public void createMessage(String to, String subject, String body)
			throws Exception;

	@Transactional(readOnly = false)
	public void sendQueuedMessages();

	public boolean sendMessage(Message message);

}