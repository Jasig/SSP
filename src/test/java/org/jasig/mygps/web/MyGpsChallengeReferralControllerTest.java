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
package org.jasig.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class MyGpsChallengeReferralControllerTest {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(MyGpsChallengeReferralControllerTest.class);

	private MyGpsChallengeReferralController controller;

	private ChallengeReferralService challengeReferralService;
	private ChallengeReferralTOFactory challengeReferralTOFactory;
	private ChallengeService challengeService;

	@Before
	public void setup() {
		challengeReferralService = createMock(ChallengeReferralService.class);
		challengeService = createMock(ChallengeService.class);
		challengeReferralTOFactory = createMock(ChallengeReferralTOFactory.class);

		controller = new MyGpsChallengeReferralController(
				challengeReferralService, challengeService,
				challengeReferralTOFactory);
	}

	@Test
	public void getByChallengeId() throws ObjectNotFoundException {
		Challenge challenge = new Challenge(UUID.randomUUID());
		List<ChallengeReferral> searchResults = Lists.newArrayList();
		List<ChallengeReferralTO> searchResultsTO = Lists.newArrayList();
		expect(challengeService.get(challenge.getId())).andReturn(challenge);
		expect(
				challengeReferralService
						.getChallengeReferralsByChallengeId(challenge))
				.andReturn(searchResults);
		expect(challengeReferralTOFactory.asTOList(searchResults)).andReturn(
				searchResultsTO);

		replay(challengeReferralService);
		replay(challengeReferralTOFactory);
		replay(challengeService);

		try {
			List<ChallengeReferralTO> response = controller
					.getByChallengeId(challenge.getId());

			verify(challengeReferralService);
			verify(challengeReferralTOFactory);
			verify(challengeService);
			assertEquals(searchResultsTO, response);
		} catch (Exception e) {
			fail("controller error");
		}
	}

	@Test
	public void search() throws ObjectNotFoundException {
		Challenge challenge = new Challenge(UUID.randomUUID());
		List<ChallengeReferral> searchResults = Lists.newArrayList();
		List<ChallengeReferralTO> searchResultsTO = Lists.newArrayList();
		expect(challengeService.get(challenge.getId())).andReturn(challenge);
		expect(challengeReferralService.challengeReferralSearch(challenge, false))
				.andReturn(searchResults);
		expect(challengeReferralTOFactory.asTOList(searchResults)).andReturn(
				searchResultsTO);

		replay(challengeReferralService);
		replay(challengeReferralTOFactory);
		replay(challengeService);

		try {
			List<ChallengeReferralTO> response = controller.search("",
					challenge.getId());

			verify(challengeReferralService);
			verify(challengeReferralTOFactory);
			verify(challengeService);
			assertEquals(searchResultsTO, response);
		} catch (Exception e) {
			fail("controller error");
		}
	}
}
