package org.jasig.ssp.dao.external;

import java.io.Serializable;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface ExternalDataDao<T> {

	PagingWrapper<T> getAll(SortingAndPaging sAndP);

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 * @return The specified instance if found
	 * @throws ObjectNotFoundException
	 *             If object was not found.
	 */
	T get(Serializable id) throws ObjectNotFoundException;
}
