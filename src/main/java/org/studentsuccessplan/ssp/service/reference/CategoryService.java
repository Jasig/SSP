package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Category;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface CategoryService extends AuditableCrudService<Category> {

	@Override
	List<Category> getAll(SortingAndPaging sAndP);

	@Override
	Category get(UUID id) throws ObjectNotFoundException; // NOPMD by jon on 4/7/12 9:41 PM

	@Override
	Category create(Category obj);

	@Override
	Category save(Category obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
