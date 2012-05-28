package org.jasig.ssp.service;

import java.util.UUID;

import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertRouting service
 * 
 * @author jon.adams
 */
public interface EarlyAlertRoutingService extends
		AuditableCrudService<EarlyAlertRouting> {
	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param campus
	 *            Only show data for this campus
	 * @param sAndP
	 *            Sorting and paging options
	 * @return All entities in the database filtered by the supplied status.
	 */
	PagingWrapper<EarlyAlertRouting> getAllForCampus(Campus campus,
			SortingAndPaging sAndP);

	/**
	 * Ensure the campusId matches the embedded campusId in the specified
	 * {@link EarlyAlertRouting} model.
	 * 
	 * @param campusId
	 *            Campus identifier
	 * @param obj
	 *            EarlyAlertRouting model with embedded Campus data
	 * @throws ObjectNotFoundException
	 *             If Campus could not be found
	 */
	void checkCampusIds(final UUID campusId, final EarlyAlertRouting obj)
			throws ObjectNotFoundException;
}