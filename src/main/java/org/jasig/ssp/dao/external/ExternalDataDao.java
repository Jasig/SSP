package org.jasig.ssp.dao.external;

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

	/**
	 * Retrieve every instance in the database filtered by the supplied status,
	 * sorting, and paging parameters.
	 * 
	 * @param sAndP
	 *            SortingAndPaging
	 * 
	 * @return All entities in the database filtered by the supplied status.
	 */
	PagingWrapper<T> getAll(SortingAndPaging sAndP);
}