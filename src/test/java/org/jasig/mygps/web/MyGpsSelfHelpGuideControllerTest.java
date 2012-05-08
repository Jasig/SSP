package org.jasig.mygps.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.reference.SelfHelpGuideTOFactory;
import org.jasig.ssp.model.reference.SelfHelpGuide;
import org.jasig.ssp.model.reference.SelfHelpGuideGroup;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.SelfHelpGuideGroupService;
import org.jasig.ssp.service.reference.SelfHelpGuideService;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideDetailTO;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class MyGpsSelfHelpGuideControllerTest {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(MyGpsSelfHelpGuideControllerTest.class);

	private transient MyGpsSelfHelpGuideController controller;

	private transient SelfHelpGuideService selfHelpGuideService;

	private transient SelfHelpGuideGroupService selfHelpGuideGroupService;

	private transient SelfHelpGuideTOFactory selfHelpGuideTOFactory;

	@Before
	public void setup() {
		selfHelpGuideService = createMock(SelfHelpGuideService.class);
		selfHelpGuideGroupService = createMock(SelfHelpGuideGroupService.class);
		selfHelpGuideTOFactory = createMock(SelfHelpGuideTOFactory.class);

		controller = new MyGpsSelfHelpGuideController(
				selfHelpGuideService, selfHelpGuideGroupService,
				selfHelpGuideTOFactory);
	}

	@Test
	public void getAll() {
		List<SelfHelpGuide> guides = Lists.newArrayList();
		guides.add(new SelfHelpGuide());

		List<SelfHelpGuideTO> guideTOs = Lists.newArrayList();
		expect(selfHelpGuideService.getAll(isA(SortingAndPaging.class)))
				.andReturn(new PagingWrapper<SelfHelpGuide>(guides));

		expect(selfHelpGuideTOFactory.asTOList(guides)).andReturn(guideTOs);

		replay(selfHelpGuideService);
		replay(selfHelpGuideGroupService);
		replay(selfHelpGuideTOFactory);

		try {
			List<SelfHelpGuideTO> results = controller.getAll();

			verify(selfHelpGuideService);
			verify(selfHelpGuideGroupService);
			verify(selfHelpGuideTOFactory);

			assertNotNull(results);
		} catch (Exception e) {
			fail("controller error");
		}
	}

	@Test
	public void getContentById() throws ObjectNotFoundException {
		SelfHelpGuide guide = new SelfHelpGuide();
		UUID selfHelpGuideId = UUID
				.fromString("7CDD9ECE-C479-4AD6-4A1D-1BB3CDD4DDE4");

		expect(selfHelpGuideService.get(selfHelpGuideId)).andReturn(guide);

		replay(selfHelpGuideService);
		replay(selfHelpGuideGroupService);

		try {
			SelfHelpGuideDetailTO content = controller
					.getContentById(selfHelpGuideId);
			assertNotNull(content);

			verify(selfHelpGuideService);
			verify(selfHelpGuideGroupService);

		} catch (Exception e) {
			fail("controller error");
		}
	}

	@Test
	public void getBySelfHelpGuideGroup() throws ObjectNotFoundException {
		SelfHelpGuideGroup group = new SelfHelpGuideGroup(UUID.randomUUID());
		List<SelfHelpGuide> guides = new ArrayList<SelfHelpGuide>();
		guides.add(new SelfHelpGuide());
		List<SelfHelpGuideTO> guideTOs = Lists.newArrayList();

		expect(selfHelpGuideGroupService.get(group.getId())).andReturn(group);
		expect(selfHelpGuideService.getBySelfHelpGuideGroup(group)).andReturn(
				guides);
		expect(selfHelpGuideTOFactory.asTOList(guides)).andReturn(guideTOs);

		replay(selfHelpGuideService);
		replay(selfHelpGuideGroupService);
		replay(selfHelpGuideTOFactory);

		try {
			List<SelfHelpGuideTO> results = controller
					.getBySelfHelpGuideGroup(group.getId());

			verify(selfHelpGuideService);
			verify(selfHelpGuideGroupService);
			verify(selfHelpGuideTOFactory);

			assertNotNull(results);
		} catch (Exception e) {
			fail("controller error");
		}

	}
}
