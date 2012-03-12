package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;

public interface FundingSourceService extends AuditableCrudService<FundingSource>{

	public List<FundingSource> getAll(ObjectStatus status);

	public FundingSource get(UUID id) throws ObjectNotFoundException;

	public FundingSource create(FundingSource obj);

	public FundingSource save(FundingSource obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
