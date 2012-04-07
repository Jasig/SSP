package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface EducationLevelService extends AuditableCrudService<EducationLevel> {

	@Override
	public List<EducationLevel> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public EducationLevel get(UUID id) throws ObjectNotFoundException;

	@Override
	public EducationLevel create(EducationLevel obj);

	@Override
	public EducationLevel save(EducationLevel obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
