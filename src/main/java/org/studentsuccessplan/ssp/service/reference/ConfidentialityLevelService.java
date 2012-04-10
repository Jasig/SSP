package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

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
