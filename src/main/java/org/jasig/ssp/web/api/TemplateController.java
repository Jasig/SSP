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

import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.reference.PlanLiteTOFactory;
import org.jasig.ssp.factory.reference.TemplateTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.service.MessageService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.TemplateService;
import org.jasig.ssp.service.external.TermService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.TemplateTO;
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

@Controller
@RequestMapping("/1/reference/map/template")
public class TemplateController  extends AbstractBaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TemplateController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	
	@Autowired
	private TemplateService service;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private TermService termService;
	
	@Autowired
	private TemplateTOFactory factory;
	
	@Autowired
	private PlanLiteTOFactory liteFactory;
	
	@Autowired
	private transient SecurityService securityService;
	
	@Autowired
	private transient ConfigService configService;
	
	@Autowired
	private transient MessageService messageService;

 
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
	PagedResponse<TemplateTO> get(
			final @RequestParam(required = false) Boolean isPrivate,
			final @RequestParam(required = false) ObjectStatus objectStatus,
			final @RequestParam(required = false) String divisionCode,
			final @RequestParam(required = false) String programCode) throws ObjectNotFoundException,
			ValidationException {
		final PagingWrapper<Template> data = getService().getAll(
				SortingAndPaging.createForSingleSortWithPaging(
						objectStatus == null ? ObjectStatus.ALL : objectStatus, null,
						null, null, null, null),isPrivate,divisionCode,programCode);

		return new PagedResponse<TemplateTO>(true, data.getResults(), getFactory()
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
	TemplateTO getTemplate(final @PathVariable UUID id) throws ObjectNotFoundException,
			ValidationException {
		Template model = getService().get(id);
		return new TemplateTO(model);
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
	PagedResponse<TemplateTO> getSummary(
			final @RequestParam(required = false) Boolean status,
			final @RequestParam(required = false) ObjectStatus objectStatus,
			final @RequestParam(required = false) String divisionCode,
			final @RequestParam(required = false) String programCode) throws ObjectNotFoundException,
			ValidationException {
		// Run getAll
		final PagingWrapper<Template> data = getService().getAll(
				SortingAndPaging.createForSingleSortWithPaging(
						objectStatus == null ? ObjectStatus.ALL : objectStatus, null,
						null, null, null, null),status,divisionCode,programCode);

		return new PagedResponse<TemplateTO>(true, data.getResults(), getFactory()
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
	TemplateTO create(@Valid @RequestBody final TemplateTO obj) throws ObjectNotFoundException,
			ValidationException, CloneNotSupportedException {
		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send an entity with an ID to the create method. Did you mean to use the save method instead?");
		}

		Template model = getFactory().from(obj);
		
		model = getService().copyAndSave(model);

		if (null != model) {
			final Template createdModel = getFactory().from(obj);
			if (null != createdModel) {
				return new TemplateTO(model);
			}
		}
		return null;
	}
	
//	/**
//	 * Returns an html page valid for printing
//	 * <p>
//	 *
//	 * 
//	 * @param obj
//	 *            instance to print.
//	 * @return html text strem
//	 * @throws ObjectNotFoundException
//	 *             If specified object could not be found.
//	 */
//	@PreAuthorize("hasRole('ROLE_PERSON_MAP_READ')")
//	@RequestMapping(value = "/print", method = RequestMethod.POST)
//	public @ResponseBody
//	String print(final HttpServletResponse response,
//			 @RequestBody final PlanOutputTO planOutputDataTO) throws ObjectNotFoundException {
//
//		SubjectAndBody message = getOutput(planOutputDataTO);
//		if(message != null)
//			return message.getBody();
//		
//		return null;
//	}
	
//	
//	/**
//	 * Returns an html page valid for printing
//	 * <p>
//	 *
//	 * 
//	 * @param obj
//	 *            instance to print.
//	 * @return html text strem
//	 * @throws ObjectNotFoundException
//	 *             If specified object could not be found.
//	 * @throws SendFailedException 
//	 */
//	@PreAuthorize("hasRole('ROLE_PERSON_MAP_READ')")
//	@RequestMapping(value = "/email", method = RequestMethod.POST)
//	public @ResponseBody
//	String email(final HttpServletResponse response,
//			 @RequestBody final PlanOutputTO planOutputDataTO) throws ObjectNotFoundException {
//		SubjectAndBody messageText = getOutput(planOutputDataTO);
//		if(messageText == null)
//			return null;
//
//	    Message message = messageService.createMessage(planOutputDataTO.getEmailTo(), 
//							planOutputDataTO.getEmailCC(),
//							messageText);
//		
//		return "Map Plan has been queued.";
//	}
//	
//	private SubjectAndBody getOutput(PlanOutputTO planOutputDataTO) throws ObjectNotFoundException{
//		Config institutionName = configService.getByName("inst_name");
//		SubjectAndBody output = null;
//		
//		if(planOutputDataTO.getOutputFormat().equals(OUTPUT_FORMAT_MATRIX)) {
//			output = service.createMapPlanMatirxOutput(planOutputDataTO.getPlan(), institutionName.getValue());
//		} else{
//			output = service.createMapPlanFullOutput(planOutputDataTO, institutionName.getValue());
//		}
//		
//		return output;
//	}

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
	TemplateTO save(@PathVariable final UUID id, @Valid @RequestBody final TemplateTO obj)
			throws ValidationException, ObjectNotFoundException, CloneNotSupportedException {
		if (id == null) {
			throw new ValidationException(
					"You submitted without an id to the save method.  Did you mean to create?");
		}

		if (obj.getId() == null) {
			obj.setId(id);
		}
		final Template oldPlan = getService().get(id);
		final Person oldOwner = oldPlan.getOwner();
		
		SspUser currentUser = getSecurityService().currentlyAuthenticatedUser();
		
		//If the currently logged in user is not the owner of this plan
		//we need to create a clone then save it.
		if(currentUser.getPerson().getId().equals(oldOwner.getId()))
		{
			final Template model = getFactory().from(obj);
			Template savedPlan = getService().save(model);
			if (null != model) {
				return new TemplateTO(savedPlan);
			}
		}
		else
		{
			obj.setId(null);
			Template model = getFactory().from(obj);
			final Template clonedPlan = getService().copyAndSave(model);
			if (null != clonedPlan) {
				return new TemplateTO(clonedPlan);
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

	public TemplateService getService() {
		return service;
	}

	public void setService(TemplateService service) {
		this.service = service;
	}

	public TemplateTOFactory getFactory() {
		return factory;
	}

	public void setFactory(TemplateTOFactory factory) {
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
