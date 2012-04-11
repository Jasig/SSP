package org.studentsuccessplan.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studentsuccessplan.mygps.business.SelfHelpGuideManager;
import org.studentsuccessplan.mygps.model.transferobject.ChallengeReferralTO;

public class MyGpsChallengeReferralControllerTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsChallengeReferralControllerTest.class);

	private MyGpsChallengeReferralController controller;
	private SelfHelpGuideManager manager;

	@Before
	public void setup() {
		manager = createMock(SelfHelpGuideManager.class);

		controller = new MyGpsChallengeReferralController();
		controller.setManager(manager);
	}

	@Test
	public void getByChallengeId() {
		UUID challengeId = UUID.randomUUID();
		List<ChallengeReferralTO> searchResults = new ArrayList<ChallengeReferralTO>();
		expect(manager.getChallengeReferralsByChallengeId(challengeId))
				.andReturn(searchResults);

		replay(manager);

		try {
			List<ChallengeReferralTO> response = controller
					.getByChallengeId(challengeId);

			verify(manager);
			assertEquals(searchResults, response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void search() {
		UUID challengeId = UUID.randomUUID();
		List<ChallengeReferralTO> searchResults = new ArrayList<ChallengeReferralTO>();
		expect(manager.challengeReferralSearch(challengeId))
				.andReturn(searchResults);

		replay(manager);

		try {
			List<ChallengeReferralTO> response = controller
					.search("", challengeId);

			verify(manager);
			assertEquals(searchResults, response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}
}
