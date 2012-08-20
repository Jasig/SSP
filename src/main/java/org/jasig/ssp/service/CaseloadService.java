package org.jasig.ssp.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.CaseloadRecord;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Case load service
 */  
public interface CaseloadService {
	/**
	 * Gets the case load for the specified {@link CoachPersonLiteTO} (advisor)
	 * filtered by the specified program status, object status, sorting, and
	 * paging parameters.
	 * 
	 * @param programStatus
	 *            program status; optional — defaults to
	 *            {@link ProgramStatus#ACTIVE_ID}.
	 * @param coach
	 *            coach (advisor); required
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return the case load for the specified {@link CoachPersonLiteTO}
	 *         (advisor) filtered by the specified parameters
	 * @throws ObjectNotFoundException
	 *             If any referenced data could not be loaded.
	 */
	PagingWrapper<CaseloadRecord> caseLoadFor(ProgramStatus programStatus,
			Person coach, SortingAndPaging sAndP)
			throws ObjectNotFoundException;
	
	Long caseLoadCountFor(ProgramStatus programStatus,
			Person coach, List<UUID> studentTypeIds, Date programStatusDateFrom, Date programStatusDateTo) throws ObjectNotFoundException;	
}