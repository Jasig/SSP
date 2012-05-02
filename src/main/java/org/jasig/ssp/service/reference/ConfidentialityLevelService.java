package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
