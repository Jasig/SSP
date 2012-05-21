package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Citizenship;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface CitizenshipService extends AuditableCrudService<Citizenship> {

	@Override
	PagingWrapper<Citizenship> getAll(SortingAndPaging sAndP);

	@Override
	Citizenship get(UUID id) throws ObjectNotFoundException;

	@Override
	Citizenship create(Citizenship obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	Citizenship save(Citizenship obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
