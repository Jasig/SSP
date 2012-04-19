package org.studentsuccessplan.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
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
import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideGroupService;
import org.studentsuccessplan.ssp.service.reference.SelfHelpGuideService;
import org.studentsuccessplan.ssp.transferobject.reference.SelfHelpGuideTO;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public class MyGpsSelfHelpGuideControllerTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsSelfHelpGuideControllerTest.class);

	private MyGpsSelfHelpGuideController controller;
	private SelfHelpGuideManager manager;
	private SelfHelpGuideService selfHelpGuideService;
	private SelfHelpGuideGroupService selfHelpGuideGroupService;

	@Before
	public void setup() {
		manager = createMock(SelfHelpGuideManager.class);
		selfHelpGuideService = createMock(SelfHelpGuideService.class);
		selfHelpGuideGroupService = createMock(SelfHelpGuideGroupService.class);

		controller = new MyGpsSelfHelpGuideController(manager,
				selfHelpGuideService, selfHelpGuideGroupService);
	}

	@Test
	public void getAll() {
		List<SelfHelpGuide> guides = new ArrayList<SelfHelpGuide>();
		guides.add(new SelfHelpGuide());
		expect(
				selfHelpGuideService.getAll(isA(SortingAndPaging.class)))
				.andReturn(guides);

		replay(manager);
		replay(selfHelpGuideService);
		replay(selfHelpGuideGroupService);

		try {
			List<SelfHelpGuideTO> results = controller.getAll();

			verify(manager);
			verify(selfHelpGuideService);
			verify(selfHelpGuideGroupService);

			assertNotNull(results);
			assert (results.size() > 0);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}

	}

	@Test
	public void getContentById() throws ObjectNotFoundException {
		SelfHelpGuideContentTO contentTO = new SelfHelpGuideContentTO();
		UUID selfHelpGuideId = UUID
				.fromString("7CDD9ECE-C479-4AD6-4A1D-1BB3CDD4DDE4");

		expect(manager.getContentById(selfHelpGuideId)).andReturn(contentTO);

		replay(manager);
		replay(selfHelpGuideService);
		replay(selfHelpGuideGroupService);

		try {
			SelfHelpGuideContentTO content = controller
					.getContentById(selfHelpGuideId);
			assertNotNull(content);

			verify(manager);
			verify(selfHelpGuideService);
			verify(selfHelpGuideGroupService);

		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}
	}

	@Test
	public void getBySelfHelpGuideGroup() throws ObjectNotFoundException {
		SelfHelpGuideGroup group = new SelfHelpGuideGroup(UUID.randomUUID());
		List<SelfHelpGuide> guides = new ArrayList<SelfHelpGuide>();
		guides.add(new SelfHelpGuide());

		expect(selfHelpGuideGroupService.get(group.getId())).andReturn(group);
		expect(selfHelpGuideService.getBySelfHelpGuideGroup(group)).andReturn(
				guides);

		replay(manager);
		replay(selfHelpGuideService);
		replay(selfHelpGuideGroupService);

		try {
			List<SelfHelpGuideTO> results = controller
					.getBySelfHelpGuideGroup(group.getId());

			verify(manager);
			verify(selfHelpGuideService);
			verify(selfHelpGuideGroupService);

			assertNotNull(results);
			assert (results.size() > 0);
		} catch (Exception e) {
			LOGGER.error("controller error", e);
		}

	}
}
