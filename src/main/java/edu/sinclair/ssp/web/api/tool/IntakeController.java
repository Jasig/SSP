package edu.sinclair.ssp.web.api.tool;

import java.util.HashMap;
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

import edu.sinclair.ssp.factory.reference.ChallengeTOFactory;
import edu.sinclair.ssp.factory.tool.IntakeTOFactory;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.service.tool.IntakeService;
import edu.sinclair.ssp.transferobject.ServiceResponse;
import edu.sinclair.ssp.transferobject.tool.IntakeFormTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/tool/studentIntake")
public class IntakeController {

	private static final Logger logger = LoggerFactory.getLogger(IntakeController.class);

	@Autowired
	private IntakeService service;

	@Autowired
	private IntakeTOFactory toFactory;

	@Autowired
	private ChallengeTOFactory challengeToFactory;
	
	@Autowired
	private ChallengeService challengeService;
	
	
	@RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
	public @ResponseBody ServiceResponse save(@PathVariable UUID studentId, @Valid @RequestBody IntakeFormTO obj) throws Exception {
		return new ServiceResponse(service.save(toFactory.toModel(obj)));
	}
	
	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	public @ResponseBody IntakeFormTO load(@PathVariable UUID studentId) throws Exception{
		IntakeFormTO formTO =  toFactory.toTO(service.loadForPerson(studentId));
		
		Map<String, Object> refData = new HashMap<String, Object>();
		
		refData.put(
				"challenges", challengeToFactory.toTOList(
						challengeService.getAll(ObjectStatus.ACTIVE)));
		
		
		formTO.setReferenceData(refData);
		
		
		return formTO;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody ServiceResponse handle(Exception e){
		logger.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
