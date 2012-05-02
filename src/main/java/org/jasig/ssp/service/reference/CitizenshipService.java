package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Citizenship;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

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
