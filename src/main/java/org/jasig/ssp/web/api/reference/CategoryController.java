/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.web.api.reference; // NOPMD

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.CategoryTOFactory;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.CategoryService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.CategoryTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
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
 * Category controller
 */
@Controller
@RequestMapping("/1/reference/category")
@PreAuthorize(Permission.SECURITY_REFERENCE_COUNSELING_REF_GUIDE_WRITE)
public class CategoryController
		extends
		AbstractAuditableReferenceController<Category, CategoryTO> {

	@Autowired
	protected transient CategoryService service;

	@Autowired
	private transient ChallengeService challengeService;

	@Override
	protected AuditableCrudService<Category> getService() {
		return service;
	}

	@Autowired
	protected transient CategoryTOFactory factory;

	@Autowired
	protected transient ChallengeTOFactory challengeTOFactory;

	@Override
	protected TOFactory<CategoryTO, Category> getFactory() {
		return factory;
	}

	protected CategoryController() {
		super(Category.class, CategoryTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CategoryController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/{id}/challenge", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<ChallengeTO> getChallengesForCategory(
			@PathVariable final UUID id,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final Category category = service.get(id);

		final PagingWrapper<Challenge> data = challengeService
				.getAllForCategory(category,
						SortingAndPaging.createForSingleSortWithPaging(status, start,
								limit, sort, sortDirection, null));

		return new PagedResponse<ChallengeTO>(true, data.getResults(),
				challengeTOFactory.asTOSetOrdered(data.getRows()));
	}

	@RequestMapping(value = "/{id}/challenge", method = RequestMethod.POST)
	public @ResponseBody
	ServiceResponse addChallengeToCategory(@PathVariable final UUID id,
			@RequestBody @NotNull final UUID challengeId)
			throws ObjectNotFoundException {

		final Challenge challenge = challengeService.get(challengeId);
		final Category category = service.get(id);

		service.addChallengeToCategory(challenge, category);

		return new ServiceResponse(true);
	}

	@RequestMapping(value = "/{id}/challenge", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse removeChallengeFromCategory(@PathVariable final UUID id,
			@RequestBody @NotNull final UUID challengeId)
			throws ObjectNotFoundException {

		final Challenge challenge = challengeService.get(challengeId);
		final Category category = service.get(id);

		service.removeChallengeFromCategory(challenge, category);

		return new ServiceResponse(true);
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
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody CategoryTO create(@Valid @RequestBody final CategoryTO obj)
			throws ObjectNotFoundException,	ValidationException {
		return super.create(obj);
	}

	/**
	 * Persist any changes to the specified instance.
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
	 */
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody CategoryTO save(@PathVariable final UUID id, @Valid @RequestBody final CategoryTO obj)
			throws ValidationException, ObjectNotFoundException {
		return super.save(id, obj);
	}

	/**
	 * Marks the specified data instance with a status of
	 * {@link ObjectStatus#INACTIVE}.
	 *
	 * @param id
	 *            The id of the data instance to mark deleted.
	 * @return Success boolean.
	 * @throws ObjectNotFoundException
	 *             If specified object could not be found.
	 */
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ServiceResponse delete(@PathVariable final UUID id)
			throws ObjectNotFoundException {
		return super.delete(id);
	}
}