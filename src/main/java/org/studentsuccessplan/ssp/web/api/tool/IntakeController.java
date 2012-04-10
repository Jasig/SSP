package org.studentsuccessplan.ssp.web.api.tool;

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

import org.studentsuccessplan.ssp.factory.TransferObjectListFactory;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.model.reference.EducationGoal;
import org.studentsuccessplan.ssp.model.reference.EducationLevel;
import org.studentsuccessplan.ssp.model.reference.EmploymentShifts;
import org.studentsuccessplan.ssp.model.reference.Ethnicity;
import org.studentsuccessplan.ssp.model.reference.FundingSource;
import org.studentsuccessplan.ssp.model.reference.Genders;
import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.model.reference.States;
import org.studentsuccessplan.ssp.model.reference.StudentStatus;
import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.model.tool.IntakeForm;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.service.reference.ChildCareArrangementService;
import org.studentsuccessplan.ssp.service.reference.CitizenshipService;
import org.studentsuccessplan.ssp.service.reference.EducationGoalService;
import org.studentsuccessplan.ssp.service.reference.EducationLevelService;
import org.studentsuccessplan.ssp.service.reference.EthnicityService;
import org.studentsuccessplan.ssp.service.reference.FundingSourceService;
import org.studentsuccessplan.ssp.service.reference.MaritalStatusService;
import org.studentsuccessplan.ssp.service.reference.StudentStatusService;
import org.studentsuccessplan.ssp.service.reference.VeteranStatusService;
import org.studentsuccessplan.ssp.service.tool.IntakeService;
import org.studentsuccessplan.ssp.transferobject.ServiceResponse;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;
import org.studentsuccessplan.ssp.transferobject.reference.ChildCareArrangementTO;
import org.studentsuccessplan.ssp.transferobject.reference.CitizenshipTO;
import org.studentsuccessplan.ssp.transferobject.reference.EducationGoalTO;
import org.studentsuccessplan.ssp.transferobject.reference.EducationLevelTO;
import org.studentsuccessplan.ssp.transferobject.reference.EthnicityTO;
import org.studentsuccessplan.ssp.transferobject.reference.FundingSourceTO;
import org.studentsuccessplan.ssp.transferobject.reference.MaritalStatusTO;
import org.studentsuccessplan.ssp.transferobject.reference.StudentStatusTO;
import org.studentsuccessplan.ssp.transferobject.reference.VeteranStatusTO;
import org.studentsuccessplan.ssp.transferobject.tool.IntakeFormTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/tool/studentIntake")
public class IntakeController {

	private static final Logger logger = LoggerFactory
			.getLogger(IntakeController.class);

	@Autowired
	private IntakeService service;

	@Autowired
	private ChallengeService challengeService;

	@Autowired
	private ChildCareArrangementService childCareArrangementService;

	@Autowired
	private CitizenshipService citizenshipService;

	@Autowired
	private EducationGoalService educationGoalService;

	@Autowired
	private EducationLevelService educationLevelService;

	@Autowired
	private EthnicityService ethnicityService;

	@Autowired
	private FundingSourceService fundingSourceService;

	@Autowired
	private MaritalStatusService maritalStatusService;

	@Autowired
	private StudentStatusService studentStatusService;

	@Autowired
	private VeteranStatusService veteranStatusService;

	private final TransferObjectListFactory<ChallengeTO, Challenge> challengeToFactory = new TransferObjectListFactory<ChallengeTO, Challenge>(
			ChallengeTO.class);

	private final TransferObjectListFactory<ChildCareArrangementTO, ChildCareArrangement> childCareArrangementToFactory = new TransferObjectListFactory<ChildCareArrangementTO, ChildCareArrangement>(
			ChildCareArrangementTO.class);

	private final TransferObjectListFactory<CitizenshipTO, Citizenship> citizenshipToFactory = new TransferObjectListFactory<CitizenshipTO, Citizenship>(
			CitizenshipTO.class);

	private final TransferObjectListFactory<EducationGoalTO, EducationGoal> educationGoalToFactory = new TransferObjectListFactory<EducationGoalTO, EducationGoal>(
			EducationGoalTO.class);

	private final TransferObjectListFactory<EducationLevelTO, EducationLevel> educationLevelToFactory = new TransferObjectListFactory<EducationLevelTO, EducationLevel>(
			EducationLevelTO.class);

	private final TransferObjectListFactory<EthnicityTO, Ethnicity> ethnicityToFactory = new TransferObjectListFactory<EthnicityTO, Ethnicity>(
			EthnicityTO.class);

