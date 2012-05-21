package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Ethnicity;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface EthnicityService extends AuditableCrudService<Ethnicity> {

	@Override
	PagingWrapper<Ethnicity> getAll(SortingAndPaging sAndP);

	@Override
	Ethnicity get(UUID id) throws ObjectNotFoundException;

	@Override
	Ethnicity create(Ethnicity obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	Ethnicity save(Ethnicity obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
