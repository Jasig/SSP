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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.tool.IntakeFormTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.AbstractReference;
import org.jasig.ssp.model.reference.EmploymentShifts;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.model.reference.RegistrationLoad;
import org.jasig.ssp.model.reference.States;
import org.jasig.ssp.model.tool.IntakeForm;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.service.reference.ChildCareArrangementService;
import org.jasig.ssp.service.reference.CitizenshipService;
import org.jasig.ssp.service.reference.CompletedItemService;
import org.jasig.ssp.service.reference.CourseworkHoursService;
import org.jasig.ssp.service.reference.EducationGoalService;
import org.jasig.ssp.service.reference.EducationLevelService;
import org.jasig.ssp.service.reference.EthnicityService;
import org.jasig.ssp.service.reference.RaceService;
import org.jasig.ssp.service.reference.FundingSourceService;
import org.jasig.ssp.service.reference.MaritalStatusService;
import org.jasig.ssp.service.reference.MilitaryAffiliationService;
import org.jasig.ssp.service.reference.RegistrationLoadService;
import org.jasig.ssp.service.reference.StudentStatusService;
import org.jasig.ssp.service.reference.VeteranStatusService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.tool.IntakeService;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.PersonChallengeTO;
import org.jasig.ssp.transferobject.PersonCompletedItemTO;
import org.jasig.ssp.transferobject.PersonEducationLevelTO;
import org.jasig.ssp.transferobject.PersonFundingSourceTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.AbstractReferenceTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.transferobject.reference.ChildCareArrangementTO;
import org.jasig.ssp.transferobject.reference.CitizenshipTO;
import org.jasig.ssp.transferobject.reference.CompletedItemTO;
import org.jasig.ssp.transferobject.reference.CourseworkHoursTO;
import org.jasig.ssp.transferobject.reference.EducationGoalTO;
import org.jasig.ssp.transferobject.reference.EducationLevelTO;
import org.jasig.ssp.transferobject.reference.EthnicityTO;
import org.jasig.ssp.transferobject.reference.RaceTO;
import org.jasig.ssp.transferobject.reference.FundingSourceTO;
import org.jasig.ssp.transferobject.reference.MaritalStatusTO;
import org.jasig.ssp.transferobject.reference.MilitaryAffiliationTO;
import org.jasig.ssp.transferobject.reference.RegistrationLoadTO;
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

