/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.mygps.web; // NOPMD

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;

import org.jasig.mygps.model.transferobject.TaskReportTO;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.TaskTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/1/mygps/task")
public class MyGpsTaskController extends AbstractBaseController {

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient TaskService taskService;

	@Autowired
	private transient ChallengeService challengeService;

	@Autowired
	private transient ChallengeReferralService challengeReferralService;

	@Autowired
	private transient PersonService personService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsTaskController.class);

	public MyGpsTaskController() {
		super();
	}

	public MyGpsTaskController(final TaskService taskService,
			final ChallengeService challengeService,
			final ChallengeReferralService challengeReferralService,
			final PersonService personService,
			final SecurityService securityService) {
		super();
		this.taskService = taskService;
		this.challengeService = challengeService;
		this.challengeReferralService = challengeReferralService;
		this.personService = personService;
		this.securityService = securityService;
	}

	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "/createCustom", method = RequestMethod.POST)
	public @ResponseBody
	TaskTO createCustom(@RequestParam("name") final String name,
			@RequestParam("description") final String description)
			throws ObjectNotFoundException, ValidationException {

		final Person student = securityService.currentUser().getPerson();
		final String session = securityService.getSessionId();

		final Task task = taskService.createCustomTaskForPerson(name,
				description, student, session);

		return new TaskTO(task);
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "/createForChallengeReferral", method = RequestMethod.POST)
	public @ResponseBody
	TaskTO createForChallengeReferral(
			@RequestParam("challengeId") final UUID challengeId,
			@RequestParam("challengeReferralId") final UUID challengeReferralId)
			throws ObjectNotFoundException, ValidationException {

		final Challenge challenge = challengeService.get(challengeId);
		final ChallengeReferral challengeReferral = challengeReferralService
				.get(challengeReferralId);
		final Person user = securityService.currentUser().getPerson();
		final String session = securityService.getSessionId();

		final Task task = taskService.createForPersonWithChallengeReferral(
				challenge,
				challengeReferral,
				user,
				session);

		return new TaskTO(task);
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public @ResponseBody
	boolean delete(@RequestParam("taskId") final UUID taskId)
			throws ObjectNotFoundException {
		taskService.delete(taskId);
		return true;
	}
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public @ResponseBody
	boolean email(@RequestParam("emailAddress") final String emailAddress)
			throws ObjectNotFoundException, ValidationException {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		final SspUser requestor = securityService.currentUser();

		List<Task> tasks;
		Person student;
		if (securityService.currentlyAuthenticatedUser() != null ) {
			student = securityService.currentUser().getPerson();
			tasks = taskService.getAllForPerson(student, false, requestor,
					sAndP);
		} else {
			student = securityService.anonymousUser().getPerson();
			tasks = taskService.getAllForSessionId(
					securityService.getSessionId(), true, sAndP);
		}

		final List<String> emailAddresses = Lists.newArrayList();
		emailAddresses.add(emailAddress);

		taskService.sendTasksForPersonToEmail(tasks, null, null, student,
				emailAddresses,
				null);

		return true;
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	public @ResponseBody
	List<TaskTO> getAll() {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<Task> tasks;

		if (securityService.currentlyAuthenticatedUser() != null ) {
			final Person student = securityService.currentUser().getPerson();
			tasks = (List<Task>) taskService.getAllForPerson(student,
					securityService.currentUser(), sAndP)
					.getRows();
		} else {
			final String sessionId = securityService.getSessionId();
			tasks = taskService.getAllForSessionId(sessionId, sAndP);
		}

		return tasks == null ? null : TaskTO.toTOList(tasks);
	}

	@RequestMapping(value = "/mark", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	public @ResponseBody
	TaskTO mark(@RequestParam("taskId") final UUID taskId,
			@RequestParam("complete") final Boolean complete)
			throws ObjectNotFoundException {

		final Task task = taskService.get(taskId);
		taskService.markTaskCompletion(task, complete);
		return new TaskTO(task);
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_MY_GPS_TOOL', 'ROLE_ANONYMOUS')")
	@ResponseBody
	public void print(final HttpServletResponse response) throws JRException, IOException {
		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<Task> tasks;

		if (securityService.currentlyAuthenticatedUser() != null) {
			final SspUser requestor = securityService.currentUser();
			final Person student = securityService.currentUser().getPerson();
			tasks = taskService.getAllForPerson(student, false, requestor,
					sAndP);
		} else {
			final String sessionId = securityService.getSessionId();
			tasks = taskService.getAllForSessionId(sessionId, false, sAndP);
		}

		List<TaskReportTO> taskTos = (tasks == null || tasks.isEmpty()) ?
				Lists.<TaskReportTO>newArrayList() :
				TaskReportTO.tasksToTaskReportTOs(tasks);

		JRDataSource beanDS;
		if (taskTos.isEmpty()) {
			beanDS = new JREmptyDataSource();
		} else {
			beanDS = new JRBeanCollectionDataSource(taskTos);
		}
		final Map<String, Object> parameters = Maps.newHashMap();

		response.addHeader("Content-Disposition", "attachment");
		response.setContentType("application/pdf");

		final InputStream is = getClass().getResourceAsStream(
				"/reports/studentTasks.jasper");
		try {
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			try {
				JasperFillManager
						.fillReportToStream(is, os, parameters, beanDS);
				final InputStream decodedInput = new ByteArrayInputStream(
						os.toByteArray());

				response.setHeader(
						"Content-disposition",
						"attachment; filename=StudentTasks.pdf");

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

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
