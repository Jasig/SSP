package org.jasig.ssp.service.external.impl;

import org.jasig.ssp.dao.external.ExternalDataDao;
import org.jasig.ssp.service.external.ExternalDataService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base class which provides a building block for creating an external data
 * service.
 * 
 * @param <T>
 *            Any external data model class.
 */
@Transactional
public abstract class AbstractExternalDataService<T> implements
		ExternalDataService<T> {

	/**
	 * Need access to the data access instance, so make children provide it.
	 * 
	 * @return the DAO
	 */
	protected abstract ExternalDataDao<T> getDao();

	@Override
	public PagingWrapper<T> getAll(final SortingAndPaging sAndP) {
		return getDao().getAll(sAndP);
	}
}