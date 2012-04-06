package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface EthnicityService extends AuditableCrudService<Ethnicity> {

	@Override
	public List<Ethnicity> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public Ethnicity get(UUID id) throws ObjectNotFoundException;

	@Override
	public Ethnicity create(Ethnicity obj);

	@Override
	public Ethnicity save(Ethnicity obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
