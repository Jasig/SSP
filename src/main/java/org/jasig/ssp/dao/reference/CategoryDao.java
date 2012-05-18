package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.reference.Category;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Category reference entity.
 */
@Repository
public class CategoryDao extends AbstractReferenceAuditableCrudDao<Category>
		implements AuditableCrudDao<Category> {

	public CategoryDao() {
		super(Category.class);
	}

}
