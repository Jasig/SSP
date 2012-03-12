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

import edu.sinclair.ssp.factory.TransferObjectListFactory;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.model.reference.EmploymentShifts;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.model.reference.Genders;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.model.reference.States;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.service.tool.IntakeService;
import edu.sinclair.ssp.transferobject.ServiceResponse;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;
import edu.sinclair.ssp.transferobject.reference.ChildCareArrangementTO;
import edu.sinclair.ssp.transferobject.reference.CitizenshipTO;
import edu.sinclair.ssp.transferobject.reference.EducationGoalTO;
import edu.sinclair.ssp.transferobject.reference.EducationLevelTO;
import edu.sinclair.ssp.transferobject.reference.EthnicityTO;
import edu.sinclair.ssp.transferobject.reference.FundingSourceTO;
import edu.sinclair.ssp.transferobject.reference.MaritalStatusTO;
import edu.sinclair.ssp.transferobject.reference.StudentStatusTO;
import edu.sinclair.ssp.transferobject.reference.VeteranStatusTO;
import edu.sinclair.ssp.transferobject.tool.IntakeFormTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/tool/studentIntake")
public class IntakeController {

	private static final Logger logger = LoggerFactory.getLogger(IntakeController.class);

	@Autowired
	private IntakeService service;

	private TransferObjectListFactory<ChallengeTO, Challenge> challengeToFactory = new TransferObjectListFactory<ChallengeTO, Challenge>(ChallengeTO.class);
	private TransferObjectListFactory<ChildCareArrangementTO, ChildCareArrangement> childCareArrangementToFactory = new TransferObjectListFactory<ChildCareArrangementTO, ChildCareArrangement>(ChildCareArrangementTO.class);
	private TransferObjectListFactory<CitizenshipTO, Citizenship> citizenshipToFactory = new TransferObjectListFactory<CitizenshipTO, Citizenship>(CitizenshipTO.class);
	private TransferObjectListFactory<EducationGoalTO, EducationGoal> educationGoalToFactory = new TransferObjectListFactory<EducationGoalTO, EducationGoal>(EducationGoalTO.class);
	private TransferObjectListFactory<EducationLevelTO, EducationLevel> educationlevelToFactory = new TransferObjectListFactory<EducationLevelTO, EducationLevel>(EducationLevelTO.class);
	private TransferObjectListFactory<EthnicityTO, Ethnicity> ethnicityToFactory = new TransferObjectListFactory<EthnicityTO, Ethnicity>(EthnicityTO.class);
	private TransferObjectListFactory<FundingSourceTO, FundingSource> fundingsourceToFactory = new TransferObjectListFactory<FundingSourceTO, FundingSource>(FundingSourceTO.class);
	private TransferObjectListFactory<MaritalStatusTO, MaritalStatus> maritalstatusToFactory = new TransferObjectListFactory<MaritalStatusTO, MaritalStatus>(MaritalStatusTO.class);
	private TransferObjectListFactory<StudentStatusTO, StudentStatus> studentstatusToFactory = new TransferObjectListFactory<StudentStatusTO, StudentStatus>(StudentStatusTO.class);
	private TransferObjectListFactory<VeteranStatusTO, VeteranStatus> veteranstatusToFactory = new TransferObjectListFactory<VeteranStatusTO, VeteranStatus>(VeteranStatusTO.class);


	@Autowired
	private ChallengeService challengeService;


	@RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
	public @ResponseBody ServiceResponse save(@PathVariable UUID studentId, @Valid @RequestBody IntakeFormTO obj) throws Exception {
		return new ServiceResponse(service.save(obj.asModel()));
	}

	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	public @ResponseBody IntakeFormTO load(@PathVariable UUID studentId) throws Exception{
		IntakeFormTO formTO =  new IntakeFormTO(service.loadForPerson(studentId));
		
		Map<String, Object> refData = new HashMap<String, Object>();
		
		refData.put(
				"challenges", challengeToFactory.toTOList(
						challengeService.getAll(ObjectStatus.ACTIVE)));
		refData.put("employmentShifts", EmploymentShifts.values());
		refData.put("genders", Genders.values());
		refData.put("states", States.values());
		
		formTO.setReferenceData(refData);
		
		
		return formTO;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody ServiceResponse handle(Exception e){
		logger.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
