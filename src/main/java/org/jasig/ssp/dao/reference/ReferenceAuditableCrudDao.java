package org.jasig.ssp.dao.reference;

import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Base CRUD methods for reference model objects.
 * <p>
 * Defaults to sorting by the <code>Name</code> property unless otherwise
 * specified.
 * 
 * @param <T>
 *            Any domain type that extends the Auditable class.
 */
@Repository
public abstract class ReferenceAuditableCrudDao<T extends Auditable> extends
		AbstractAuditableCrudDao<T> {

	/**
	 * Constructor that initializes the instance with the specific type.
	 * 
	 * @param persistentClass
	 */
	protected ReferenceAuditableCrudDao(Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	@SuppressWarnings("unchecked")
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		long totalRows = (Long) createCriteria().setProjection(
				Projections.rowCount()).uniqueResult();

		return new PagingWrapper<T>(totalRows, createCriteria(sAndP).list());
	}
}
