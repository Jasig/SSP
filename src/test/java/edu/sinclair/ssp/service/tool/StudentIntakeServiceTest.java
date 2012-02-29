package edu.sinclair.ssp.service.tool;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.service.reference.FundingSourceService;
import edu.sinclair.ssp.service.reference.VeteranStatusService;

public class StudentIntakeServiceTest {

	//private static final Logger logger = LoggerFactory.getLogger(StudentIntakeServiceTest.class);
	
	private StudentIntakeService service;
	private ChallengeService challengeService;
	private FundingSourceService fundingSourceService;
	private VeteranStatusService veteranStatusService;
	
	@Before
	public void setup() {
		service = new StudentIntakeService();
		
		challengeService = createMock(ChallengeService.class);
		fundingSourceService = createMock(FundingSourceService.class);
		veteranStatusService = createMock(VeteranStatusService.class);
		
		service.setChallengeService(challengeService);
		service.setFundingSourceService(fundingSourceService);
		service.setVeteranStatusService(veteranStatusService);
	}
	
	@Test
	public void testReferenceData() {
		expect(challengeService.getAll()).andReturn(new ArrayList<Challenge>());
		expect(fundingSourceService.getAll()).andReturn(new ArrayList<FundingSource>());
		expect(veteranStatusService.getAll()).andReturn(new ArrayList<VeteranStatus>());
		
		replay(challengeService);
		replay(fundingSourceService);
		replay(veteranStatusService);
		
		Map<String, Object> refData = service.referenceData();
		
		assertNotNull(refData);
		assertTrue(refData.containsKey(StudentIntakeService.REF_TITLE_CHALLENGES));
		assertTrue(refData.containsKey(StudentIntakeService.REF_TITLE_FUNDING_SOURCES));
		assertTrue(refData.containsKey(StudentIntakeService.REF_TITLE_VETERAN_STATUSES));
		
		verify(challengeService);
		verify(fundingSourceService);
		verify(veteranStatusService);
	}

}
