package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface ConfidentialityLevelService extends
		AuditableCrudService<ConfidentialityLevel> {

	@Override
	public PagingWrapper<ConfidentialityLevel> getAll(SortingAndPaging sAndP);

	@Override
	public ConfidentialityLevel get(UUID id) throws ObjectNotFoundException;

	@Override
	public ConfidentialityLevel create(ConfidentialityLevel obj);

	@Override
	public ConfidentialityLevel save(ConfidentialityLevel obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
