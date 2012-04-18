package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface MessageTemplateService extends AuditableCrudService<MessageTemplate> {

	@Override
	public List<MessageTemplate> getAll(SortingAndPaging sAndP);

	@Override
	public MessageTemplate get(UUID id) throws ObjectNotFoundException;

	@Override
	public MessageTemplate create(MessageTemplate obj);

	@Override
	public MessageTemplate save(MessageTemplate obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