import com.google.common.collect.Lists;

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
	private transient CompletedItemService completedItemsService;

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
	private transient RaceService raceService;

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
	private transient RegistrationLoadService registrationLoadService;
	
	@Autowired
	private transient CourseworkHoursService courseworkHoursService;
	
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
		formTO.setReferenceData(referenceData(formTO));
		return formTO;
	}

	/**
	 * Return all the data that is necessary to complete an intake form.
	 * 
	 * @return Service response with success value, in the JSON format.
	 * @throws ObjectNotFoundException 
	 */
	public Map<String, Object> referenceData(IntakeFormTO formTO) throws ObjectNotFoundException {
		final Map<String, Object> refData = new HashMap<String, Object>();

		final SortingAndPaging sAndP = SortingAndPaging
				.createForSingleSortWithPaging(ObjectStatus.ALL, 0, -1, null, null, null);

		refData.put("challenges", challengeReferenceDataFor(formTO, sAndP));
		refData.put("checklist", checkListReferenceDataFor(formTO, sAndP));
		
		refData.put("childCareArrangements", childCareArrangementReferenceDataFor(formTO, sAndP));
		refData.put("citizenships", citizenshipReferenceDataFor(formTO, sAndP));
		refData.put("educationGoals", educationGoalReferenceDataFor(formTO, sAndP));
		refData.put("educationLevels", educationLevelReferenceDataFor(formTO, sAndP));
		refData.put("ethnicities", ethnicityReferenceDataFor(formTO, sAndP));
		refData.put("races", raceReferenceDataFor(formTO, sAndP));
		refData.put("fundingSources", fundingSourceReferenceDataFor(formTO, sAndP));
		refData.put("maritalStatuses", maritalStatusArrangementReferenceDataFor(formTO, sAndP));
		refData.put("militaryAffiliations", militaryAffiliationReferenceDataFor(formTO, sAndP));
		refData.put("studentStatuses", studentStatusReferenceDataFor(formTO, sAndP));
		refData.put("veteranStatuses", veteranStatusReferenceDataFor(formTO, sAndP));

		refData.put("employmentShifts", EmploymentShifts.values());
		refData.put("genders", Genders.values());
		refData.put("states", States.values());
		refData.put("futureTerms",termService.getCurrentAndFutureTerms());
		refData.put("registrationLoads", registrationLoadReferenceDataFor(formTO, sAndP));
		refData.put("courseworkHours", courseworkHoursReferenceDataFor(formTO, sAndP));
		return refData;
	}

	private List<ChallengeTO> challengeReferenceDataFor(IntakeFormTO formTO,
														SortingAndPaging sAndP) {
		return filterInactiveExceptFor(
				associatedIds(formTO.getPersonChallenges(), PERSON_ASSOC_CHALLENGE_UUID_EXTRACTOR),
				ChallengeTO.toTOList(challengeService.getAllForIntake(sAndP).getRows(), true));
	}
	private List<CompletedItemTO> checkListReferenceDataFor(IntakeFormTO formTO,
			SortingAndPaging sAndP) {
		return filterInactiveExceptFor(
		associatedIds(formTO.getPersonChecklist(), PERSON_ASSOC_CHECKLIST_UUID_EXTRACTOR),
		CompletedItemTO.toTOList(completedItemsService.getAll(sAndP).getRows()));
	}
	
	private List<ChildCareArrangementTO> childCareArrangementReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<ChildCareArrangementTO> allTOs =
				ChildCareArrangementTO.toTOList(childCareArrangementService.getAll(sAndP).getRows());

		if ( formTO.getPersonDemographics() == null ||
				formTO.getPersonDemographics().getChildCareArrangementId() == null ) {

			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonDemographics().getChildCareArrangementId()),
				allTOs);
	}

	private List<CitizenshipTO> citizenshipReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<CitizenshipTO> allTOs =
				CitizenshipTO.toTOList(citizenshipService.getAll(sAndP).getRows());

		if ( formTO.getPersonDemographics() == null ||
				formTO.getPersonDemographics().getCitizenshipId() == null ) {

			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonDemographics().getCitizenshipId()),
				allTOs);
	}

	private List<EducationGoalTO> educationGoalReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<EducationGoalTO> allTOs =
				EducationGoalTO.toTOList(educationGoalService.getAll(sAndP).getRows());

		if ( formTO.getPersonEducationGoal() == null ) {
			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);
		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonEducationGoal().getEducationGoalId()),
				allTOs);
	}

	private List<EducationLevelTO> educationLevelReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		return filterInactiveExceptFor(
				associatedIds(formTO.getPersonEducationLevels(), PERSON_ASSOC_EDU_LEVEL_UUID_EXTRACTOR),
				EducationLevelTO.toTOList(educationLevelService.getAll(sAndP).getRows()));
	}

	private List<EthnicityTO> ethnicityReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<EthnicityTO> allTOs =
				EthnicityTO.toTOList(ethnicityService.getAll(sAndP).getRows());

		if ( formTO.getPersonDemographics() == null ||
				formTO.getPersonDemographics().getEthnicityId() == null ) {

			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonDemographics().getEthnicityId()),
				allTOs);
	}
	
	private List<RaceTO> raceReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<RaceTO> allTOs =
				RaceTO.toTOList(raceService.getAll(sAndP).getRows());

		if ( formTO.getPersonDemographics() == null ||
				formTO.getPersonDemographics().getRaceId() == null ) {

			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonDemographics().getRaceId()),
				allTOs);
	}

	private List<FundingSourceTO> fundingSourceReferenceDataFor(IntakeFormTO formTO,
														SortingAndPaging sAndP) {
		return filterInactiveExceptFor(
				associatedIds(formTO.getPersonFundingSources(), PERSON_ASSOC_FUNDING_SOURCE_UUID_EXTRACTOR),
				FundingSourceTO.toTOList(fundingSourceService.getAll(sAndP).getRows()));
	}

	private List<MaritalStatusTO> maritalStatusArrangementReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<MaritalStatusTO> allTOs =
				MaritalStatusTO.toTOList(maritalStatusService.getAll(sAndP).getRows());

		if ( formTO.getPersonDemographics() == null ||
				formTO.getPersonDemographics().getMaritalStatusId() == null ) {

			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonDemographics().getMaritalStatusId()),
				allTOs);
	}

	private List<MilitaryAffiliationTO> militaryAffiliationReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<MilitaryAffiliationTO> allTOs =
				MilitaryAffiliationTO.toTOList(militaryAffiliationService.getAll(sAndP).getRows());

		if ( formTO.getPersonDemographics() == null ||
				formTO.getPersonDemographics().getMilitaryAffiliationId() == null ) {

			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonDemographics().getMilitaryAffiliationId()),
				allTOs);
	}

	private List<StudentStatusTO> studentStatusReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<StudentStatusTO> allTOs =
				StudentStatusTO.toTOList(studentStatusService.getAll(sAndP).getRows());

		if ( formTO.getPersonEducationPlan() == null ||
				formTO.getPersonEducationPlan().getStudentStatusId() == null) {
			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);
		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonEducationPlan().getStudentStatusId()),
				allTOs);
	}

	private List<VeteranStatusTO> veteranStatusReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<VeteranStatusTO> allTOs =
				VeteranStatusTO.toTOList(veteranStatusService.getAll(sAndP).getRows());

		if ( formTO.getPersonDemographics() == null ||
				formTO.getPersonDemographics().getVeteranStatusId() == null ) {

			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonDemographics().getVeteranStatusId()),
				allTOs);
	}

	private List<RegistrationLoadTO> registrationLoadReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<RegistrationLoadTO> allTOs =
				RegistrationLoadTO.toTOList(registrationLoadService.getAll(sAndP).getRows());

		if ( formTO.getPersonEducationGoal() == null ||
				formTO.getPersonEducationGoal().getRegistrationLoadId() == null ) {

			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonEducationGoal().getRegistrationLoadId()),
				allTOs);
	}

	private List<CourseworkHoursTO> courseworkHoursReferenceDataFor(IntakeFormTO formTO, SortingAndPaging sAndP) {
		final List<CourseworkHoursTO> allTOs =
				CourseworkHoursTO.toTOList(courseworkHoursService.getAll(sAndP).getRows());

		if ( formTO.getPersonEducationGoal() == null ||
				formTO.getPersonEducationGoal().getCourseworkHoursId() == null ) {
 
			return filterInactiveExceptFor(Lists.<UUID>newArrayListWithCapacity(0), allTOs);

		}

		return filterInactiveExceptFor(
				Lists.newArrayList(formTO.getPersonEducationGoal().getCourseworkHoursId()),
				allTOs);
	}
	
	private <T extends AbstractReferenceTO> List<T>
	filterInactiveExceptFor(Collection<UUID> ids, List<T> toFilter) {
		List<T> filtered = Lists.newArrayListWithCapacity(toFilter.size());
		for ( T filterable : toFilter ) {
			if ( filterable.getObjectStatus() == ObjectStatus.ACTIVE ||
					ids.contains(filterable.getId())) {
				filtered.add(filterable);
			}
		}
		return filtered;
	}

	private static interface UUIDExtractor<T extends AbstractAuditableTO> {
		UUID fromTO(T to);
	}

	private static final UUIDExtractor<PersonChallengeTO> PERSON_ASSOC_CHALLENGE_UUID_EXTRACTOR =
			new UUIDExtractor<PersonChallengeTO>() {
				@Override
				public UUID fromTO(PersonChallengeTO to) {
					return to.getChallengeId();
				}
			};
	private static final UUIDExtractor<PersonCompletedItemTO> PERSON_ASSOC_CHECKLIST_UUID_EXTRACTOR =
			new UUIDExtractor<PersonCompletedItemTO>() {
				@Override
				public UUID fromTO(PersonCompletedItemTO to) {
					return to.getCompletedItemId();
				}
			};
	private static final UUIDExtractor<PersonEducationLevelTO> PERSON_ASSOC_EDU_LEVEL_UUID_EXTRACTOR =
			new UUIDExtractor<PersonEducationLevelTO>() {
				@Override
				public UUID fromTO(PersonEducationLevelTO to) {
					return to.getEducationLevelId();
				}
			};

	private static final UUIDExtractor<PersonFundingSourceTO> PERSON_ASSOC_FUNDING_SOURCE_UUID_EXTRACTOR =
			new UUIDExtractor<PersonFundingSourceTO>() {
				@Override
				public UUID fromTO(PersonFundingSourceTO to) {
					return to.getFundingSourceId();
				}
			};

	// no personassoc superclass for these Person*TOs, hence the callback
	private <T extends AbstractAuditableTO> Collection<UUID>
	associatedIds(List<T> tos, UUIDExtractor<T> uuidExtractor) {
		List<UUID> uuids = Lists.newArrayList();
		if ( tos == null ) {
			return uuids;
		}
		for ( T to : tos ) {
			uuids.add(uuidExtractor.fromTO(to));
		}
		return uuids;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
