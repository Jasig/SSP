package org.jasig.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.Category;

/**
 * Data access class for the Category reference entity.
 */
@Repository
public class CategoryDao extends ReferenceAuditableCrudDao<Category> implements
		AuditableCrudDao<Category> {

	public CategoryDao() {
		super(Category.class);
	}

}
