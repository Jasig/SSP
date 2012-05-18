package org.jasig.ssp.service.reference.impl;

import java.util.List;

import org.jasig.ssp.dao.reference.ChallengeCategoryDao;
import org.jasig.ssp.dao.reference.ChallengeDao;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
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
	private transient ChallengeCategoryDao challengeCategoryDao;

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
	protected ChallengeDao getDao() {
		return dao;
	}

	protected void setDao(final ChallengeDao dao) {
		this.dao = dao;
	}
}
