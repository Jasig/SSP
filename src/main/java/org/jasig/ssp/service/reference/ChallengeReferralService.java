package org.jasig.ssp.service.reference;

import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * ChallengeReferral service
 */
public interface ChallengeReferralService extends
		ReferenceService<ChallengeReferral> {

	List<ChallengeReferral> getChallengeReferralsByChallengeId(
			Challenge challenge);

	PagingWrapper<ChallengeReferral> getAllForChallenge(Challenge challenge,
			SortingAndPaging sAndP);

	List<ChallengeReferral> challengeReferralSearch(Challenge challenge);

	int getChallengeReferralCountByChallengeAndQuery(Challenge challenge,
			String query, SortingAndPaging sAndP);

	long countByChallengeIdNotOnActiveTaskList(Challenge challenge,
			Person student, String sessionId);

	List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			Challenge challenge, Person student, String sessionId);
}