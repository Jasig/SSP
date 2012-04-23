package org.studentsuccessplan.ssp.web.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.TaskService;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/person/{personId}/task")
public class PersonTaskController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonTaskController.class);

	@Autowired
	private transient TaskService service;

	@Autowired
	private transient PersonService personService;

	/**
	 * Get All tasks for a given person, group them by their Task Group. A Task
	 * Group name is equivalent to the name of the Challenge associated with the
	 * Task. In the case of Custom Tasks, they are associated with a group named
	 * "Custom".
	 * 
	 * @param personId
	 *            Person identifier
	 * @param id
	 *            Task identifier (though it is currently unused)
	 * @param status
	 *            Object status
	 * @param start
	 *            First result index
	 * @param limit
	 *            Maximum amount of result rows to return
	 * @param sort
	 *            Sort property
	 * @param sortDirection
	 *            Sort direction
	 * @return Task lists in task groups.
	 * @throws ObjectNotFoundException
	 *             If the person identifier is not valid.
	 */
	@RequestMapping(value = "/group/", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, List<Task>> getAllTasksWithTaskGroup(
			final @PathVariable UUID personId, final @PathVariable UUID id,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		service.getAllGroupedByTaskGroup(personService.get(personId),
				SortingAndPaging.createForSingleSort(status, start, limit,
						sort, sortDirection, null));

		return null;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
