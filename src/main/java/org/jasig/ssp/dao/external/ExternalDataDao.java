package org.jasig.ssp.dao.external;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * ExternalData DAO.
 * 
 * @author jon.adams
 * 
 * @param <T>
 *            Any <code>ExternalData</code> model.
 */
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
	T get(@NotNull Serializable id) throws ObjectNotFoundException;
}