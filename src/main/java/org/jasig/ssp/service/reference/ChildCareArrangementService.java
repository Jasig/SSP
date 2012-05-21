package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.ChildCareArrangement;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface ChildCareArrangementService extends
		AuditableCrudService<ChildCareArrangement> {

	@Override
	PagingWrapper<ChildCareArrangement> getAll(SortingAndPaging sAndP);

	@Override
	ChildCareArrangement get(UUID id) throws ObjectNotFoundException;

	@Override
	ChildCareArrangement create(ChildCareArrangement obj)
			throws ObjectNotFoundException, ValidationException;

	@Override
	ChildCareArrangement save(ChildCareArrangement obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
