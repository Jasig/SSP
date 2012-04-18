package org.studentsuccessplan.ssp.service;

import java.util.List;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.Task;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface TaskService extends AbstractTaskService<Task> {

	List<Task> getAllForPersonAndChallengeReferral(Person person,
			boolean complete, ChallengeReferral challengeReferral,
			SortingAndPaging sAndP);

	List<Task> getAllForSessionIdAndChallengeReferral(
			String sessionId, boolean complete,
			ChallengeReferral challengeReferral, SortingAndPaging sAndP);
}
