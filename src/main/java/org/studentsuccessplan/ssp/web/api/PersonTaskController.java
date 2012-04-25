package org.studentsuccessplan.ssp.web.api;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.TaskService;
import org.studentsuccessplan.ssp.transferobject.TaskTO;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Maps;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/person/{personId}/task")
public class PersonTaskController extends
		AbstractPersonAssocController<Task, TaskTO> {

	protected PersonTaskController() {
		super(Task.class, TaskTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonTaskController.class);

	@Autowired
	private transient TaskService service;

	/**
	 * Get All tasks for a given person, group them by their Task Group. A Task
	 * Group name is equivalent to the name of the Challenge associated with the
	 * Task. In the case of Custom Tasks, they are associated with a group named
	 * "Custom".
	 */
	@RequestMapping(value = "/group/", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, List<TaskTO>> getAllTasksWithTaskGroup(
			final @PathVariable UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final Map<String, List<TaskTO>> taskTOsWithTaskGroups = Maps
				.newTreeMap();

		final Map<String, List<Task>> tasksWithTaskGroups = service
				.getAllGroupedByTaskGroup(
						personService.get(personId),
						SortingAndPaging.createForSingleSort(status, start,
								limit, sort, sortDirection, null));

		for (Entry<String, List<Task>> tasksWithTaskGroup : tasksWithTaskGroups
				.entrySet()) {
			taskTOsWithTaskGroups.put(tasksWithTaskGroup.getKey(),
					TaskTO.tasksToTaskTOs(tasksWithTaskGroup.getValue()));
		}

		return taskTOsWithTaskGroups;
	}

	/**
	 * Method Signature on this method will be changed to return a printable
	 * report
	 * 
	 * @throws ObjectNotFoundException
	 */
	@RequestMapping(value = "/print/", method = RequestMethod.GET)
	public @ResponseBody
	List<TaskTO> print(
			final @PathVariable UUID personId,
			final @RequestBody List<UUID> taskIds)
			throws ObjectNotFoundException {

		final List<Task> tasks = service.getTasksInList(taskIds,
				new SortingAndPaging(ObjectStatus.ACTIVE));

		return TaskTO.tasksToTaskTOs(tasks);
	}

	@RequestMapping(value = "/email/", method = RequestMethod.GET)
	public @ResponseBody
	boolean email(
			final @PathVariable UUID personId,
			final @RequestBody @Valid EmailForm emailForm)
			throws Exception {

		final List<Task> tasks = service.getTasksInList(emailForm.getTaskIds(),
				new SortingAndPaging(ObjectStatus.ACTIVE));
		final Person student = personService.get(personId);

		final List<Person> recipients;

		if (emailForm.getRecipientIds() == null) {
			recipients = null;
		} else {
			// get a list of recipients from a list of recipientIds
			recipients = personService.peopleFromListOfIds(
					emailForm.getRecipientIds(),
					new SortingAndPaging(ObjectStatus.ACTIVE));
		}

		service.sendTasksForPersonToEmail(tasks, student,
				emailForm.getRecipientEmailAddresses(), recipients);

		return true;
	}

	/**
	 * Command Object for the email method of the PersonTaskController
	 * Only one of either recipientEmailAddresses or recipientIds is required
	 * 
	 */
	protected class EmailForm {
		@NotNull
		private List<UUID> taskIds;

		private List<String> recipientEmailAddresses;
		private List<UUID> recipientIds;

		public List<UUID> getTaskIds() {
			return taskIds;
		}

		public void setTaskIds(List<UUID> taskIds) {
			this.taskIds = taskIds;
		}

		public List<String> getRecipientEmailAddresses() {
			return recipientEmailAddresses;
		}

		public void setRecipientEmailAddresses(
				List<String> recipientEmailAddresses) {
			this.recipientEmailAddresses = recipientEmailAddresses;
		}

		public List<UUID> getRecipientIds() {
			return recipientIds;
		}

		public void setRecipientIds(List<UUID> recipientIds) {
			this.recipientIds = recipientIds;
		}
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected TaskService getService() {
		return service;
	}
}
