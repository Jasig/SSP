package org.jasig.ssp.service.reference.impl;

import java.util.List;

import org.jasig.ssp.dao.reference.ChallengeChallengeReferralDao;
import org.jasig.ssp.dao.reference.ChallengeDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeChallengeReferral;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ChallengeServiceImpl extends AbstractReferenceService<Challenge>
		implements ChallengeService {

	@Autowired
	transient private ChallengeDao dao;

	@Autowired
	private transient ChallengeReferralService challengeReferralService;

	@Autowired
	private transient ChallengeChallengeReferralDao challengeChallengeReferralDao;

	@Autowired
	private transient SecurityService securityService;

	@Override
	public List<Challenge> challengeSearch(final String query) {
		final List<Challenge> challenges = dao.searchByQuery(query);
		final List<Challenge> results = Lists.newArrayList();

		for (final Challenge challenge : challenges) {
			final long count = challengeReferralService
					.countByChallengeIdNotOnActiveTaskList(challenge,
							securityService.currentUser().getPerson(),
							securityService.getSessionId());
			if (count > 0) {
				results.add(challenge);
			}
		}

		return results;
	}

	@Override
	public PagingWrapper<Challenge> getAllForCategory(
			final Category category,
			final SortingAndPaging sAndP) {
		return dao.getAllForCategory(category.getId(), sAndP);
	}

	@Override
	public PagingWrapper<Challenge> getAllForPerson(
			final Person person,
			final SortingAndPaging sAndP) {
		return dao.getAllForPerson(person.getId(), sAndP);
	}

	@Override
	public PagingWrapper<Challenge> getAllForIntake(final SortingAndPaging sAndP) {
		return dao.getAllInStudentIntake(sAndP);
	}

	@Override
	protected ChallengeDao getDao() {
		return dao;
	}

	protected void setDao(final ChallengeDao dao) {
		this.dao = dao;
	}

	@Override
	public ChallengeChallengeReferral addChallengeReferralToChallenge(
			final ChallengeReferral referral, final Challenge challenge) {
		// get current referrals for challenge
		final PagingWrapper<ChallengeChallengeReferral> challengeReferralChallenges = challengeChallengeReferralDao
				.getAllforChallengeReferralAndChallenge(referral.getId(),
						challenge.getId(), new SortingAndPaging(
								ObjectStatus.ACTIVE));

		ChallengeChallengeReferral challengeReferral = null;
		// if this challengeReferral is already there and ACTIVE, ignore
		if (challengeReferralChallenges.getResults() < 1) {
			challengeReferral = new ChallengeChallengeReferral();
			challengeReferral.setChallenge(challenge);
			challengeReferral.setChallengeReferral(referral);
			challengeReferral.setObjectStatus(ObjectStatus.ACTIVE);

			challengeReferral = challengeChallengeReferralDao
					.save(challengeReferral);
		}

		return challengeReferral;
	}

	@Override
	public ChallengeChallengeReferral removeChallengeReferralFromChallenge(
			final ChallengeReferral referral, final Challenge challenge) {
		// get current referrals for challenge
		final PagingWrapper<ChallengeChallengeReferral> challengeReferralChallenges = challengeChallengeReferralDao
				.getAllforChallengeReferralAndChallenge(referral.getId(),
						challenge.getId(), new SortingAndPaging(
								ObjectStatus.ACTIVE));

		ChallengeChallengeReferral challengeReferral = null;
		// if this challenge referral is already there and ACTIVE, delete
		if (challengeReferralChallenges.getResults() > 0) {
			for (ChallengeChallengeReferral item : challengeReferralChallenges
					.getRows()) {
				item.setObjectStatus(ObjectStatus.DELETED);

				// we'll just return the last one
				challengeReferral = challengeChallengeReferralDao.save(item);
			}
		}

		return challengeReferral;
	}
}
