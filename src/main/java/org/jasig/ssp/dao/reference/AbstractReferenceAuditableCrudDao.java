package org.jasig.ssp.dao.reference;

import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Base CRUD methods for reference model objects.
 * 
 * <p>
 * Defaults to sorting by the <code>Name</code> property unless otherwise
 * specified.
 * 
 * @param <T>
 *            * Any domain type that extends the Auditable class.
 */
@Repository
public abstract class AbstractReferenceAuditableCrudDao<T extends Auditable>
		extends AbstractAuditableCrudDao<T> {

	/**
	 * Constructor that initializes the instance with the specific type.
	 * 
	 * @param persistentClass
	 */
	protected AbstractReferenceAuditableCrudDao(final Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		return processCriteriaWithPaging(createCriteria(), sAndP);
	}
}