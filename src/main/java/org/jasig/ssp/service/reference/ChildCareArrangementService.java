package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface ChildCareArrangementService extends
		AuditableCrudService<ChildCareArrangement> {

	@Override
	public PagingWrapper<ChildCareArrangement> getAll(SortingAndPaging sAndP);

	@Override
	public ChildCareArrangement get(UUID id) throws ObjectNotFoundException;

	@Override
	public ChildCareArrangement create(ChildCareArrangement obj);

	@Override
	public ChildCareArrangement save(ChildCareArrangement obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
