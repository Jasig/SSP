package org.studentsuccessplan.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studentsuccessplan.ssp.factory.reference.ChallengeReferralTOFactory;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChallengeReferral;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.ChallengeReferralService;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeReferralTO;

import com.google.common.collect.Lists;

public class MyGpsChallengeReferralControllerTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsChallengeReferralControllerTest.class);

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
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void search() throws ObjectNotFoundException {
		Challenge challenge = new Challenge(UUID.randomUUID());
		List<ChallengeReferral> searchResults = Lists.newArrayList();
		List<ChallengeReferralTO> searchResultsTO = Lists.newArrayList();
		expect(challengeService.get(challenge.getId())).andReturn(challenge);
		expect(challengeReferralService.challengeReferralSearch(challenge))
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
			LOGGER.error("controller error", e);
		}
	}
}
