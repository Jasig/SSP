package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.ConfidentialityLevel;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface ConfidentialityLevelService extends AuditableCrudService<ConfidentialityLevel> {

	@Override
	public List<ConfidentialityLevel> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public ConfidentialityLevel get(UUID id) throws ObjectNotFoundException;

	@Override
	public ConfidentialityLevel create(ConfidentialityLevel obj);

	@Override
	public ConfidentialityLevel save(ConfidentialityLevel obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
