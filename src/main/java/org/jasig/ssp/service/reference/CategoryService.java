package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Category;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface CategoryService extends AuditableCrudService<Category> {

	@Override
	PagingWrapper<Category> getAll(SortingAndPaging sAndP);

	@Override
	Category get(UUID id) throws ObjectNotFoundException;

	@Override
	Category create(Category obj);

	@Override
	Category save(Category obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
