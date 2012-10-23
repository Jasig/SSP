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
package org.jasig.ssp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.dao.SelfHelpGuideQuestionResponseDao;
import org.jasig.ssp.dao.SelfHelpGuideResponseDao;
import org.jasig.ssp.dao.reference.ChallengeDao;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SelfHelpGuideQuestionResponse;
import org.jasig.ssp.model.SelfHelpGuideResponse;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.service.AbstractPersonAssocAuditableService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonSelfHelpGuideResponseService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.transferobject.SelfHelpGuideResponseTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonSelfHelpGuideResponseServiceImpl
		extends AbstractPersonAssocAuditableService<SelfHelpGuideResponse>
		implements PersonSelfHelpGuideResponseService {

	@Autowired
	private transient SelfHelpGuideResponseDao dao;

	@Autowired
	private transient SelfHelpGuideQuestionResponseDao selfHelpGuideQuestionResponseDao;

	@Autowired
	private transient ChallengeDao challengeDao;

	@Autowired
	private transient ChallengeReferralService challengeReferralService;

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(PersonSelfHelpGuideResponseServiceImpl.class);

	@Override
	public PagingWrapper<SelfHelpGuideResponse> getAllForPerson(
			final Person person,
			final SortingAndPaging sAndP) {
		return dao.getAllForPersonId(person.getId(), sAndP);
	}

	@Override
	public SelfHelpGuideResponse save(final SelfHelpGuideResponse obj)
			throws ObjectNotFoundException {
		return getDao().save(obj);
	}

	@Override
	protected SelfHelpGuideResponseDao getDao() {
		return dao;
	}

	@Override
	@Transactional(readOnly = false)
	public SelfHelpGuideResponse initiateSelfHelpGuideResponse(
			final SelfHelpGuide selfHelpGuide,
			final Person person)
			throws ObjectNotFoundException {
		final SelfHelpGuideResponse response =
				SelfHelpGuideResponse.createDefaultForSelfHelpGuideAndPerson(
						selfHelpGuide, person);

		dao.save(response);

		return response;
	}

	@Override
	public boolean answerSelfHelpGuideQuestion(
			final SelfHelpGuideResponse selfHelpGuideResponse,
			final SelfHelpGuideQuestion selfHelpGuideQuestion,
			final Boolean response)
			throws ObjectNotFoundException {

		final SelfHelpGuideQuestionResponse selfHelpGuideQuestionResponse = new SelfHelpGuideQuestionResponse();
		selfHelpGuideQuestionResponse.setEarlyAlertSent(false);
		selfHelpGuideQuestionResponse.setResponse(response);
		selfHelpGuideQuestionResponse
				.setSelfHelpGuideQuestion(selfHelpGuideQuestion);
		selfHelpGuideQuestionResponse
				.setSelfHelpGuideResponse(selfHelpGuideResponse);

		selfHelpGuideQuestionResponseDao.save(selfHelpGuideQuestionResponse);

		return true;
	}

	@Override
	public boolean completeSelfHelpGuideResponse(
			final SelfHelpGuideResponse selfHelpGuideResponse)
			throws ObjectNotFoundException {
		selfHelpGuideResponse.setCompleted(true);
		return dao.save(selfHelpGuideResponse).isCompleted();
	}

	@Override
	public boolean cancelSelfHelpGuideResponse(
			final SelfHelpGuideResponse response)
			throws ObjectNotFoundException {
		response.setCancelled(true);
		dao.save(response);
		return true;
	}

	@Override
	public SelfHelpGuideResponseTO getSelfHelpGuideResponseFor(
			final SelfHelpGuideResponse response,
			final SortingAndPaging referralSAndP)
			throws ObjectNotFoundException {

		final SelfHelpGuideResponseTO responseTO = new SelfHelpGuideResponseTO(
				response);

		// Get identified challenges
		final List<ChallengeTO> challengeTOs = new ArrayList<ChallengeTO>();

		int count = 0;
		for (Challenge challenge : challengeDao
				.selectAffirmativeBySelfHelpGuideResponseId(response.getId())) {

			count = challengeReferralService
					.getChallengeReferralCountByChallengeAndQuery(challenge,
							"", referralSAndP);

			if (count > 0) {
				final ChallengeTO challengeTO = new ChallengeTO(); // NOPMD
				challengeTO.setDescription(challenge
						.getSelfHelpGuideDescription());
				challengeTO.setId(challenge.getId());
				challengeTO.setName(challenge.getName());

				challengeTOs.add(challengeTO);
			}
		}

		responseTO.setChallengesIdentified(challengeTOs);

		return responseTO;
	}

}
