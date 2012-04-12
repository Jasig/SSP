package org.studentsuccessplan.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

import com.google.common.collect.Lists;

public class MyGpsChallengeControllerTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsChallengeControllerTest.class);

	private MyGpsChallengeController controller;
	private ChallengeService manager;

	@Before
	public void setup() {
		manager = createMock(ChallengeService.class);
		controller = new MyGpsChallengeController(manager);
	}

	@Test
	public void search() {
		String query = "ABCDEFG";
		List<Challenge> searchResults = Lists.newArrayList();
		List<ChallengeTO> searchResultsTO = Lists.newArrayList();
		expect(manager.challengeSearch(query)).andReturn(searchResults);

		replay(manager);

		try {
			List<ChallengeTO> response = controller.search(query);

			verify(manager);
			assertEquals(searchResultsTO, response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}
}
