/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api.tool; // NOPMD by jon.adams

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.tool.IntakeFormTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.EmploymentShifts;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.model.reference.States;
import org.jasig.ssp.model.tool.IntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.service.reference.ChildCareArrangementService;
import org.jasig.ssp.service.reference.CitizenshipService;
import org.jasig.ssp.service.reference.EducationGoalService;
import org.jasig.ssp.service.reference.EducationLevelService;
import org.jasig.ssp.service.reference.EthnicityService;
import org.jasig.ssp.service.reference.FundingSourceService;
import org.jasig.ssp.service.reference.MaritalStatusService;
import org.jasig.ssp.service.reference.MilitaryAffiliationService;
import org.jasig.ssp.service.reference.StudentStatusService;
import org.jasig.ssp.service.reference.VeteranStatusService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.tool.IntakeService;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.transferobject.reference.ChildCareArrangementTO;
import org.jasig.ssp.transferobject.reference.CitizenshipTO;
import org.jasig.ssp.transferobject.reference.EducationGoalTO;
import org.jasig.ssp.transferobject.reference.EducationLevelTO;
import org.jasig.ssp.transferobject.reference.EthnicityTO;
import org.jasig.ssp.transferobject.reference.FundingSourceTO;
import org.jasig.ssp.transferobject.reference.MaritalStatusTO;
import org.jasig.ssp.transferobject.reference.MilitaryAffiliationTO;
import org.jasig.ssp.transferobject.reference.StudentStatusTO;
import org.jasig.ssp.transferobject.reference.VeteranStatusTO;
import org.jasig.ssp.transferobject.tool.IntakeFormTO;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.jasig.ssp.web.api.validation.ValidationException;
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

/**
 * Student Intake tool services
 * <p>
 * Mapped to URI path <code>/1/tool/studentIntake</code>
 */
@Controller
@RequestMapping("/1/tool/studentIntake")
public class IntakeController extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IntakeController.class);

	@Autowired
	private transient IntakeService service;

	@Autowired
	private transient IntakeFormTOFactory factory;

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
	private transient MilitaryAffiliationService militaryAffiliationService;

	@Autowired
	private transient StudentStatusService studentStatusService;

	@Autowired
	private transient VeteranStatusService veteranStatusService;
	
	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private transient TermService termService;

	/**
	 * Save changes to an IntakeForm
	 * 
	 * @param studentId
	 *            Student identifier
	 * @param intakeForm
	 *            Incoming data
	 * @return Service response with success value, in the JSON format.
	 * @throws ValidationException
	 *             If IntakeForm data was not valid.
	 * @throws ObjectNotFoundException
	 *             If any reference look up data couldn't be loaded.
	 */
	@PreAuthorize("hasRole('ROLE_STUDENT_INTAKE_WRITE')")
	@RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
	public @ResponseBody
	ServiceResponse save(final @PathVariable UUID studentId,
			final @Valid @RequestBody IntakeFormTO intakeForm)
			throws ObjectNotFoundException, ValidationException {
		final IntakeForm model = factory.from(intakeForm);
		model.getPerson().setId(studentId);
		return new ServiceResponse(service.save(model));
	}

	/**
	 * Using the studentId passed, return the IntakeForm in its current state,
	 * creating it if necessary.
	 * 
	 * @param studentId
	 *            Student identifier Any errors will throw this generic
	 *            exception.
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException
	 *             If any reference data could not be loaded.
	 */
	@RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_STUDENT_INTAKE_READ')")
	public @ResponseBody
	IntakeFormTO load(final @PathVariable UUID studentId)
			throws ObjectNotFoundException {
		final IntakeFormTO formTO = new IntakeFormTO(
				service.loadForPerson(studentId));
		formTO.setReferenceData(referenceData());
		return formTO;
	}

	/**
	 * Return all the data that is necessary to complete an intake form.
	 * 
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException 
	 */
	public Map<String, Object> referenceData() throws ObjectNotFoundException {
		final Map<String, Object> refData = new HashMap<String, Object>();

		final SortingAndPaging sAndP = new SortingAndPaging(ObjectStatus.ACTIVE);

		final List<ChallengeTO> challenges = ChallengeTO
				.toTOList(challengeService.getAllForIntake(sAndP).getRows(),
						true);

		refData.put("challenges", challenges);

		refData.put("childCareArrangements", ChildCareArrangementTO
				.toTOList(childCareArrangementService.getAll(sAndP).getRows()));
		refData.put("citizenships",
				CitizenshipTO.toTOList(citizenshipService.getAll(sAndP)
						.getRows()));
		refData.put("educationGoals", EducationGoalTO
				.toTOList(educationGoalService.getAll(sAndP).getRows()));
		refData.put("educationLevels", EducationLevelTO
				.toTOList(educationLevelService.getAll(sAndP).getRows()));
		refData.put("ethnicities",
				EthnicityTO.toTOList(ethnicityService.getAll(sAndP).getRows()));
		refData.put("fundingSources", FundingSourceTO
				.toTOList(fundingSourceService.getAll(sAndP).getRows()));
		refData.put("maritalStatuses", MaritalStatusTO
				.toTOList(maritalStatusService.getAll(sAndP).getRows()));
		refData.put("militaryAffiliations", MilitaryAffiliationTO
				.toTOList(militaryAffiliationService.getAll(sAndP).getRows()));
		refData.put("studentStatuses", StudentStatusTO
				.toTOList(studentStatusService.getAll(sAndP).getRows()));
		refData.put("veteranStatuses", VeteranStatusTO
				.toTOList(veteranStatusService.getAll(sAndP).getRows()));

		refData.put("employmentShifts", EmploymentShifts.values());
		refData.put("genders", Genders.values());
		refData.put("states", States.values());
		refData.put("futureTerms",termService.getCurrentAndFutureTerms());
		refData.put("registrationLoadRanges", configService.getByName("registration_load_ranges").getValue());
		refData.put("weeklyCourseWorkHourRanges", configService.getByName("weekly_course_work_hour_ranges").getValue());
		return refData;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
