package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Person;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface ChallengeReferralService extends
		AuditableCrudService<ChallengeReferral> {

	@Override
	List<ChallengeReferral> getAll(SortingAndPaging sAndP);

	@Override
	ChallengeReferral get(UUID id) throws ObjectNotFoundException;

	@Override
	ChallengeReferral create(ChallengeReferral obj);

	@Override
	ChallengeReferral save(ChallengeReferral obj)
			throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	List<ChallengeReferral> getChallengeReferralsByChallengeId(
			Challenge challenge);

	List<ChallengeReferral> challengeReferralSearch(Challenge challenge);

	int getChallengeReferralCountByChallengeAndQuery(Challenge challenge,
			String query, SortingAndPaging sAndP);

	int countByChallengeIdNotOnActiveTaskList(Challenge challenge,
			Person student, String sessionId);

	List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			Challenge challenge, Person student, String sessionId);
}
