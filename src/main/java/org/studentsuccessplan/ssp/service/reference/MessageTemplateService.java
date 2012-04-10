package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.MessageTemplate;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface MessageTemplateService extends AuditableCrudService<MessageTemplate> {

	@Override
	public List<MessageTemplate> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public MessageTemplate get(UUID id) throws ObjectNotFoundException;

	@Override
	public MessageTemplate create(MessageTemplate obj);

	@Override
	public MessageTemplate save(MessageTemplate obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
