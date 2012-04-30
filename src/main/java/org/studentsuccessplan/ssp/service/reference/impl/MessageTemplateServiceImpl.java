package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.MessageTemplateDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.MessageTemplateService;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class MessageTemplateServiceImpl implements MessageTemplateService {

	@Autowired
	private MessageTemplateDao dao;

	@Override
	public PagingWrapper<MessageTemplate> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
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
	public MessageTemplate save(MessageTemplate obj)
			throws ObjectNotFoundException {
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
