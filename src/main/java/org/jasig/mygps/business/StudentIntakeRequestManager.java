package org.jasig.mygps.business;

import java.util.Calendar;
import java.util.Date;

import javax.mail.SendFailedException;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.dao.TaskDao;
import org.jasig.ssp.dao.reference.MessageTemplateDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.Task;
import org.jasig.ssp.model.reference.ConfidentialityLevel;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.reference.ConfidentialityLevelService;
import org.jasig.ssp.service.reference.MessageTemplateService;
import org.jasig.ssp.service.reference.impl.ConfidentialityLevelServiceImpl;
import org.jasig.ssp.transferobject.AppointmentTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class StudentIntakeRequestManager {

	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StudentIntakeRequestManager.class);
	
	@Autowired
	protected transient TaskService taskService;
	
	@Autowired
	protected transient MessageTemplateDao messageTemplateDao;
	
	@Autowired
	protected transient PersonDao personDao;
	
	@Autowired 
	protected transient MessageService messageService;
	
	@Autowired
	protected transient MessageTemplateService  messageTemplateService;
	
	@Autowired
	protected transient SecurityService securityService;
	
	@Autowired
	protected transient ConfidentialityLevelService confidentialityLevelService;
	

	
	public void processStudentIntakeRequest(AppointmentTO appointment) throws ObjectNotFoundException
	{
		Person student = personDao.get(appointment.getPersonId());
		
		Task studentIntakeTask = createIntakeTask(student);
		
		try 
		{
			
			taskService.create(studentIntakeTask);
			
			SubjectAndBody studentIntakeMessage = messageTemplateService.createStudentIntakeTaskMessage(studentIntakeTask);
				messageService.createMessage(student, appointment.getIntakeEmail(), studentIntakeMessage);
				
			clearIntakeData(student);
			
			personDao.save(student);
			
		} catch (SendFailedException e) {
			LOGGER.error(e.getLocalizedMessage());
		} catch (ValidationException e) {
			LOGGER.error(e.getLocalizedMessage());
		}
		
		
	}



	private Task createIntakeTask(Person student)
			throws ObjectNotFoundException 
	{
		Task studentIntakeTask = new Task();
		studentIntakeTask.setDescription("Your advisor has requested you fill out the Student Intake Form" );
		studentIntakeTask.setName("Student Intake");
		studentIntakeTask.setPerson(student);
		studentIntakeTask.setLink("<a href='intake.html'>Click Here To Fill Out Student Intake</a>");
		studentIntakeTask.setDueDate(new Date(Calendar.getInstance().getTimeInMillis()+259200000));
		studentIntakeTask.setSessionId(securityService.getSessionId());
		studentIntakeTask.setConfidentialityLevel(confidentialityLevelService.get(ConfidentialityLevel.CONFIDENTIALITYLEVEL_EVERYONE));
		return studentIntakeTask;
	}



	private void clearIntakeData(Person student) 
	{
		student.setStudentIntakeRequestDate(new Date());
		student.setStudentIntakeCompleteDate(null);
		student.setDemographics(null);
		student.setEducationGoal(null);
		student.setEducationPlan(null);
		student.getChallenges().clear();
		student.getFundingSources().clear();
		student.getEducationLevels().clear();
	}
	
}
