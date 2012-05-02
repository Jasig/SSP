package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Ethnicity;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
