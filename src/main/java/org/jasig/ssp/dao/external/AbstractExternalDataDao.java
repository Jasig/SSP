package org.jasig.ssp.dao.external;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.AbstractDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Abstract ExternalData DAO
 * 
 * @author jon.adams
 * 
 * @param <T>
 *            Any <code>ExternalData</code> model.
 */
public abstract class AbstractExternalDataDao<T>
		extends AbstractDao<T>
		implements ExternalDataDao<T> {

	protected AbstractExternalDataDao(@NotNull final Class<T> persistentClass) {
		super(persistentClass);
	}

	@Override
	public PagingWrapper<T> getAll(final ObjectStatus status) {
		return processCriteriaWithSortingAndPaging(createCriteria(),
				new SortingAndPaging(status));
	}

	@Override
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		return processCriteriaWithSortingAndPaging(createCriteria(),
				sAndP);
	}
}