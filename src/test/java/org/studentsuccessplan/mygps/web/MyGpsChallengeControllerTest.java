package org.studentsuccessplan.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studentsuccessplan.mygps.business.SelfHelpGuideManager;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

public class MyGpsChallengeControllerTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsChallengeControllerTest.class);

	private MyGpsChallengeController controller;
	private SelfHelpGuideManager manager;

	@Before
	public void setup() {
		manager = createMock(SelfHelpGuideManager.class);

		controller = new MyGpsChallengeController();
		controller.setManager(manager);
	}

	@Test
	public void search() {
		String query = "ABCDEFG";
		List<ChallengeTO> searchResults = new ArrayList<ChallengeTO>();
		expect(manager.challengeSearch(query)).andReturn(searchResults);

		replay(manager);

		try {
			List<ChallengeTO> response = controller.search(query);

			verify(manager);
			assertEquals(searchResults, response);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}
}
