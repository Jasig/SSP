package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.CategoryDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Category;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao dao;

	@Override
	public List<Category> getAll(final ObjectStatus status,
			final Integer firstResult, final Integer maxResults,
			final String sort, final String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public Category get(final UUID id) throws ObjectNotFoundException {
		final Category obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "Category");
		}

		return obj;
	}

	@Override
	public Category create(final Category obj) {
		return dao.save(obj);
	}

	@Override
	public Category save(final Category obj) throws ObjectNotFoundException {
		Category current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		Category current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(final CategoryDao dao) {
		this.dao = dao;
	}
}
