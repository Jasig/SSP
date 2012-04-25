package org.studentsuccessplan.mygps.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.studentsuccessplan.mygps.model.transferobject.TaskReportTO;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.PersonService;
import org.studentsuccessplan.ssp.service.SecurityService;
import org.studentsuccessplan.ssp.service.TaskService;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.transferobject.TaskTO;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/mygps/task")
public class MyGpsTaskController extends AbstractMyGpsController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private ChallengeReferralService challengeReferralService;

	@Autowired
	private PersonService personService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsTaskController.class);

	public MyGpsTaskController() {
	}

	public MyGpsTaskController(final TaskService taskService,
			final ChallengeService challengeService,
			final ChallengeReferralService challengeReferralService,
			final PersonService personService,
			final SecurityService securityService) {
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
	boolean createTaskForStudent(@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam("studentId") String studentId,
			@RequestParam("dueDate") Date dueDate,
			@RequestParam("messageTemplate") UUID messageTemplateId)
			throws Exception {

		Person student = personService.personFromUserId(studentId);
		if (student == null) {
			throw new ObjectNotFoundException(
					"Unable to acquire person for supplied student id "
							+ studentId);
		}

		String session = securityService.getSessionId();

		Task task = taskService.createCustomTaskForPerson(name,
				description, student, session);

		taskService.sendNoticeToStudentOnCustomTask(task, messageTemplateId);

		return true;
	}

	@RequestMapping(value = "/createCustom", method = RequestMethod.GET)
	public @ResponseBody
	TaskTO createCustom(@RequestParam("name") String name,
			@RequestParam("description") String description) throws Exception {

		Person student = securityService.currentUser().getPerson();
		String session = securityService.getSessionId();

		Task task = taskService.createCustomTaskForPerson(name, description,
				student, session);

		return new TaskTO(task);
	}

	@RequestMapping(value = "/createForChallengeReferral", method = RequestMethod.GET)
	public @ResponseBody
	TaskTO createForChallengeReferral(
			@RequestParam("challengeId") UUID challengeId,
			@RequestParam("challengeReferralId") UUID challengeReferralId)
			throws Exception {

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
	boolean delete(@RequestParam("taskId") UUID taskId) throws Exception {

		taskService.delete(taskId);
		return true;
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public @ResponseBody
	boolean email(@RequestParam("emailAddress") String emailAddress)
			throws Exception {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<Task> tasks = null;
		Person student = null;
		if (securityService.isAuthenticated()) {
			student = securityService.currentUser().getPerson();
			tasks = taskService.getAllForPerson(student, false, sAndP);
		} else {
			student = securityService.anonymousUser().getPerson();
			tasks = taskService.getAllForSessionId(
					securityService.getSessionId(), true, sAndP);
		}

		List<String> emailAddresses = Lists.newArrayList();
		emailAddresses.add(emailAddress);

		taskService.sendTasksForPersonToEmail(tasks, student, emailAddresses,
				null);

		return true;
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody
	List<TaskTO> getAll() throws Exception {

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<Task> tasks = null;

		if (securityService.isAuthenticated()) {
			Person student = securityService.currentUser().getPerson();
			tasks = taskService.getAllForPerson(student, sAndP);
		} else {
			String sessionId = securityService.getSessionId();
			tasks = taskService.getAllForSessionId(sessionId, sAndP);
		}

		return (tasks != null) ? TaskTO.tasksToTaskTOs(tasks) : null;
	}

	@RequestMapping(value = "/mark", method = RequestMethod.GET)
	public @ResponseBody
	TaskTO mark(@RequestParam("taskId") UUID taskId,
			@RequestParam("complete") Boolean complete) throws Exception {

		Task task = taskService.get(taskId);
		taskService.markTaskCompletion(task, complete);
		return new TaskTO(task);
	}

	@RequestMapping(value = "/print", method = RequestMethod.GET)
	public ModelAndView print() throws Exception {

		final Map<String, Object> model = new HashMap<String, Object>();

		final SortingAndPaging sAndP = new SortingAndPaging(
				ObjectStatus.ACTIVE);

		List<Task> tasks = null;

		if (securityService.isAuthenticated()) {
			Person student = securityService.currentUser().getPerson();
			tasks = taskService.getAllForPerson(student, false, sAndP);
			model.put("studentName",
					student.getFirstName() + " " + student.getLastName());
		} else {
			String sessionId = securityService.getSessionId();
			tasks = taskService.getAllForSessionId(sessionId, false, sAndP);
			model.put("studentName", "");
		}

		model.put("myBeanData",
				(tasks != null) ? TaskReportTO.tasksToTaskReportTOs(tasks)
						: null);

		return new ModelAndView("actionPlanReport", model);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
