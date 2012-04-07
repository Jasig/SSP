package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface MaritalStatusService extends AuditableCrudService<MaritalStatus> {

	@Override
	public List<MaritalStatus> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public MaritalStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	public MaritalStatus create(MaritalStatus obj);

	@Override
	public MaritalStatus save(MaritalStatus obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
