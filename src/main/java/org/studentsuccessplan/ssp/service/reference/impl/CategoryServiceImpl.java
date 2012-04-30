package org.studentsuccessplan.ssp.service.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.dao.reference.CategoryDao;
import org.studentsuccessplan.ssp.model.reference.Category;
import org.studentsuccessplan.ssp.service.reference.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl extends AbstractReferenceService<Category>
		implements CategoryService {

	public CategoryServiceImpl() {
		super(Category.class);
	}

	@Autowired
	transient private CategoryDao dao;

	protected void setDao(final CategoryDao dao) {
		this.dao = dao;
	}

	@Override
	protected CategoryDao getDao() {
		return dao;
	}
}
