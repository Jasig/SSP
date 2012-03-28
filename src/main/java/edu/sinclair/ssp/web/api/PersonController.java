package edu.sinclair.ssp.web.api;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.factory.TransferObjectListFactory;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.transferobject.PersonTO;
import edu.sinclair.ssp.transferobject.ServiceResponse;
import edu.sinclair.ssp.web.api.validation.ValidationException;

/**
 * Some basic methods for manipulating people in the system.
 * 
 * :TODO Lock down the methods in the class based on business requirements
 * 
 */
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/person")
public class PersonController extends RestController<PersonTO> {

	private static final Logger logger = LoggerFactory
			.getLogger(PersonController.class);

	@Autowired
	private PersonService service;

	private TransferObjectListFactory<PersonTO, Person> toFactory = new TransferObjectListFactory<PersonTO, Person>(
			PersonTO.class);

	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	List<PersonTO> getAll(@RequestParam(required = false) ObjectStatus status)
			throws Exception {
		if (status == null) {
			status = ObjectStatus.ACTIVE;
		}
		return toFactory.toTOList(service.getAll(status));
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	PersonTO get(@PathVariable UUID id) throws Exception {
		Person model = service.get(id);
		if (model != null) {
			return new PersonTO(model);
		} else {
			return null;
		}
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody
	PersonTO create(@Valid @RequestBody PersonTO obj) throws Exception {
		if (obj.getId() != null) {
			throw new ValidationException(
					"You submitted a person with an id to the create method.  Did you mean to save?");
		}

		Person model = obj.asModel();

		if (null != model) {
			Person createdModel = service.create(model);
			if (null != createdModel) {
				return new PersonTO(createdModel);
			}
		}
		return null;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	PersonTO save(@PathVariable UUID id, @Valid @RequestBody PersonTO obj)
			throws Exception {
		if (id == null) {
			throw new ValidationException(
					"You submitted a person without an id to the save method.  Did you mean to create?");
		}

		Person model = obj.asModel();
		model.setId(id);

		Person savedPerson = service.save(model);
		if (null != savedPerson) {
			return new PersonTO(savedPerson);
		}
		return null;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(@PathVariable UUID id) throws Exception {
		service.delete(id);
		return new ServiceResponse(true);
	}

	@Override
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ServiceResponse handle(Exception e) {
		logger.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}

}
