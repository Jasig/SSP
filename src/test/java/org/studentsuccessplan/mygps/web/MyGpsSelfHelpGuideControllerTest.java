package org.studentsuccessplan.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.studentsuccessplan.mygps.business.SelfHelpGuideManager;
import org.studentsuccessplan.mygps.model.transferobject.SelfHelpGuideContentTO;
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideTO;

public class MyGpsSelfHelpGuideControllerTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuideControllerTest.class);

	private MyGpsSelfHelpGuideController controller;
	private SelfHelpGuideManager manager;

	@Before
	public void setup() {
		manager = createMock(SelfHelpGuideManager.class);

		controller = new MyGpsSelfHelpGuideController();
		controller.setSelfHelpGuideManager(manager);
	}

	@Test
	public void getAll() {
		List<SelfHelpGuide> guides = new ArrayList<SelfHelpGuide>();
		guides.add(new SelfHelpGuide());
		expect(manager.getAll()).andReturn(guides);

		replay(manager);

		try {
			List<SelfHelpGuideTO> results = controller.getAll();

			verify(manager);
			assertNotNull(results);
			assert (results.size() > 0);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}

	}

	@Test
	public void getContentById() {
		SelfHelpGuideContentTO contentTO = new SelfHelpGuideContentTO();
		UUID selfHelpGuideId = UUID
				.fromString("7CDD9ECE-C479-4AD6-4A1D-1BB3CDD4DDE4");

		expect(manager.getContentById(selfHelpGuideId)).andReturn(contentTO);

		replay(manager);

		try {
			SelfHelpGuideContentTO content = controller
					.getContentById(selfHelpGuideId);
			assertNotNull(content);
			verify(manager);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void getBySelfHelpGuideGroup() {
		List<SelfHelpGuide> guides = new ArrayList<SelfHelpGuide>();
		guides.add(new SelfHelpGuide());

		UUID selfHelpGuideGroupId = UUID
				.fromString("7CDD9ECE-C479-4AD6-4A1D-1BB3CDD4DDE4");

		expect(manager.getBySelfHelpGuideGroup(selfHelpGuideGroupId))
				.andReturn(guides);

		replay(manager);

		try {
			List<SelfHelpGuideTO> results = controller
					.getBySelfHelpGuideGroup(selfHelpGuideGroupId);

			verify(manager);
			assertNotNull(results);
			assert (results.size() > 0);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}

	}
}