	private final TransferObjectListFactory<FundingSourceTO, FundingSource> fundingSourceToFactory = new TransferObjectListFactory<FundingSourceTO, FundingSource>(
			FundingSourceTO.class);

	private final TransferObjectListFactory<MaritalStatusTO, MaritalStatus> maritalStatusToFactory = new TransferObjectListFactory<MaritalStatusTO, MaritalStatus>(
			MaritalStatusTO.class);

	private final TransferObjectListFactory<StudentStatusTO, StudentStatus> studentStatusToFactory = new TransferObjectListFactory<StudentStatusTO, StudentStatus>(
			StudentStatusTO.class);

	private final TransferObjectListFactory<VeteranStatusTO, VeteranStatus> veteranStatusToFactory = new TransferObjectListFactory<VeteranStatusTO, VeteranStatus>(
			VeteranStatusTO.class);

	/**
	 * Save changes to an IntakeForm
	 * 
	 * @param studentId
	 *            Student identifier
	 * @param intakeForm
	 *            Incoming data
	 * @exception Exception
	 *                Any errors will throw this generic exception.
	 * @return Service response with success value, in the JSON format.
	 */
	@RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
	public @ResponseBody
	ServiceResponse save(@PathVariable UUID studentId,
			@Valid @RequestBody IntakeFormTO intakeForm) throws Exception {
		IntakeForm model = intakeForm.asModel();
		model.getPerson().setId(studentId);
		return new ServiceResponse(service.save(model));
	}

	/**
	 * Using the studentId passed, return the IntakeForm in its current state,
	 * creating it if necessary.
	 * 
	 * @param studentId
	 *            Student identifier
	 * @exception Exception
	 *                Any errors will throw this generic exception.
	 * @return Service response with success value, in the JSON format.
	 */
	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	public @ResponseBody
	IntakeFormTO load(@PathVariable UUID studentId) throws Exception {
		IntakeFormTO formTO = new IntakeFormTO(service.loadForPerson(studentId));
		formTO.setReferenceData(referenceData());
		return formTO;
	}

	/**
	 * Return all the data that is necessary to complete an intake form.
	 * 
	 * @exception Exception
	 *                Any errors will throw this generic exception.
	 * @return Service response with success value, in the JSON format.
	 */
	public Map<String, Object> referenceData() throws Exception {
		Map<String, Object> refData = new HashMap<String, Object>();

		refData.put("challenges", challengeToFactory.toTOList(challengeService
				.getAll(ObjectStatus.ACTIVE, null, null, null, null)));
		refData.put("childCareArrangements", childCareArrangementToFactory
				.toTOList(childCareArrangementService.getAll(
						ObjectStatus.ACTIVE, null, null, null, null)));
		refData.put("citizenships", citizenshipToFactory
				.toTOList(citizenshipService.getAll(ObjectStatus.ACTIVE, null,
						null, null, null)));
		refData.put("educationGoals", educationGoalToFactory
				.toTOList(educationGoalService.getAll(ObjectStatus.ACTIVE,
						null, null, null, null)));
		refData.put("educationLevels", educationLevelToFactory
				.toTOList(educationLevelService.getAll(ObjectStatus.ACTIVE,
						null, null, null, null)));
		refData.put("ethnicitys", ethnicityToFactory.toTOList(ethnicityService
				.getAll(ObjectStatus.ACTIVE, null, null, null, null)));
		refData.put("fundingSources", fundingSourceToFactory
				.toTOList(fundingSourceService.getAll(ObjectStatus.ACTIVE,
						null, null, null, null)));
		refData.put("maritalStatuss", maritalStatusToFactory
				.toTOList(maritalStatusService.getAll(ObjectStatus.ACTIVE,
						null, null, null, null)));
		refData.put("studentStatuss", studentStatusToFactory
				.toTOList(studentStatusService.getAll(ObjectStatus.ACTIVE,
						null, null, null, null)));
		refData.put("veteranStatuss", veteranStatusToFactory
				.toTOList(veteranStatusService.getAll(ObjectStatus.ACTIVE,
						null, null, null, null)));

		refData.put("employmentShifts", EmploymentShifts.values());
		refData.put("genders", Genders.values());
		refData.put("states", States.values());

		return refData;
	}

	/**
	 * Wraps any Exceptions in a {@link ServiceResponse}
	 * 
	 * @param e
	 *            Exception to handle
	 * @return Service response with success value, in the JSON format.
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ServiceResponse handle(Exception e) {
		logger.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}
