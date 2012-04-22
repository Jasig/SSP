package org.studentsuccessplan.ssp.web.api.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;
import org.studentsuccessplan.ssp.web.api.BaseController;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/tool/studentIntake")
public class IntakeController extends BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IntakeController.class);

	@Autowired
	private transient IntakeService service;

	@Autowired
	private transient ChallengeService challengeService;

	@Autowired
	private transient ChildCareArrangementService childCareArrangementService;

	@Autowired
	private transient CitizenshipService citizenshipService;

	@Autowired
	private transient EducationGoalService educationGoalService;

	@Autowired
	private transient EducationLevelService educationLevelService;

	@Autowired
	private transient EthnicityService ethnicityService;

	@Autowired
	private transient FundingSourceService fundingSourceService;

	@Autowired
	private transient MaritalStatusService maritalStatusService;

	@Autowired
	private transient StudentStatusService studentStatusService;

	@Autowired
	private transient VeteranStatusService veteranStatusService;

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
	ServiceResponse save(final @PathVariable UUID studentId,
			final @Valid @RequestBody IntakeFormTO intakeForm) throws Exception {
		final IntakeForm model = intakeForm.asModel();
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
	IntakeFormTO load(final @PathVariable UUID studentId) throws Exception {
		final IntakeFormTO formTO = new IntakeFormTO(
				service.loadForPerson(studentId));
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
		final Map<String, Object> refData = new HashMap<String, Object>();

		SortingAndPaging sAndP = new SortingAndPaging(ObjectStatus.ACTIVE);
		refData.put("challenges", ChallengeTO
				.listToTOList((List<Challenge>) challengeService.getAll(sAndP)
						.getRows()));
		refData.put(
				"childCareArrangements",
				ChildCareArrangementTO
						.listToTOList((List<ChildCareArrangement>) childCareArrangementService
								.getAll(sAndP).getRows()));
		refData.put("citizenships", CitizenshipTO
				.listToTOList((List<Citizenship>) citizenshipService.getAll(
						sAndP).getRows()));
		refData.put("educationGoals", EducationGoalTO
				.listToTOList((List<EducationGoal>) educationGoalService
						.getAll(sAndP).getRows()));
		refData.put("educationLevels", EducationLevelTO
				.listToTOList((List<EducationLevel>) educationLevelService
						.getAll(sAndP).getRows()));
		refData.put("ethnicities", EthnicityTO
				.listToTOList((List<Ethnicity>) ethnicityService.getAll(sAndP)
						.getRows()));
		refData.put("fundingSources", FundingSourceTO
				.listToTOList((List<FundingSource>) fundingSourceService
						.getAll(sAndP).getRows()));
		refData.put("maritalStatuses", MaritalStatusTO
				.listToTOList((List<MaritalStatus>) maritalStatusService
						.getAll(sAndP).getRows()));
		refData.put("studentStatuses", StudentStatusTO
				.listToTOList((List<StudentStatus>) studentStatusService
						.getAll(sAndP).getRows()));
		refData.put("veteranStatuses", VeteranStatusTO
				.listToTOList((List<VeteranStatus>) veteranStatusService
						.getAll(sAndP).getRows()));

		refData.put("employmentShifts", EmploymentShifts.values());
		refData.put("genders", Genders.values());
		refData.put("states", States.values());

		return refData;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
