package edu.sinclair.ssp.service;

import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.Message;
import edu.sinclair.ssp.model.Person;

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