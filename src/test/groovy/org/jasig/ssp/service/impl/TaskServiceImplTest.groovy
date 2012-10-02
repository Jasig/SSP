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
package org.jasig.ssp.service.impl

import static org.junit.Assert.*

import java.util.UUID

import org.junit.Test
import org.junit.Before
import org.jasig.ssp.dao.TaskDao
import org.jasig.ssp.model.ObjectStatus
import org.jasig.ssp.model.Person
import org.jasig.ssp.model.Task
import org.jasig.ssp.model.reference.Challenge
import org.jasig.ssp.model.reference.ChallengeReferral
import org.jasig.ssp.security.SspUser
import org.springframework.security.core.GrantedAuthority


class TaskServiceImplTest {
	//the task that will get returned from the mock dao
	private Task testTask1

	private Person testStudent = new Person(id:UUID.randomUUID());

	private List<Task> all

	//a mock dao for the service to call;
	private def dao = [
		get:{UUID id -> testTask1},
		save:{Task task -> return task},
		getAllForPersonId:{personId, requestor, sAndP -> return all}
	] as TaskDao

	private TaskServiceImpl service

	@Before
	void setup(){
		service = new TaskServiceImpl(dao:dao)
		testTask1 = new Task(id:UUID.randomUUID(),
				challenge: new Challenge(name:"testChallenge"),
				challengeReferral: new ChallengeReferral(),
				description:"test description",
				objectStatus:ObjectStatus.ACTIVE,
				person: testStudent,
				createdBy: testStudent);
		all = [testTask1]

	}

	@Test
	void markTaskComplete() {
		service.markTaskComplete(testTask1)
		assertNotNull(testTask1.getCompletedDate())
	}

	@Test
	void markTaskIncomplete(){
		service.markTaskIncomplete(testTask1)
		assertNull(testTask1.getCompletedDate())
	}

	@Test
	void markTaskCompletion_true(){
		service.markTaskCompletion(testTask1, true)
		assertNotNull(testTask1.getCompletedDate());
	}

	@Test
	void markTaskCompletion_false(){
		service.markTaskCompletion(testTask1, false)
		assertNull(testTask1.getCompletedDate())
	}

	@Test
	void setReminderSentDateToToday() {
		service.setReminderSentDateToToday(testTask1)
		assertNotNull(testTask1.getReminderSentDate())
	}

	@Test
	void save(){
		Task newTask = service.save(testTask1)
		assertEquals(newTask.getId(), testTask1.getId())
		assertEquals(newTask.getChallenge(), testTask1.getChallenge())
		assertEquals(newTask.getChallengeReferral(), testTask1.getChallengeReferral())
		assertEquals(newTask.description, testTask1.description)
	}

	@Test
	void getAllGroupedByTaskGroup(){
		all << new Task(id:UUID.randomUUID(),
				challenge: new Challenge(name:"testChallenge"),
				challengeReferral: new ChallengeReferral(),
				description:"test description",
				objectStatus:ObjectStatus.ACTIVE,
				person: testStudent,
				createdBy: testStudent);
		all << new Task(id:UUID.randomUUID(),
				challenge: new Challenge(name:"testChallenge2"),
				challengeReferral: new ChallengeReferral(),
				description:"test description",
				objectStatus:ObjectStatus.ACTIVE,
				person: testStudent,
				createdBy: testStudent);
		all << new Task(id:UUID.randomUUID(),
				description:"test description",
				objectStatus:ObjectStatus.ACTIVE,
				person: testStudent,
				createdBy: testStudent);

		SspUser requestor = new SspUser("username", "password",
				true, true,true,true, new ArrayList<GrantedAuthority>())

		Map<String, List<Task>> grouped = service.getAllGroupedByTaskGroup(testStudent, requestor, null);
		assertEquals(2,grouped["testChallenge"].size());
		assertEquals(1,grouped["testChallenge2"].size());
		assertEquals(1,grouped[Task.CUSTOM_GROUP_NAME].size());
	}
}