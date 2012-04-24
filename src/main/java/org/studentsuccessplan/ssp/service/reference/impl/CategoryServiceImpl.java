package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.CategoryDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Category;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.CategoryService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	transient private CategoryDao dao;

	@Override
	public List<Category> getAll(SortingAndPaging sAndP) {
		return dao.getAll(sAndP);
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
	public void delete(final UUID id) throws ObjectNotFoundException {
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
