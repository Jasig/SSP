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
package org.jasig.ssp.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.external.ExternalPersonPlanStatusTOFactory;
import org.jasig.ssp.factory.reference.PlanLiteTOFactory;
import org.jasig.ssp.factory.reference.PlanTOFactory;
import org.jasig.ssp.model.Message;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.model.SubjectAndBody;
import org.jasig.ssp.model.external.ExternalPersonPlanStatus;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.PlanService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.external.ExternalPersonPlanStatusService;
import org.jasig.ssp.service.external.ExternalStudentFinancialAidService;
import org.jasig.ssp.service.external.ExternalStudentTranscriptService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PlanCourseTO;
import org.jasig.ssp.transferobject.PlanLiteTO;
import org.jasig.ssp.transferobject.PlanOutputTO;
import org.jasig.ssp.transferobject.PlanTO;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.external.ExternalPersonPlanStatusTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/1/person/{personId}/map/plan")
public class PlanController  extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlanController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@Autowired
	private PlanService service;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private PlanTOFactory factory;
	
	@Autowired
	private PlanLiteTOFactory liteFactory;
	
	@Autowired
	private transient SecurityService securityService;
	
	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private transient MessageService messageService;
	
	@Autowired
	private transient ExternalPersonPlanStatusService planStatusService;
	
	@Autowired
	private ExternalPersonPlanStatusTOFactory planStatusFactory;
	
	@Autowired
	private ExternalStudentFinancialAidService externalStudentFinancialAidService;
	
	@Autowired
	private ExternalStudentTranscriptService externalStudentTranscriptService;

 
	/**
	 * Retrieves the specified list from persistent storage.
	 * 
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_READ')")
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<PlanTO> get(final @PathVariable UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit) throws ObjectNotFoundException,
			ValidationException {
		// Run getAll
		final PagingWrapper<Plan> data = getService().getAllForStudent(
				SortingAndPaging.createForSingleSortWithPaging(
						status == null ? ObjectStatus.ALL : status, start,
						limit, null, null, null),personId);

		return new PagedResponse<PlanTO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));		
	}

	/**
	 * Retrieves the specified list from persistent storage.
	 * 
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_READ')")
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public @ResponseBody
	PlanTO getPlan(final @PathVariable UUID personId,final @PathVariable UUID id) throws ObjectNotFoundException,
			ValidationException {
		Plan model = getService().get(id);
		return validatePlan(new PlanTO(model));
	}	
	/**
	 * Retrieves the current entity from persistent storage.
	 * 
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */	
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	@RequestMapping(value="/current", method = RequestMethod.GET)
	public @ResponseBody
	PlanTO getCurrentForStudent(final @PathVariable UUID personId) throws ObjectNotFoundException,
			ValidationException {
		final Plan model = getService().getCurrentForStudent(personId);
		if (model == null) {
			return null;
		}
		return validatePlan(new PlanTO(model));
	}

	/**
	 * Retrieves the specified list from persistent storage.  
	 * 
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If that specified data is not invalid.
	 */
	@RequestMapping(value="/summary", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<PlanLiteTO> getSummary(final @PathVariable UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit) throws ObjectNotFoundException,
			ValidationException {
		// Run getAll
		final PagingWrapper<Plan> data = getService().getAllForStudent(
				SortingAndPaging.createForSingleSortWithPaging(
						status == null ? ObjectStatus.ALL : status, start,
						limit, null, null, null),personId);

		return new PagedResponse<PlanLiteTO>(true, data.getResults(), getLiteFactory()
				.asTOList(data.getRows()));		
	}
	
	/**
	 * Persist a new instance of the specified object.
	 * <p>
	 * Must not include an id.
	 * 
	 * @param obj
	 *            New instance to persist.
	 * @return Original instance plus the generated id.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If the specified data contains an id (since it shouldn't).
	 * @throws CloneNotSupportedException 
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	PlanTO create(@Valid @RequestBody final PlanTO obj) throws ObjectNotFoundException,
			ValidationException, CloneNotSupportedException {
		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send an entity with an ID to the create method. Did you mean to use the save method instead?");
		}

		Plan model = getFactory().from(obj);
		model = getService().copyAndSave(model);

		if (null != model) {
			final Plan createdModel = getFactory().from(obj);
			if (null != createdModel) {
				return validatePlan(new PlanTO(model));
			}
		}
		return null;
	}
	
	/**
	 * Returns an html page valid for printing
	 * <p>
	 *
	 * 
	 * @param obj
	 *            instance to print.
	 * @return html text strem
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_READ')")
	@RequestMapping(value = "/print", method = RequestMethod.POST)
	public @ResponseBody
	String print(final HttpServletResponse response,
			 @RequestBody final PlanOutputTO planOutputDataTO) throws ObjectNotFoundException {

		
			
		SubjectAndBody message = getOutput(planOutputDataTO);
		if(message != null)
			return message.getBody();
		
		return null;
	}
	
	
	/**
	 * Returns an html page valid for printing
	 * <p>
	 *
	 * 
	 * @param obj
	 *            instance to print.
	 * @return html text strem
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws SendFailedException 
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_READ')")
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public @ResponseBody
	String email(final HttpServletResponse response,
			 @RequestBody final PlanOutputTO planOutputDataTO) throws ObjectNotFoundException {
		SubjectAndBody messageText = getOutput(planOutputDataTO);
		if(messageText == null)
			return null;

	    messageService.createMessage(planOutputDataTO.getEmailTo(), 
							planOutputDataTO.getEmailCC(),
							messageText);
		
		return "Map Plan has been queued.";
	}
	
	private SubjectAndBody getOutput(PlanOutputTO planOutputDataTO) throws ObjectNotFoundException{
		Config institutionName = configService.getByName("inst_name");
		SubjectAndBody output = null;
		
		if(planOutputDataTO.getOutputFormat().equals(PlanService.OUTPUT_FORMAT_MATRIX)) {
			output = service.createMatirxOutput(planOutputDataTO.getNonOutputTO(), institutionName.getValue());
		} else{
			UUID personID = UUID.fromString(planOutputDataTO.getPlan().getPersonId());
			String schoolId = personService.get(personID).getSchoolId();
			planOutputDataTO.setFinancialAid(externalStudentFinancialAidService.getStudentFinancialAidBySchoolId(schoolId));
			planOutputDataTO.setGpa(externalStudentTranscriptService.getRecordsBySchoolId(schoolId));
			output = service.createFullOutput(planOutputDataTO, institutionName.getValue());
		}
		
		return output;
	}

	/**
	 * Persist any changes to the plan instance.
	 * 
	 * @param id
	 *            Explicit id to the instance to persist.
	 * @param obj
	 *            Full instance to persist.
	 * @return The update data object instance.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 * @throws ValidationException
	 *             If the specified id is null.
	 * @throws CloneNotSupportedException 
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	PlanTO save(@PathVariable final UUID id, @Valid @RequestBody final PlanTO obj)
			throws ValidationException, ObjectNotFoundException, CloneNotSupportedException {
		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		if (obj.getId() == null) {
			obj.setId(id);
		}
		final Plan oldPlan = getService().get(id);
		final Person oldOwner = oldPlan.getOwner();
		
		SspUser currentUser = getSecurityService().currentlyAuthenticatedUser();
		
		//If the currently logged in user is not the owner of this plan
		//we need to create a clone then save it.
		if(currentUser.getPerson().getId().equals(oldOwner.getId()))
		{
			final Plan model = getFactory().from(obj);
			Plan savedPlan = getService().save(model);
			if (null != model) {
				return validatePlan(new PlanTO(savedPlan));
			}
		}
		else
		{
			obj.setId(null);
			Plan model = getFactory().from(obj);
			final Plan clonedPlan = getService().copyAndSave(model);
			if (null != clonedPlan) {
				return validatePlan(new PlanTO(clonedPlan));
			}
		}

		return null;
	}

	/**
	 * Marks the specified plan instance with a status of
	 * {@link ObjectStatus#INACTIVE}.
	 * 
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id)
			throws ObjectNotFoundException {
		getService().delete(id);
		return new ServiceResponse(true);
	}
	
	/**
	 * Validate the plan instance.
	 * 
	 * @param id
	 *            Explicit id to the instance to persist.
	 * @param obj
	 *            Full instance of plan object.
	 * @return The validated data object instance.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public @ResponseBody
	PlanTO validatePlan(final HttpServletResponse response,
			 @RequestBody final PlanTO plan)
			throws ObjectNotFoundException {
		String schoolId = null;
		if(StringUtils.isNotBlank(plan.getPersonId())){
			Person student = personService.get(UUID.fromString(plan.getPersonId()));
			schoolId = student.getSchoolId();
		}
		PlanTO validatedTO = getService().validate(plan, schoolId);
		return validatedTO;
	}
	
	/**
	 * Return plan status for given student.
	 * 
	 * @param personId
	 *            Explicit personId to the instance to persist.
	 * @return The current plan status of the student.
	 */
	@PreAuthorize("hasRole('ROLE_PERSON_MAP_WRITE')")
	@RequestMapping(value = "/planstatus", method = RequestMethod.POST)
	public @ResponseBody
	ExternalPersonPlanStatusTO getPlanStatus(final HttpServletResponse response,
			@PathVariable final UUID personId)
			throws ObjectNotFoundException {
		if(personId == null){
			return null;
		}
		String schoolId = null;
		Person student = personService.get(personId);
		schoolId = student.getSchoolId();
		//TODO not the cleanest way to handle but clientside generates 500 error in console
		// Currently plan status is not required.
		try{
			return planStatusFactory.from(planStatusService.getBySchoolId(schoolId));
		}catch(Exception exp)
		{
			return null;
		}
	}
	
	private PlanTO validatePlan(PlanTO plan) throws ObjectNotFoundException{
		String schoolId = null;
		if(StringUtils.isNotBlank(plan.getPersonId())){
			Person student = personService.get(UUID.fromString(plan.getPersonId()));
			schoolId = student.getSchoolId();
		}
		return getService().validate(plan, schoolId);
	}

	public PlanService getService() {
		return service;
	}

	public void setService(PlanService service) {
		this.service = service;
	}

	public PlanTOFactory getFactory() {
		return factory;
	}

	public void setFactory(PlanTOFactory factory) {
		this.factory = factory;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public PlanLiteTOFactory getLiteFactory() {
		return liteFactory;
	}

	public void setLiteFactory(PlanLiteTOFactory liteFactory) {
		this.liteFactory = liteFactory;
	}
}
