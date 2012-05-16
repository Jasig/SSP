package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface MessageTemplateService extends
		AuditableCrudService<MessageTemplate> {

	@Override
	PagingWrapper<MessageTemplate> getAll(SortingAndPaging sAndP);

	@Override
	MessageTemplate get(UUID id) throws ObjectNotFoundException;

	@Override
	MessageTemplate create(MessageTemplate obj) throws ObjectNotFoundException;

	@Override
	MessageTemplate save(MessageTemplate obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}