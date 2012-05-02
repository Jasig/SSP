package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.FundingSource;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface FundingSourceService extends
		AuditableCrudService<FundingSource> {

	@Override
	public PagingWrapper<FundingSource> getAll(SortingAndPaging sAndP);

	@Override
	public FundingSource get(UUID id) throws ObjectNotFoundException;

	@Override
	public FundingSource create(FundingSource obj);

	@Override
	public FundingSource save(FundingSource obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
