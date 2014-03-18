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

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.reference.ProgramStatus;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonProgramStatusService;
import org.jasig.ssp.service.reference.ProgramStatusService;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.SendFailedException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Services to manipulate EarlyAlerts.
 * <p>
 * Mapped to URI path <code>/1/person/{personId}/earlyAlert</code>
 */
@Controller
public class PersonEarlyAlertController extends
		AbstractPersonAssocController<EarlyAlert, EarlyAlertTO> {

	protected PersonEarlyAlertController() {
		super(EarlyAlert.class, EarlyAlertTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonEarlyAlertController.class);

	@Autowired
	private transient EarlyAlertService service;

	@Autowired
	private transient EarlyAlertTOFactory factory;
	
	@Autowired
	private transient ProgramStatusService programStatusService;
	
	@Autowired
	private transient PersonProgramStatusService personProgramStatusService;

	@Override
	protected EarlyAlertTOFactory getFactory() {
		return factory;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected EarlyAlertService getService() {
		return service;
	}

	// Overriding to specify full request path since we needed a custom create
	// method
	@Override
	@DynamicPermissionChecking
	@RequestMapping(value = "/1/person/{personId}/earlyAlert/{id}", method = RequestMethod.GET)
	public @ResponseBody
	EarlyAlertTO get(final @PathVariable @NotNull UUID id,
			@PathVariable @NotNull final UUID personId)
			throws ObjectNotFoundException,
			ValidationException {
		if (personId == null) {
			throw new ValidationException("Missing person identifier in path.");
		}

		return super.get(id, personId);
	}

	@Override
	@DynamicPermissionChecking
	@RequestMapping(value = "/1/person/{personId}/earlyAlert", method = RequestMethod.POST)
	public @ResponseBody
	EarlyAlertTO create(@PathVariable @NotNull final UUID personId,
			@Valid @NotNull @RequestBody final EarlyAlertTO obj)
			throws ValidationException, ObjectNotFoundException {
		// validate incoming data
		if (personId == null) {
			throw new IllegalArgumentException(
					"Missing or invalid person identifier in the path.");
		}

		if (obj == null) {
			throw new IllegalArgumentException(
					"Missing or invalid early alert data.");
		}

		if (obj.getPersonId() != null && !personId.equals(obj.getPersonId())) {
			throw new ValidationException(
					"Person identifier in path, did not match the person"
							+ " identifier in the early alert data. Those values must"
							+ " match if a person identifier is set in the data.");
		}

		if (obj.getPersonId() == null) {
			obj.setPersonId(personId);
		}
		
		if(obj.getClosedById() != null)
		{
			obj.setClosedDate(new Date());
		}
		
		// As per SSP-900, students will have their programStatus set to 'Active'  regardless of it's current state
		setProgramStatusToActiveIfNotAlready(personId);
		
		// create
		final EarlyAlertTO earlyAlertTO = super.create(personId, obj);

		// send e-mail to student if requested
		if (obj.getSendEmailToStudent() != null
				&& Boolean.TRUE.equals(obj.getSendEmailToStudent())) {
			try {
				service.sendMessageToStudent(factory.from(earlyAlertTO));
			} catch (final SendFailedException exc) {
				LOGGER.error(
						"Send message failed when creating a new early alert. Early Alert was created, but message was not succesfully sent to student.",
						exc);
			} catch (final ObjectNotFoundException exc) {
				LOGGER.error(
						"Send message failed when creating a new early alert. Early Alert was created, but message was not succesfully sent to student.",
						exc);
			} catch (final ValidationException exc) {
				LOGGER.error(
						"Send message failed when creating a new early alert. Early Alert was created, but message was not succesfully sent to student.",
						exc);
			}
		}

		// return created EarlyAlert
		return earlyAlertTO;
	}

	private void setProgramStatusToActiveIfNotAlready(final UUID personId)
			throws ObjectNotFoundException, ValidationException {
		Person student = personService.load(personId);
		ProgramStatus activeStatus = programStatusService.getActiveStatus();
		if(!activeStatus.getName().equals(student.getCurrentProgramStatusName()))
		{
			PersonProgramStatus personProgramStatus = new PersonProgramStatus();
			personProgramStatus.setPerson(student);
			personProgramStatus.setProgramStatus(activeStatus);
			personProgramStatus.setEffectiveDate(Calendar.getInstance().getTime());
			personProgramStatusService.expireActive(student,personProgramStatus); 
			student.getProgramStatuses().add(personProgramStatus);
		}
	}

	@Override
	@RequestMapping(value = "/1/person/{personId}/earlyAlert/{id}", method = RequestMethod.PUT)
	@DynamicPermissionChecking
	public @ResponseBody
	EarlyAlertTO save(@PathVariable final UUID id,
			@PathVariable final UUID personId,
			@Valid @RequestBody final EarlyAlertTO obj)
			throws ObjectNotFoundException, ValidationException {
		
		if(obj.getClosedById() != null)
		{
			obj.setClosedDate(new Date());
		}
		return super.save(id, personId, obj);
	}

	@Override
	@RequestMapping(value = "/1/person/{personId}/earlyAlert/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable final UUID id,
			@PathVariable final UUID personId) throws ObjectNotFoundException {
		return super.delete(id, personId);
	}

	// Overriding because the default sort column needs to be unique
	@Override
	@RequestMapping(value = "/1/person/{personId}/earlyAlert", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<EarlyAlertTO> getAll(
			@PathVariable final UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {
		// Check permissions
		checkPermissionForOp("READ");

		// Run getAll for the specified person
		final Person person = personService.get(personId);
		final PagingWrapper<EarlyAlert> data = getService().getAllForPerson(
				person,
				SortingAndPaging.createForSingleSortWithPaging(status, start,
						limit, sort, sortDirection, "createdDate"));

		return new PagedResponse<EarlyAlertTO>(true, data.getResults(),
				getFactory().asTOList(data.getRows()));
	}

	/**
	 * Create an early alert.
	 * 
	 * @param studentId
	 *            student id (legacy school id, not the SSP person id)
	 * @param obj
	 *            early alert data
	 * @return Created early alert, with assigned id.
	 * @throws ObjectNotFoundException
	 *             If any of the specified data could not be found.
	 * @throws ValidationException
	 *             If any of the data was not valid.
	 */
	@RequestMapping(value = "/1/person/earlyAlert", method = RequestMethod.POST)
	@DynamicPermissionChecking
	public @ResponseBody
	EarlyAlertTO create(@RequestParam final String studentId,
			@Valid @RequestBody final EarlyAlertTO obj)
			throws ObjectNotFoundException, ValidationException {

		checkPermissionForOp("wRITE");

		if (obj.getId() != null) {
			throw new ValidationException(
					"It is invalid to send with an ID to the create method. Did you mean to use the save method instead?");
		}

		if (StringUtils.isEmpty(studentId)) {
			throw new IllegalArgumentException(
					"Person identifier is required.");
		}

		UUID personId = null; // NOPMD by jon.adams on 5/14/12 1:40 PM

		// Figure out which type of PersonID was sent
		try {
			personId = UUID.fromString(studentId); // NOPMD by jon.adams
		} catch (final IllegalArgumentException exc) {
			final Person person = personService.getBySchoolId(studentId,false);

			if (person == null) {
				throw new ObjectNotFoundException(
						null, "Person", exc);
			}

			personId = person.getId();
		}
		
		// As per SSP-900, students will have their programStatus set to 'Active'  regardless of it's current state
		setProgramStatusToActiveIfNotAlready(personId);
		
		if (personId == null) {
			throw new ObjectNotFoundException(
					"Specified person or student identifier could not be found.",
					"Person");
		}

		if (obj.getPersonId() == null) {
			obj.setPersonId(personId);
		}

		return super.create(personId, obj);
	}

	@Override
	public String permissionBaseName() {
		return "EARLY_ALERT";
	}
}