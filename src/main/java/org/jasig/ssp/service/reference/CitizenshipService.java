package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface CitizenshipService extends AuditableCrudService<Citizenship> {

	@Override
	public PagingWrapper<Citizenship> getAll(SortingAndPaging sAndP);

	@Override
	public Citizenship get(UUID id) throws ObjectNotFoundException;

	@Override
	public Citizenship create(Citizenship obj);

	@Override
	public Citizenship save(Citizenship obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
