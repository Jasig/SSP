package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.CategoryDao;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.service.reference.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Category service
 */
@Service
@Transactional
public class CategoryServiceImpl extends AbstractReferenceService<Category>
		implements CategoryService {

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
