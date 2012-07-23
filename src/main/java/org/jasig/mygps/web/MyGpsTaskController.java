package org.jasig.mygps.web; // NOPMD

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.SendFailedException;

import org.jasig.mygps.model.transferobject.TaskReportTO;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.security.SspUser;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

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

	/*
	 * Allow external services to create tasks for a student, provided they have
	 * the correct security token.
	 * 
	 * @param name - name of the task
	 * 
	 * @param description - description of the task
	 * 
	 * @param studentId - a student id of the student receiving the task
	 */
	@RequestMapping(value = "/createTaskForStudent", method = RequestMethod.POST)
	public @ResponseBody
	boolean createTaskForStudent(@RequestParam("name") final String name,
			@RequestParam("description") final String description,
			@RequestParam("studentId") final String studentId,
			@RequestParam("dueDate") final Date dueDate)
			throws ObjectNotFoundException, ValidationException,
			SendFailedException {

		final Person student = personService.personFromUsername(studentId);
		if (student == null) {
			throw new ObjectNotFoundException(
					"Unable to acquire person for supplied student id "
							+ studentId, "Person");
		}

		final String session = securityService.getSessionId();
		final Task task = taskService.createCustomTaskForPerson(name,
				description, student, session);

		taskService.sendNoticeToStudentOnCustomTask(task);

		return true;
	}

	@RequestMapping(value = "/createCustom", method = RequestMethod.GET)
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

	@RequestMapping(value = "/createForChallengeReferral", method = RequestMethod.GET)
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

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public @ResponseBody
	boolean delete(@RequestParam("taskId") final UUID taskId)
			throws ObjectNotFoundException {
		taskService.delete(taskId);
		return true;
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public @ResponseBody
	boolean email(@RequestParam("emailAddress") final String emailAddress)
			throws ObjectNotFoundException, ValidationException {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		final SspUser requestor = securityService.currentUser();

		List<Task> tasks;
		Person student;
		if (securityService.isAuthenticated()) {
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

		taskService.sendTasksForPersonToEmail(tasks, null, student,
				emailAddresses,
				null);

		return true;
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody
	List<TaskTO> getAll() {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<Task> tasks;

		if (securityService.isAuthenticated()) {
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

	@RequestMapping(value = "/mark", method = RequestMethod.GET)
	public @ResponseBody
	TaskTO mark(@RequestParam("taskId") final UUID taskId,
			@RequestParam("complete") final Boolean complete)
			throws ObjectNotFoundException {

		final Task task = taskService.get(taskId);
		taskService.markTaskCompletion(task, complete);
		return new TaskTO(task);
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public ModelAndView print() {
		final Map<String, Object> model = new HashMap<String, Object>();
		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<Task> tasks;

		if (securityService.isAuthenticated()) {
			final SspUser requestor = securityService.currentUser();
			final Person student = securityService.currentUser().getPerson();
			tasks = taskService.getAllForPerson(student, false, requestor,
					sAndP);
			model.put("studentName",
					student.getFirstName() + " " + student.getLastName());
		} else {
			final String sessionId = securityService.getSessionId();
			tasks = taskService.getAllForSessionId(sessionId, false, sAndP);
			model.put("studentName", "");
		}

		model.put("myBeanData",
				tasks == null ? null : TaskReportTO.tasksToTaskReportTOs(tasks));

		return new ModelAndView("actionPlanReport", model);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
