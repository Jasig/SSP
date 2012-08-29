package org.jasig.ssp.service;

import java.util.Date;

import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.EarlyAlertResponse;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * EarlyAlertResponse service
 * 
 * @author jon.adams
 * 
 */
public interface EarlyAlertResponseService
		extends AuditableCrudService<EarlyAlertResponse> {
	/**
	 * Retrieve every response in the database filtered by the supplied status
	 * and early alert.
	 * 
	 * @param earlyAlert
	 * @param sAndP
	 *            Sorting and paging options
	 * @return All entities in the database filtered by the supplied status.
	 */
	PagingWrapper<EarlyAlertResponse> getAllForEarlyAlert(
			EarlyAlert earlyAlert,
			SortingAndPaging sAndP);

	Long getEarlyAlertResponseCountForCoach(Person coach, Date createDateFrom,
			Date createDateTo);

}