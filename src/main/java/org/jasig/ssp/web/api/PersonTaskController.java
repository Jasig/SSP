/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api; // NOPMD

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jasig.ssp.factory.TaskTOFactory;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Strength;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.GoalService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.StrengthService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.transferobject.form.EmailPersonTasksForm;
import org.jasig.ssp.transferobject.reports.StudentActionPlanTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Maps;


/**
 * Services to manipulate Tasks.
 * <p>
 * Mapped to URI path <code>/1/person/{personId}/task</code>
 */
@Controller
@RequestMapping("/1/person/{personId}/task")
public class PersonTaskController extends
		AbstractRestrictedPersonAssocController<Task, TaskTO> {

	protected PersonTaskController() {
		super(Task.class, TaskTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonTaskController.class);

	@Autowired
	private transient TaskService service;

	@Autowired
	private transient TaskTOFactory factory;

	@Autowired
	private transient GoalService goalService;
	

	@Autowired
	private transient StrengthService strengthService;

	@Override
	protected TaskTOFactory getFactory() {
		return factory;
	}

	@Autowired
	private transient SecurityService securityService;

	/**
	 * Get All tasks for a given person, group them by their Task Group. A Task
	 * Group name is equivalent to the name of the Challenge associated with the
	 * Task. In the case of Custom Tasks, they are associated with a group named
	 * "Custom".
	 * 
	 * @param personId
	 *            Person identifier
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
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	Map<String, List<TaskTO>> getAllTasksWithTaskGroup(
			final @PathVariable UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		checkPermissionForOp("READ");

		final Map<String, List<TaskTO>> taskTOsWithTaskGroups = Maps
				.newTreeMap();

		final Map<String, List<Task>> tasksWithTaskGroups = service
				.getAllGroupedByTaskGroup(personService.get(personId),
						securityService.currentUser(), SortingAndPaging
								.createForSingleSortWithPaging(status, start, limit,
										sort, sortDirection, null));

		for (final Entry<String, List<Task>> tasksWithTaskGroup : tasksWithTaskGroups
				.entrySet()) {
			taskTOsWithTaskGroups.put(tasksWithTaskGroup.getKey(),
					TaskTO.toTOList(tasksWithTaskGroup.getValue()));
		}

		return taskTOsWithTaskGroups;
	}

	/**
	 * <p>
	 * Print the selected Tasks.
	 * </p>
	 * <p>
	 * If no tasks are selected, then just return the tasks for the person,
	 * (just for the session if it is the anon user).
	 * </p>
	 * 
	 * <p>
	 * Method Signature on this method will be changed to return a printable
	 * report
	 * </p>
	 * 
	 * @param response
	 *            Servlet response
	 * @param personId
	 *            Person id
	 * @param taskIds
	 *            the tasks
	 * @param goalIds
	 *            the goals
	 * @throws ObjectNotFoundException
	 *             If the Person id could not be found
	 * @throws JRException
	 *             Thrown for any reporting exception
	 * @throws IOException
	 *             IO exception
	 */
	@RequestMapping(value = "/print", method = RequestMethod.POST)
	@DynamicPermissionChecking
	public @ResponseBody
	void print(final HttpServletResponse response,
			final @PathVariable UUID personId,
			final @RequestParam(required = false) List<UUID> taskIds,
			final @RequestParam(required = false) List<UUID> goalIds,
			final @RequestParam(required = false) List<UUID> strengthIds
			) throws ObjectNotFoundException, JRException, IOException {

		checkPermissionForOp("READ");

		final SspUser requestor = securityService.currentUser();
		final Map<String, ArrayList<Task>> challengesAndTasks = Maps
				.newHashMap();
		final SortingAndPaging sAndP = new SortingAndPaging(ObjectStatus.ACTIVE);
		final Person person = personService.get(personId);
		final List<Task> tasks = service.getTasksForPersonIfNoneSelected(
				taskIds, person, requestor, securityService.getSessionId(),
				sAndP);
		final List<Goal> goals = goalService.getGoalsForPersonIfNoneSelected(
				goalIds, person, requestor, securityService.getSessionId(),
				sAndP);

		final List<Strength> strengths = strengthService.getStrengthsForPersonIfNoneSelected(
				strengthIds, person, requestor, securityService.getSessionId(),
				sAndP);
		final Iterator<Task> taskIter = tasks.iterator();
		while (taskIter.hasNext()) {
			final Task task = taskIter.next();
			final Challenge challenge = task.getChallenge();

			// handle an empty challenge
			final String challengeName = challenge == null ? "" : task
					.getChallenge().getName();

			ArrayList<Task> taskList = challengesAndTasks.get(challengeName);
			if (taskList == null) {
				taskList = new ArrayList<Task>(); // NOPMD
				taskList.add(task);
				challengesAndTasks.put(challengeName, taskList);
			} else {
				taskList.add(task);
			}
		}

		final Collection<ArrayList<Task>> taskList = challengesAndTasks
				.values();
		final ArrayList<StudentActionPlanTO> studentActionPlanTOs = new ArrayList<StudentActionPlanTO>();
		final Iterator<ArrayList<Task>> tasklistIter = taskList.iterator();
		while (tasklistIter.hasNext()) {
			final ArrayList<Task> currentTaskList = tasklistIter.next();
            final List<TaskTO> taskTOList = TaskTO.toTOList(currentTaskList);

			studentActionPlanTOs.add(new StudentActionPlanTO(taskTOList, // NOPMD
					(currentTaskList.get(0).getChallenge() == null ? ""
							: currentTaskList.get(0).getChallenge().getName()),
					(currentTaskList.get(0).getChallenge() == null ? ""
							: currentTaskList.get(0).getChallenge()
									.getDescription())));
		}

		JRDataSource beanDS;
		if (studentActionPlanTOs == null || studentActionPlanTOs.size() <= 0) {
			beanDS = new JREmptyDataSource();
		}
		else {
			beanDS = new JRBeanCollectionDataSource(studentActionPlanTOs);
		}

		final JRBeanCollectionDataSource goalsDS = new JRBeanCollectionDataSource(
				goals);
		
		final JRBeanCollectionDataSource strengthsDS = new JRBeanCollectionDataSource(
				strengths);
		final Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("studentName",
				person.getFirstName() + " " + person.getLastName());
		parameters.put("studentId", person.getSchoolId());
		parameters.put("initialDate", person.getCreatedDate());
		parameters.put("strengths", strengthsDS);
		parameters.put("reviewDate", new Date());
		parameters.put("goals", goalsDS);

		response.addHeader("Content-Disposition", "attachment");
		response.setContentType("application/pdf");

		final InputStream is = getClass().getResourceAsStream(
				"/reports/studentTaskReport.jasper");
		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				JasperFillManager
						.fillReportToStream(is, os, parameters, beanDS);
				final InputStream decodedInput = new ByteArrayInputStream(
						os.toByteArray());

				response.setHeader(
						"Content-disposition",
						"attachment; filename=Student_Task_Report-"
								+ person.getLastName() + ".pdf");

				JasperExportManager.exportReportToPdfStream(decodedInput,
						response.getOutputStream());
				response.flushBuffer();
			} finally {
				os.close();
			}
		} finally {
			is.close();
		}
	}

	/**
	 * <p>
	 * Email the selected tasks to addresses and/or people.
	 * </p>
	 * 
	 * <p>
	 * If no tasks are selected, then just return the tasks for the person,
	 * (just for the session if it is the anon user).
	 * </p>
	 * 
	 * @param personId
	 *            Person identifier
	 * @param emailForm
	 *            e-mail form data
	 * @return True if action was successful
	 * @throws ValidationException
	 *             If any data is invalid.
	 * @throws ObjectNotFoundException
	 *             If any reference data could not be found.
	 */
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	@DynamicPermissionChecking
	public @ResponseBody
	boolean email(final @PathVariable UUID personId,
			final @RequestBody @Valid EmailPersonTasksForm emailForm)
			throws ObjectNotFoundException, ValidationException {

		checkPermissionForOp("READ");

		List<Person> recipients = null; // NOPMD because passing null as allowed

		final Person student = personService.get(personId);

		// goals
		final List<Goal> goals = goalService.getGoalsForPersonIfNoneSelected(
				emailForm.getGoalIds(), student, securityService.currentUser(),
				securityService.getSessionId(), new SortingAndPaging(
						ObjectStatus.ACTIVE));
		
		// strengths
		final List<Strength> strengths = strengthService.getStrengthsForPersonIfNoneSelected(
				emailForm.getStrengthIds(), student, securityService.currentUser(),
				securityService.getSessionId(), new SortingAndPaging(
						ObjectStatus.ACTIVE));

		// tasks
		final List<Task> tasks = service.getTasksForPersonIfNoneSelected(
				emailForm.getTaskIds(), student, securityService.currentUser(),
				securityService.getSessionId(), new SortingAndPaging(
						ObjectStatus.ACTIVE));

		service.sendTasksForPersonToEmail(tasks, goals, strengths, student,
				emailForm);

		return true;
	}
	
	/**
	 * bulk save changes to the specified IDs and objects, for the specified person.
	 * 
	 * @param personId
	 *            the person
	 * @param maps
	 *            the list of maps to update
	 * @return the updated instance
	 * @throws ObjectNotFoundException
	 *             If the specified ID could not be found.
	 * @throws ValidationException
	 *             If the updated data was not valid.
	 */
	@RequestMapping(value = "/bulk", method = RequestMethod.POST)
	@DynamicPermissionChecking
	public @ResponseBody
	List<TaskTO> bulk(
			@PathVariable final UUID personId,
			@Valid @RequestBody final List<LinkedHashMap> maps)
			throws ObjectNotFoundException, ValidationException {

		checkPermissionForOp("WRITE");
		if (maps == null || maps.size() == 0) {
			throw new ValidationException("Missing data.");
		}
		if (personId == null) {
			throw new IllegalArgumentException("Student identifier is required.");
		}
		Person student = personService.get(personId);
		List<TaskTO> taskTOs = null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			taskTOs = mapper.convertValue(maps, new TypeReference<List<TaskTO>>() { });
		}catch(IllegalArgumentException exp){
			throw new IllegalArgumentException("Converting objects to Tasks resulted in illegal argument exception.");
		}
		List<Task> createdTasks = service.create(taskTOs, student);
		return factory.asTOList(createdTasks);
	}


	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected TaskService getService() {
		return service;
	}

	@Override
	public String permissionBaseName() {
		return "TASK";
	}
}
