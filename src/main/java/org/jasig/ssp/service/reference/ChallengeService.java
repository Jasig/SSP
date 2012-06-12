package org.jasig.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeChallengeReferral;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

public interface ChallengeService extends AuditableCrudService<Challenge> {

	@Override
	PagingWrapper<Challenge> getAll(SortingAndPaging sAndP);

	@Override
	Challenge get(UUID id) throws ObjectNotFoundException;

	@Override
	Challenge create(Challenge obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	Challenge save(Challenge obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	List<Challenge> challengeSearch(String query);

	PagingWrapper<Challenge> getAllForCategory(Category category,
			SortingAndPaging sAndP);

	PagingWrapper<Challenge> getAllForPerson(
			final Person person, final SortingAndPaging sAndP);

	ChallengeChallengeReferral addChallengeReferralToChallenge(
			ChallengeReferral referral, Challenge challenge);

	ChallengeChallengeReferral removeChallengeReferralFromChallenge(
			ChallengeReferral referral, Challenge challenge);
}