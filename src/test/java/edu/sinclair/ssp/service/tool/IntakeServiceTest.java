package edu.sinclair.ssp.service.tool;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.impl.SecurityServiceInTestEnvironment;
import edu.sinclair.ssp.service.reference.ChallengeService;

public class IntakeServiceTest {

	// private static final Logger logger =
	// LoggerFactory.getLogger(StudentIntakeServiceTest.class);

	// private IntakeService service;

	private ChallengeService challengeService;

	// private PersonService personService;

	// private PersonDao personDao;

	// private FundingSourceService fundingSourceService;
	// private VeteranStatusService veteranStatusService;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setup() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));

		challengeService = createMock(ChallengeService.class);
		// fundingSourceService = createMock(FundingSourceService.class);
		// veteranStatusService = createMock(VeteranStatusService.class);

		// service.setChallengeService(challengeService);
		// service.setFundingSourceService(fundingSourceService);
		// service.setVeteranStatusService(veteranStatusService);
	}

	@Ignore
	@Test
	@Transactional
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
}
