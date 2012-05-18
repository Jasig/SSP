package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.FundingSource;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface FundingSourceService extends
		AuditableCrudService<FundingSource> {

	@Override
	PagingWrapper<FundingSource> getAll(SortingAndPaging sAndP);

	@Override
	FundingSource get(UUID id) throws ObjectNotFoundException;

	@Override
	FundingSource create(FundingSource obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	FundingSource save(FundingSource obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
