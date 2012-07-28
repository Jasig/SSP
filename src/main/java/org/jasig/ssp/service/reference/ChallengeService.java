package org.jasig.ssp.service.reference;

import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeChallengeReferral;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ReferenceService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface ChallengeService extends ReferenceService<Challenge> {

	List<Challenge> challengeSearch(String query);

	PagingWrapper<Challenge> getAllForCategory(Category category,
			SortingAndPaging sAndP);

	PagingWrapper<Challenge> getAllForPerson(
			final Person person, final SortingAndPaging sAndP);

	ChallengeChallengeReferral addChallengeReferralToChallenge(
			ChallengeReferral referral, Challenge challenge);

	ChallengeChallengeReferral removeChallengeReferralFromChallenge(
			ChallengeReferral referral, Challenge challenge);

	PagingWrapper<Challenge> getAllForIntake(SortingAndPaging sAndP);
}