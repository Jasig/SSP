package org.jasig.ssp.service;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * Goal service
 */
public interface GoalService extends
		RestrictedPersonAssocAuditableService<Goal> {

	/**
	 * If tasks are selected, get them, otherwise return the tasks for the
	 * person, (just for the session if it is the anonymous user).
	 * 
	 * @param selectedIds
	 *            Selected {@link Task} identifiers
	 * @param person
	 *            the person
	 * @param requester
	 *            the requester
	 * @param sessionId
	 *            session identifier
	 * @param sAndP
	 *            sorting and paging options
	 * @return Selected tasks, or all tasks for the anonymous user.
	 */
	List<Goal> getGoalsForPersonIfNoneSelected(final List<UUID> selectedIds,
			final Person person, final SspUser requester,
			final String sessionId, final SortingAndPaging sAndP);
}