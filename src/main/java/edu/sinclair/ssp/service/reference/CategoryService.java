package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Category;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface CategoryService extends AuditableCrudService<Category> {

	@Override
	List<Category> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	Category get(UUID id) throws ObjectNotFoundException; // NOPMD by jon on 4/7/12 9:41 PM

	@Override
	Category create(Category obj);

	@Override
	Category save(Category obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
