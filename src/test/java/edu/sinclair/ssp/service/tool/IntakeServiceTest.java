package edu.sinclair.ssp.service.tool;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.ChallengeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class IntakeServiceTest {

	// private static final Logger logger =
	// LoggerFactory.getLogger(StudentIntakeServiceTest.class);

	@Autowired
	private IntakeService service;

	private ChallengeService challengeService;

	// private FundingSourceService fundingSourceService;
	// private VeteranStatusService veteranStatusService;

	@Before
	public void setup() {
		challengeService = createMock(ChallengeService.class);
		// fundingSourceService = createMock(FundingSourceService.class);
		// veteranStatusService = createMock(VeteranStatusService.class);

		// service.setChallengeService(challengeService);
		// service.setFundingSourceService(fundingSourceService);
		// service.setVeteranStatusService(veteranStatusService);
	}

	@Ignore
	@Test
	public void testReferenceData() {
		expect(challengeService.getAll(ObjectStatus.ACTIVE)).andReturn(
				new ArrayList<Challenge>());
		// expect(fundingSourceService.getAll()).andReturn(new
		// ArrayList<FundingSource>());
		// expect(veteranStatusService.getAll()).andReturn(new
		// ArrayList<VeteranStatus>());

		replay(challengeService);
		// replay(fundingSourceService);
		// replay(veteranStatusService);

		// Map<String, Object> refData = service.referenceData();

		// assertNotNull(refData);
		// assertTrue(refData.containsKey(StudentIntakeService.REF_TITLE_CHALLENGES));
		// assertTrue(refData.containsKey(StudentIntakeService.REF_TITLE_FUNDING_SOURCES));
		// assertTrue(refData.containsKey(StudentIntakeService.REF_TITLE_VETERAN_STATUSES));

		verify(challengeService);
		// verify(fundingSourceService);
		// verify(veteranStatusService);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testIntakeServiceFormObjectNotFoundException()
			throws ObjectNotFoundException {
		service.loadForPerson(UUID.randomUUID());
	}

	@Test
	public void testIntakeServiceFromLoadForPersonFromDatabaseForAdminUser()
			throws ObjectNotFoundException {
		IntakeForm form = service.loadForPerson(Person.SYSTEM_ADMINISTRATOR_ID);
		assertNotNull("Admin user could not be loaded.", form.getPerson());
		assertNotNull("Admin user loaded but was missing the Person instance.",
				form.getPerson());
		assertEquals("Admin user loaded but identifiers did not match.",
				Person.SYSTEM_ADMINISTRATOR_ID, form.getPerson().getId());

		assertNull(
				"Admin user loaded but included a PersonDemographics instance even though it should not have.",
				form.getPersonDemographics());
	}
}
