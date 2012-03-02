package edu.sinclair.ssp.web.api.tool;

import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import edu.sinclair.ssp.factory.reference.ChallengeTOFactory;
import edu.sinclair.ssp.factory.tool.StudentIntakeTOFactory;
import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.service.reference.FundingSourceService;
import edu.sinclair.ssp.service.reference.VeteranStatusService;
import edu.sinclair.ssp.service.tool.StudentIntakeService;
import edu.sinclair.ssp.transferobject.tool.StudentIntakeFormTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/tool/studentIntake")
public class StudentIntakeController {

	private static final Logger logger = LoggerFactory.getLogger(StudentIntakeController.class);

	@Autowired
	private StudentIntakeService service;

	@Autowired
	private StudentIntakeTOFactory toFactory;

	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private ChallengeTOFactory challengeTOFactory;

	@Autowired
	private FundingSourceService fundingSourceService;

	@Autowired
	private VeteranStatusService veteranStatusService;

	public static final String REF_TITLE_CHALLENGES = "challenges";
	public static final String REF_TITLE_FUNDING_SOURCES = "fundingSources";
	public static final String REF_TITLE_VETERAN_STATUSES = "veteranStatuses";

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> create() throws Exception {
		Map<String, Object> referenceData =  Maps.newHashMap();
		
		referenceData.put(REF_TITLE_CHALLENGES, challengeTOFactory.toTOList(challengeService.getAll()));
		referenceData.put(REF_TITLE_FUNDING_SOURCES, fundingSourceService.getAll());
		referenceData.put(REF_TITLE_VETERAN_STATUSES, veteranStatusService.getAll());
		
		return referenceData;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody boolean save(@PathVariable UUID studentId, @Valid @RequestBody StudentIntakeFormTO obj) throws Exception {
		return service.save(toFactory.toModel(obj));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody StudentIntakeFormTO load(@PathVariable UUID studentId){
		return toFactory.toTO(service.loadForStudent(studentId));
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody Exception handle(Exception e){
		logger.error("Error: ", e);
		return e;
	}
}
