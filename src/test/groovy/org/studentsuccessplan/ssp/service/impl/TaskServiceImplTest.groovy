package org.studentsuccessplan.ssp.service.impl

import static org.junit.Assert.*

import java.util.UUID

import org.junit.Test
import org.junit.Before
import org.studentsuccessplan.ssp.dao.TaskDao
import org.studentsuccessplan.ssp.model.ObjectStatus
import org.studentsuccessplan.ssp.model.Person
import org.studentsuccessplan.ssp.model.Task
import org.studentsuccessplan.ssp.model.reference.Challenge
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral

class TaskServiceImplTest {
	//the task that will get returned from the mock dao
	private Task testTask

	//a mock dao for the service to call;
	private def dao = [
		get:{UUID id -> testTask},
		save:{Task task -> return task}] as TaskDao

	private TaskServiceImpl service

	@Before
	void setup(){
		service = new TaskServiceImpl(dao:dao)
		testTask = new Task(id:UUID.randomUUID(),
				challenge: new Challenge(),
				challengeReferral: new ChallengeReferral(),
				description:"test description",
				objectStatus:ObjectStatus.ACTIVE,
				person: new Person());
	}

	@Test
	void markTaskComplete() {
		service.markTaskComplete(testTask)
		assertNotNull(testTask.getCompletedDate())
	}

	@Test
	void markTaskIncomplete(){
		service.markTaskIncomplete(testTask)
		assertNull(testTask.getCompletedDate())
	}

	@Test
	void markTaskCompletion_true(){
		service.markTaskCompletion(testTask, true)
		assertNotNull(testTask.getCompletedDate());
	}

	@Test
	void markTaskCompletion_false(){
		service.markTaskCompletion(testTask, false)
		assertNull(testTask.getCompletedDate())
	}

	@Test
	void setReminderSentDateToToday() {
		service.setReminderSentDateToToday(testTask)
		assertNotNull(testTask.getReminderSentDate())
	}

	@Test
	void save(){
		Task newTask = service.save(testTask)
		assertEquals(newTask.getId(), testTask.getId())
		assertEquals(newTask.getChallenge(), testTask.getChallenge())
		assertEquals(newTask.getChallengeReferral(), testTask.getChallengeReferral())
		assertEquals(newTask.description, testTask.description)
	}
}