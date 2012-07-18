package org.jasig.ssp.service.external;

import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Base class which provides a building block for creating an external data
 * service.
 * 
 * @param <T>
 *            Any external data model class.
 */
public interface ExternalDataService<T> {

	/**
	 * Retrieve every instance in the database filtered by the supplied status,
	 * sorting, and paging parameters.
	 * 
	 * @param sAndP
	 *            SortingAndPaging
	 * 
	 * @return All entities filtered by the supplied parameters.
	 */
	PagingWrapper<T> getAll(final SortingAndPaging sAndP);
}