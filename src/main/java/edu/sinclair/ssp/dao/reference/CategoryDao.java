package edu.sinclair.ssp.dao.reference;

import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.dao.AuditableCrudDao;
import edu.sinclair.ssp.model.reference.Category;

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
