package org.jasig.ssp.factory.reference.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jasig.ssp.dao.reference.CategoryDao;
import org.jasig.ssp.factory.reference.AbstractReferenceTOFactory;
import org.jasig.ssp.factory.reference.CategoryTOFactory;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.transferobject.reference.CategoryTO;

@Service
@Transactional(readOnly = true)
public class CategoryTOFactoryImpl extends
		AbstractReferenceTOFactory<CategoryTO, Category>
		implements CategoryTOFactory {

	public CategoryTOFactoryImpl() {
		super(CategoryTO.class, Category.class);
	}

	@Autowired
	private transient CategoryDao dao;

	@Override
	protected CategoryDao getDao() {
		return dao;
	}

}
