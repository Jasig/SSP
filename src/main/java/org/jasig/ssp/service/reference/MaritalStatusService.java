package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.MaritalStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface MaritalStatusService extends
		AuditableCrudService<MaritalStatus> {

	@Override
	PagingWrapper<MaritalStatus> getAll(SortingAndPaging sAndP);

	@Override
	MaritalStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	MaritalStatus create(MaritalStatus obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	MaritalStatus save(MaritalStatus obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
