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
package org.jasig.ssp.service.reference.impl;

import java.util.List;

import org.jasig.ssp.dao.reference.ChallengeChallengeReferralDao;
import org.jasig.ssp.dao.reference.ChallengeDao;
import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeChallengeReferral;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.reference.ChallengeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	@Autowired
	private transient ChallengeReferralTOFactory challengeReferralTOFactory;

	@Autowired
	private transient ChallengeTOFactory challengeTOFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeServiceImpl.class);
	
	@Override
	public List<Challenge> challengeSearch(final String query) {
		final List<Challenge> challenges = dao.searchByQuery(query);
		final List<Challenge> results = Lists.newArrayList();

		if (challenges != null) {
			for (final Challenge challenge : challenges) {
				final long count = challengeReferralService
						.countByChallengeIdNotOnActiveTaskList(challenge,
								securityService.currentUser().getPerson(),
								securityService.getSessionId());
				if (count > 0) {
					results.add(challenge);
				}
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
			for (final ChallengeChallengeReferral item : challengeReferralChallenges
					.getRows()) {
				item.setObjectStatus(ObjectStatus.INACTIVE);

				// we'll just return the last one
				challengeReferral = challengeChallengeReferralDao.save(item);
			}
		}

		return challengeReferral;
	}
	
	@Override
	public List<ChallengeTO> search(final String query)
			throws Exception {
		try {
			final List<Challenge> challenges = challengeSearch(query);

			final List<ChallengeTO> challengeTOs = Lists.newArrayList();

			for (Challenge challenge : challenges) {
				ChallengeTO challengeTO = challengeTOFactory.from(challenge);

				List<ChallengeReferral> referrals = challengeReferralService
						.byChallengeIdNotOnActiveTaskList(challenge,
								securityService.currentUser().getPerson(),
								securityService.getSessionId());
				challengeTO
						.setChallengeChallengeReferrals(challengeReferralTOFactory
								.asTOList(referrals));
				challengeTOs.add(challengeTO);
			}


			return challengeTOs;
		} catch (Exception e) {
			LOGGER.error("ERROR : search() : {}", e.getMessage(), e);
			throw e;
		}
	}
}
