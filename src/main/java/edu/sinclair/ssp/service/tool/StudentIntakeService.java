package edu.sinclair.ssp.service.tool;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.service.reference.FundingSourceService;
import edu.sinclair.ssp.service.reference.VeteranStatusService;

@Service
public class StudentIntakeService {

	//private static final Logger logger = LoggerFactory.getLogger(StudentIntakeService.class);

	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private FundingSourceService fundingSourceService;
	
	@Autowired
	private VeteranStatusService veteranStatusService;
	
	public static final String REF_TITLE_CHALLENGES = "challenges";
	public static final String REF_TITLE_FUNDING_SOURCES = "fundingSources";
	public static final String REF_TITLE_VETERAN_STATUSES = "veteranStatuses";
	
	public Map<String, Object> referenceData(){
		Map<String, Object> referenceData =  Maps.newHashMap();
		
		referenceData.put(REF_TITLE_CHALLENGES, challengeService.getAll());
		referenceData.put(REF_TITLE_FUNDING_SOURCES, fundingSourceService.getAll());
		referenceData.put(REF_TITLE_VETERAN_STATUSES, veteranStatusService.getAll());
		
		return referenceData;
	}

	protected void setChallengeService(ChallengeService challengeService) {
		this.challengeService = challengeService;
	}

	protected void setFundingSourceService(FundingSourceService fundingSourceService) {
		this.fundingSourceService = fundingSourceService;
	}

	protected void setVeteranStatusService(VeteranStatusService veteranStatusService) {
		this.veteranStatusService = veteranStatusService;
	}
	
}
