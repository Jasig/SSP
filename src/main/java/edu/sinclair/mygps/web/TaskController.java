package edu.sinclair.mygps.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.sinclair.mygps.business.TaskManager;
import edu.sinclair.mygps.model.transferobject.TaskTO;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.service.SecurityService;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskManager taskManager;

	@Autowired
	private SecurityService securityService;

	private Logger logger = LoggerFactory.getLogger(TaskController.class);

	// Needed for tests
	public void setManager(TaskManager taskManager){
		this.taskManager = taskManager;
	}

	/*
	 * Allow external services to create tasks for a student, provided they have the correct security token.
	 * @param name - name of the task
	 * @param description - description of the task
	 * @param studentId - a student id of the student receiving the task
	 * @param token - security token allowing access to the service.
	 */
	@RequestMapping(value="/createTaskForStudent", method = RequestMethod.POST)
	public @ResponseBody boolean createTaskForStudent(@RequestParam("name") String name, @RequestParam("description") String description,
			@RequestParam("studentId") String studentId, @RequestParam("dueDate") Date dueDate, @RequestParam("token") String token,
			@RequestParam("messageTemplate") UUID messageTemplate)
			throws Exception {
		try {
			if(token.equals("30e96815b3b985df8ea6f2f3c4ef00cb9aaa11620ae3a7ae44eb3808d25083a87663c6e390400f164b9b88a9f7681cb3c96e77b830fa257d0c286e7d6")){
				taskManager.createTaskForStudent(name, description, studentId, dueDate, messageTemplate);
				return true;
			}else{
				logger.error("Token exception in TaskController.createTaskForStudent");
				return false;
			}
		} catch (Exception e) {
			logger.error("ERROR: createTaskForStudent(): {}", e.getMessage(), e);
			return false;
		}
	}

	@RequestMapping(value="/createCustom", method = RequestMethod.GET)
	public @ResponseBody TaskTO createCustom(@RequestParam("name") String name, @RequestParam("description") String description) throws Exception {

		try {
			return taskManager.createCustom(name, description);
		} catch (Exception e) {
			logger.error("ERROR : createCustom() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value="/createForChallengeReferral", method = RequestMethod.GET)
	public @ResponseBody
	TaskTO createForChallengeReferral(
			@RequestParam("challengeId") UUID challengeId,
			@RequestParam("challengeReferralId") UUID challengeReferralId)
					throws Exception {

		try {
			return taskManager.createTaskForChallengeReferral(challengeId, challengeReferralId);
		} catch (Exception e) {
			logger.error("ERROR : createForChallengeReferral() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public @ResponseBody boolean delete(@RequestParam("taskId") String taskId) throws Exception {

		try {
			return taskManager.deleteTask(taskId);
		} catch (Exception e) {
			logger.error("ERROR : delete() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value="/email", method = RequestMethod.GET)
	public @ResponseBody boolean email(@RequestParam("emailAddress") String emailAddress) throws Exception {

		try {
			return taskManager.email(emailAddress);
		} catch (Exception e) {
			logger.error("ERROR : email() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value="/getAll", method = RequestMethod.GET)
	public @ResponseBody List<TaskTO> getAll() throws Exception {

		try {
			return taskManager.getAllTasks();
		} catch (Exception e) {
			logger.error("ERROR : getAll() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value="/mark", method = RequestMethod.GET)
	public @ResponseBody TaskTO mark(@RequestParam("taskId") String taskId, @RequestParam("complete") Boolean complete) throws Exception {

		try {
			return taskManager.markTask(taskId, complete);
		} catch (Exception e) {
			logger.error("ERROR : markTask() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value="/print", method = RequestMethod.GET)
	public ModelAndView print() throws Exception {

		try {
			Map<String, Object> model = new HashMap<String, Object>();

			if (securityService.isAuthenticated()) {
				Person student = securityService.currentlyLoggedInSspUser()
						.getPerson();
				model.put("studentName",
						student.getFirstName() + " " + student.getLastName());
			} else {
				model.put("studentName", "");
			}
			model.put("myBeanData", taskManager.getActionPlanReportData());

			return new ModelAndView("actionPlanReport", model);
		} catch (Exception e) {
			logger.error("ERROR : print() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody String handleException(Exception e, HttpServletResponse response) {
		logger.error("ERROR : handleException()", e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return e.getMessage();
	}

}
