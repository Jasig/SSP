package org.jasig.ssp.service.external;

import java.io.Serializable;

import org.jasig.ssp.service.ObjectNotFoundException;
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

	PagingWrapper<T> getAll(final SortingAndPaging sAndP);

	T get(final Serializable id) throws ObjectNotFoundException;
}