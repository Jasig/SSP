package org.jasig.ssp.service.reference.impl;

import java.util.List;

import org.jasig.ssp.dao.reference.ChallengeDao;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ChallengeServiceImpl extends AbstractReferenceService<Challenge>
		implements ChallengeService {

	public ChallengeServiceImpl() {
		super();
	}

	@Autowired
	transient private ChallengeDao dao;

	@Autowired
	private transient ChallengeReferralService challengeReferralService;

	@Autowired
	private transient SecurityService securityService;

	@Override
	public List<Challenge> challengeSearch(final String query) {
		final List<Challenge> challenges = dao.searchByQuery(query);
		final List<Challenge> results = Lists.newArrayList();

		for (Challenge challenge : challenges) {
			long count = challengeReferralService
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
	protected ChallengeDao getDao() {
		return dao;
	}

	protected void setDao(final ChallengeDao dao) {
		this.dao = dao;
	}
}
