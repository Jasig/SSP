package org.jasig.ssp.web.api;

import java.util.UUID;

import javax.validation.Valid;

import org.jasig.ssp.factory.EarlyAlertRoutingTOFactory;
import org.jasig.ssp.model.EarlyAlertRouting;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.EarlyAlertRoutingService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.transferobject.EarlyAlertRoutingTO;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
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

/**
 * EarlyAlertRouting controller
 */
@Controller
@PreAuthorize(Permission.SECURITY_REFERENCE_WRITE)
@RequestMapping("/1/reference/campus/{campusId}/earlyAlertRouting")
public class EarlyAlertRoutingController
		extends AbstractBaseController {

	/**
	 * Empty constructor
	 */
	protected EarlyAlertRoutingController() {
		super();
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EarlyAlertRoutingController.class);

	@Autowired
	protected transient EarlyAlertRoutingService service;

	@Autowired
	protected transient EarlyAlertRoutingTOFactory factory;

	@Autowired
	protected transient CampusService campusService;

	/**
	 * Retrieve every instance in the database filtered by the supplied status.
	 * 
	 * @param campusId
	 *            Campus identifier
	 * @param status
	 *            Filter by this status.
	 * @param start
	 *            First result (0-based index) to return. Parameter must be a
	 *            positive, non-zero integer. Often comes from client as a
	 *            parameter labeled <code>start</code>. A null value indicates
	 *            to return rows starting from index 0.
	 * @param limit
	 *            Maximum number of results to return. Parameter must be a
	 *            positive, non-zero integer. Often comes from client as a
	 *            parameter labeled <code>limit</code>. A null value indicates
	 *            return all rows from the start parameter to the end of the
	 *            data.
	 * @param sort
	 *            Property name. If null or empty string, the default sort will
	 *            be used. If non-empty, must be a case-sensitive model property
	 *            name. Often comes from client as a parameter labeled
	 *            <code>sort</code>. Example sort expression:
	 *            <code>propertyName</code>
	 * @param sortDirection
	 *            Ascending/descending keyword. If null or empty string, the
	 *            default sort will be used. Must be <code>ASC</code> or
	 *            <code>DESC</code>.
	 * @throws ObjectNotFoundException
	 *             If any data could not be found
	 * @return All entities in the database filtered by the supplied status.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<EarlyAlertRoutingTO> getAll(
			final @PathVariable UUID campusId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {
		service.checkCampusIds(campusId, null);

		// Run getAll for the specified Campus
		final PagingWrapper<EarlyAlertRouting> list = service
				.getAllForCampus(campusService.get(campusId), SortingAndPaging
						.createForSingleSort(status, start, limit, sort,
								sortDirection,
								null));

		return new PagedResponse<EarlyAlertRoutingTO>(true,
				list.getResults(),
				factory.asTOList(list.getRows()));
	}

	/**
	 * Retrieves the specified instance from persistent storage.
	 * 
	 * @param id
	 *            The specific id to use to lookup the associated data.
	 * @param campusId
	 *            The campus id
	 * @return The specified instance if found.
	 * @throws ObjectNotFoundException
	 *             If specified data could not be found.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	EarlyAlertRoutingTO get(
			final @PathVariable UUID campusId,
			final @PathVariable UUID id)
			throws ObjectNotFoundException {
		final EarlyAlertRouting model = service.get(id);

		if (model == null) {
			throw new ObjectNotFoundException(id, "EarlyAlertRouting");
		}

		service.checkCampusIds(campusId, model);

		return new EarlyAlertRoutingTO(model);
	}

	/**
	 * Persist a new instance of the specified object.
	 * <p>
	 * Must not include an id.
	 * 
	 * @param campusId
	 *            Campus identifier
	 * @param obj
	 *            New instance to persist.
	 * @return Original instance plus the generated id.
	 * @throws org.jasig.ssp.web.api.validation.ValidationException
	 *             If the data contains an id (since it shouldn't).
	 * @throws ObjectNotFoundException
	 *             If the data could not be found.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	EarlyAlertRoutingTO create(final @PathVariable UUID campusId,
			final @Valid @RequestBody EarlyAlertRoutingTO obj)
			throws ObjectNotFoundException, ValidationException {
		if (obj.getId() != null) {
			throw new ValidationException(
					"You submitted a earlyAlertRouting with an id to the create method. Did you mean to save?");
		}

		if (obj.getCampusId() == null) {
			obj.setCampusId(campusId);
		}

		final EarlyAlertRouting model = factory.from(obj);

		if (null != model) {
			service.checkCampusIds(campusId, model);

			final EarlyAlertRouting createdModel = service.create(model);
			if (null != createdModel) {
				return new EarlyAlertRoutingTO(createdModel);
			}
		}

		return null;
	}

	/**
	 * Persist any changes to the specified instance.
	 * 
	 * @param campusId
	 *            Campus identifier
	 * @param id
	 *            Explicit id to the instance to persist.
	 * @param obj
	 *            Full instance to persist.
	 * @return The update data object instance.
	 * @throws ValidationException
	 *             If there was any missing or invalid data.
	 * @throws ObjectNotFoundException
	 *             If data could not be found.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	EarlyAlertRoutingTO save(final @PathVariable UUID campusId,
			final @PathVariable UUID id,
			final @Valid @RequestBody EarlyAlertRoutingTO obj)
			throws ObjectNotFoundException, ValidationException {
		if (id == null) {
			throw new ValidationException(
					"You submitted a earlyAlertRouting without an id to the save method. Did you mean to create?");
		}

		if (obj == null) {
			throw new ValidationException(
					"EarlyAlertRouting data is required.");
		}

		if (obj.getCampusId() == null) {
			obj.setCampusId(campusId);
		}

		final EarlyAlertRouting model = factory.from(obj);
		model.setId(id);

		service.checkCampusIds(campusId, model);

		final EarlyAlertRouting savedEarlyAlertRouting = service.save(model);
		if (null == savedEarlyAlertRouting) {
			return null;
		}

		return new EarlyAlertRoutingTO(savedEarlyAlertRouting);
	}

	/**
	 * Marks the specified data instance with a status of
	 * {@link ObjectStatus#INACTIVE}.
	 * 
	 * @param campusId
	 *            Campus identifier
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws ObjectNotFoundException
	 *             If any data could not be found.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse delete(final @PathVariable UUID campusId,
			final @PathVariable UUID id)
			throws ObjectNotFoundException {
		service.checkCampusIds(campusId, service.get(id));

		service.delete(id);
		return new ServiceResponse(true);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}