package org.jasig.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * ChallengeReferral service
 */
public interface ChallengeReferralService extends
		AuditableCrudService<ChallengeReferral> {

	@Override
	PagingWrapper<ChallengeReferral> getAll(SortingAndPaging sAndP);

	@Override
	ChallengeReferral get(UUID id) throws ObjectNotFoundException;

	@Override
	ChallengeReferral create(ChallengeReferral obj)
			throws ObjectNotFoundException, ValidationException;

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

	long countByChallengeIdNotOnActiveTaskList(Challenge challenge,
			Person student, String sessionId);

	List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			Challenge challenge, Person student, String sessionId);
}