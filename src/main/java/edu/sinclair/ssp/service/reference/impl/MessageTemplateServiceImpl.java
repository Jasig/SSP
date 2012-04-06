package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.MessageTemplateDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.MessageTemplate;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.MessageTemplateService;

@Service
@Transactional
public class MessageTemplateServiceImpl implements MessageTemplateService {

	@Autowired
	private MessageTemplateDao dao;

	@Override
	public List<MessageTemplate> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public MessageTemplate get(UUID id) throws ObjectNotFoundException {
		MessageTemplate obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "MessageTemplate");
		}

		return obj;
	}

	@Override
	public MessageTemplate create(MessageTemplate obj) {
		return dao.save(obj);
	}

	@Override
	public MessageTemplate save(MessageTemplate obj) throws ObjectNotFoundException {
		MessageTemplate current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		MessageTemplate current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(MessageTemplateDao dao) {
		this.dao = dao;
	}
}
