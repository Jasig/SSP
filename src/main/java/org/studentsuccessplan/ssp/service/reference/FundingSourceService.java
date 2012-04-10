package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface FundingSourceService extends AuditableCrudService<FundingSource> {

	@Override
	public List<FundingSource> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public FundingSource get(UUID id) throws ObjectNotFoundException;

	@Override
	public FundingSource create(FundingSource obj);

	@Override
	public FundingSource save(FundingSource obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
