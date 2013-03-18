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

import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.dao.reference.ChallengeReferralDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TaskService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChallengeReferralServiceImpl extends
		AbstractReferenceService<ChallengeReferral>
		implements ChallengeReferralService {

	@Autowired
	transient private ChallengeReferralDao dao;

	@Autowired
	private transient TaskService taskService;

	@Autowired
	private transient SecurityService securityService;

	@Override
	public List<ChallengeReferral> getChallengeReferralsByChallengeId(
			final Challenge challenge) {
		return dao.byChallengeId(challenge.getId());
	}

	@Override
	public PagingWrapper<ChallengeReferral> getAllForChallenge(
			final Challenge challenge, final SortingAndPaging sAndP) {
		return dao.getAllForChallenge(challenge.getId(), sAndP);
	}

	@Override
	public List<ChallengeReferral> challengeReferralSearch(
			final Challenge challenge) {
		return dao.byChallengeIdNotOnActiveTaskList(challenge.getId(),
				securityService.currentUser().getPerson(),
				securityService.getSessionId());
	}

	@Override
	public List<ChallengeReferral> getChallengeReferralCountByChallengeAndQuery(
			final Challenge challenge, final String query,
			final SortingAndPaging sAndP) {

		int count = 0;

		List<ChallengeReferral> referrals = new ArrayList<ChallengeReferral>();
		
		for (final ChallengeReferral challengeReferral : dao
				.byChallengeIdAndQuery(
						challenge.getId(), query)) {

			// Does the referral exist as an active/incomplete task?
			// Need to check both the tasks created w/in MyGPS as well as those
			// created in SSP.

			boolean isEmpty = false;

			if (securityService.currentlyAuthenticatedUser() != null ) {
				final Person student = securityService.currentUser()
						.getPerson();
				isEmpty = taskService.getAllForPersonAndChallengeReferral(
						student, false, challengeReferral,
						securityService.currentUser(),
						sAndP).isEmpty();
			} else {
				isEmpty = taskService.getAllForSessionIdAndChallengeReferral(
						securityService.getSessionId(), false,
						challengeReferral, sAndP).isEmpty();
			}

			if (isEmpty) {
				referrals.add(challengeReferral);
			}
		}

		return referrals;
	}

	@Override
	public long countByChallengeIdNotOnActiveTaskList(
			final Challenge challenge,
			final Person student, final String sessionId) {
		return dao.countByChallengeIdNotOnActiveTaskList(challenge.getId(),
				student, sessionId);
	}

	@Override
	public List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			final Challenge challenge, final Person student,
			final String sessionId) {
		return dao.byChallengeIdNotOnActiveTaskList(challenge.getId(), student,
				sessionId);
	}

	@Override
	protected ChallengeReferralDao getDao() {
		return dao;
	}

	protected void setDao(final ChallengeReferralDao dao) {
		this.dao = dao;
	}
}