package org.jasig.ssp.service;

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
}