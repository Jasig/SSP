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

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.jasig.ssp.factory.reference.TemplateLiteTOFactory;
import org.jasig.ssp.factory.reference.TemplateTOFactory;
import org.jasig.ssp.model.MapTemplateVisibility;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.SubjectAndBody;
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
import org.jasig.ssp.transferobject.TemplateLiteTO;
import org.jasig.ssp.transferobject.TemplateOutputTO;
import org.jasig.ssp.transferobject.TemplateSearchTO;
import org.jasig.ssp.transferobject.TemplateTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
	
	private static String ANONYMOUS_MAP_TEMPLATE_ACCESS="anonymous_map_template_access";
	 
	@Autowired
	private TemplateService service;
	
	@Autowired
	private PersonService personService;

	@Autowired
	private TermService termService;
	
	@Autowired
	private TemplateTOFactory factory;
	
	@Autowired
	private TemplateLiteTOFactory liteFactory;
	
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
	@DynamicPermissionChecking
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<TemplateTO> get(
			final @RequestParam(required = false) MapTemplateVisibility visibility,
			final @RequestParam(required = false) ObjectStatus objectStatus,
			final @RequestParam(required = false) String divisionCode,
			final @RequestParam(required = false) String programCode,
			final @RequestParam(required = false) String departmentCode) throws ObjectNotFoundException,
			ValidationException {
		
		TemplateSearchTO searchTO = new TemplateSearchTO(visibility,  objectStatus,
															divisionCode,  programCode,  departmentCode);
		validateAccessForGet(searchTO);
		final PagingWrapper<Template> data = getService().getAll(
				SortingAndPaging.createForSingleSortWithPaging(
						objectStatus == null ? ObjectStatus.ALL : objectStatus, null,
						null, null, null, null), searchTO);
		if(data == null)
			return null;
		
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
	@DynamicPermissionChecking
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public @ResponseBody
	TemplateTO getTemplate(final @PathVariable UUID id) throws ObjectNotFoundException,
			ValidationException {
		Template model = getService().get(id);
		SspUser currentUser = getSecurityService().currentlyAuthenticatedUser();
		TemplateTO to = validatePlan(new TemplateTO(model));
		if(to != null ){
			if(to.getVisibility().equals(MapTemplateVisibility.PRIVATE) && currentUser != null){
				if(!to.getCreatedBy().getId().equals(currentUser.getPerson().getId()))
					throw new AccessDeniedException("Insufficient permissions to view private template.");
			}else if ((currentUser == null || !getSecurityService().isAuthenticated()) && !to.getVisibility().equals(MapTemplateVisibility.ANONYMOUS))
				throw new AccessDeniedException("Insufficient permissions to view requested template.");
			else if((currentUser == null || !getSecurityService().isAuthenticated()) && !anonymousUsersAllowed()){
				throw new AccessDeniedException("Insufficient permissions to view requested template. Unanimous access is not activated.");
			}
			return to;
		}
	    return null;
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
	@DynamicPermissionChecking
	@RequestMapping(value="/summary", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<TemplateLiteTO> getSummary(
			final @RequestParam(required = false) MapTemplateVisibility visibility,
			final @RequestParam(required = false) ObjectStatus objectStatus,
			final @RequestParam(required = false) String divisionCode,
			final @RequestParam(required = false) String programCode,
			final @RequestParam(required = false) String departmentCode) throws ObjectNotFoundException,
			ValidationException {
		
		TemplateSearchTO searchTO = new TemplateSearchTO(visibility, objectStatus,
				divisionCode,  programCode,  departmentCode);
		
		validateAccessForGet(searchTO);
		final PagingWrapper<Template> data = getService().getAll(
				SortingAndPaging.createForSingleSortWithPaging(
						objectStatus == null ? ObjectStatus.ALL : objectStatus, null,
						null, null, null, null), searchTO);
		if(data == null)
			return null;
		return new PagedResponse<TemplateLiteTO>(true, data.getResults(), getLiteFactory()
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
		assertTemplateWritePublicApiAuthorization(obj);



		Template model = getFactory().from(obj);
		model = getService().save(model);
		if (null != model) {
			final Template createdModel = getFactory().from(obj);
			if (null != createdModel) {
				return validatePlan(new TemplateTO(model));
			}
		}
		return null;
	}

	/**
	 * Rejects the change request if the user is proposing saving/creating a
	 * public {@link Template} but lacks the necessary permissions. For most
	 * operations we do this in terms of a {@link TemplateTO}, which represents
	 * the proposed new state, instead of a {@link Template} because
	 * it's not the existing state that matters, it's the end state. I.e.
	 * a user should be allowed to make a public {@link Template} private but
	 * not vice versa. Also, testing the TO in this was is slightly more
	 * performant than testing the mapped {@link Template} or both since we
	 * can skip the mapping if the test fails. There is at least one case,
	 * though (deletion), where we need the actual model (see
	 * {@link #assertTemplateWritePublicApiAuthorization(org.jasig.ssp.model.Template)}).
	 *
	 * <p>This also just <em>happens</em> to work because we require that
	 * all API update operations pass the entire object to be updated, so
	 * the client is essentially forced to specify the privateness mode that
	 * they want in every inbound {@link TemplateTO}</p>
	 *
	 * @param template
	 * @throws AccessDeniedException
	 */
	private void assertTemplateWritePublicApiAuthorization(TemplateTO template)
			throws AccessDeniedException {
		if ( securityService.hasAuthority("ROLE_MAP_PUBLIC_TEMPLATE_WRITE") || template.getIsPrivate()){
			return;
		}

		throw new AccessDeniedException("Insufficient permissions to create, delete, or make changes to a public template.");
	}

	
	
	/**
	 * This overload intended primarily to support the delete operation.
	 *
	 * @see #assertTemplateWritePublicApiAuthorization(org.jasig.ssp.transferobject.TemplateTO)
	 * @param template
	 */
	private void assertTemplateWritePublicApiAuthorization(Template template) {
		if ( securityService.hasAuthority("ROLE_MAP_PUBLIC_TEMPLATE_WRITE") || template.getIsPrivate()){
			return;
		}

		throw new AccessDeniedException("Insufficient permissions to create, delete, or make changes to a public template.");
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
			 @RequestBody final TemplateOutputTO planOutputDataTO) throws ObjectNotFoundException {

		SubjectAndBody message = getService().createOutput(planOutputDataTO);
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
			 @RequestBody final TemplateOutputTO planOutputDataTO) throws ObjectNotFoundException {
		
		SubjectAndBody messageText = getService().createOutput(planOutputDataTO);
		if(messageText == null)
			return null;

	   messageService.createMessage(planOutputDataTO.getEmailTo(), 
							planOutputDataTO.getEmailCC(),
							messageText);
		
		return "Map Plan has been queued.";
	}

	/**
	 * Persist any changes to the template instance.
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

		assertTemplateWritePublicApiAuthorization(obj);

		final Template oldTemplate = getService().get(id);
		final Person oldOwner = oldTemplate.getOwner();
		SspUser currentUser = getSecurityService().currentlyAuthenticatedUser();
		
//		Three scenarios for template save
//		1) User is template admin (has the ROLE_MAP_PUBLIC_TEMPLATE_WRITE perm): save in place
//		2) User is not a template admin but saves a template in which he is already the owner: save in place
//		3) User is not a template admin but saves a template in which he is NOT the owner: copy on save
		if(currentUser.getPerson().getId().equals(oldOwner.getId()) || securityService.hasAuthority("ROLE_MAP_PUBLIC_TEMPLATE_WRITE") )
		{
			final Template model = getFactory().from(obj);
			Template savedTemplate = getService().save(model);
			if (null != model) {
				return validatePlan(new TemplateTO(savedTemplate));
			}
		}
		else
		{
			obj.setId(null);
			Template model = getFactory().from(obj);
			final Template clonedTemplate = getService().copyAndSave(model,securityService.currentlyAuthenticatedUser().getPerson());

			if (null != clonedTemplate) {
				return validatePlan(new TemplateTO(clonedTemplate));
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
		Template model = getService().get(id);
		
		assertTemplateWritePublicApiAuthorization(model);
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
	TemplateTO validatePlan(final HttpServletResponse response,
			 @RequestBody final TemplateTO plan)
			throws ObjectNotFoundException {

		TemplateTO validatedTO = getService().validate(plan);
		return validatedTO;
	}
	
	private TemplateTO validatePlan(TemplateTO plan) throws ObjectNotFoundException{
		return getService().validate(plan);
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

	public TemplateLiteTOFactory getLiteFactory() {
		return liteFactory;
	}

	public void setLiteFactory(TemplateLiteTOFactory liteFactory) {
		this.liteFactory = liteFactory;
	}
	
	private Boolean anonymousUsersAllowed() {
		return Boolean.parseBoolean(configService.getByName(ANONYMOUS_MAP_TEMPLATE_ACCESS).getValue().toLowerCase());
	}
	
	private void validateAccessForGet(TemplateSearchTO searchTO){
		if(!securityService.isAuthenticated() || !securityService.hasAuthority("ROLE_PERSON_MAP_READ")){
			if(!anonymousUsersAllowed()){
				LOGGER.info("Invalid request for templates, requested by anonymous user, anonymous access is not enabled");
				throw new AccessDeniedException("Invalid request for templates, requested by anonymous user, anonymous access is not enabled");
			}
			if(searchTO.visibilityAll())
				searchTO.setVisibility(MapTemplateVisibility.ANONYMOUS);
			if(!searchTO.getVisibility().equals(MapTemplateVisibility.ANONYMOUS)){
				LOGGER.info("Invalid request for templates, request by anonymous user request was for private/authenticated templates only.");
				throw new AccessDeniedException("Invalid request for templates, request by anonymous user request was for private/authenticated templates only.");
			}
		}
	}

}
