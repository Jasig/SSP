package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Ethnicity;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface EthnicityService extends AuditableCrudService<Ethnicity> {

	@Override
	public PagingWrapper<Ethnicity> getAll(SortingAndPaging sAndP);

	@Override
	public Ethnicity get(UUID id) throws ObjectNotFoundException;

	@Override
	public Ethnicity create(Ethnicity obj);

	@Override
	public Ethnicity save(Ethnicity obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
