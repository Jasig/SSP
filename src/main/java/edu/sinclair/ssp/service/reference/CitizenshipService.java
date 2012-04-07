package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface CitizenshipService extends AuditableCrudService<Citizenship> {

	@Override
	public List<Citizenship> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public Citizenship get(UUID id) throws ObjectNotFoundException;

	@Override
	public Citizenship create(Citizenship obj);

	@Override
	public Citizenship save(Citizenship obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
