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
package org.jasig.ssp.web.api.reference;

import java.util.ArrayList;
import java.util.UUID;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.SelfHelpGuideQuestionTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SelfHelpGuideQuestion;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.SelfHelpGuideQuestionService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.SelfHelpGuideQuestionTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/1/selfHelpGuides/selfHelpGuideQuestions")
@PreAuthorize(Permission.SECURITY_REFERENCE_MYGPS_WRITE)
public class SelfHelpGuideQuestionController
		extends
		AbstractAuditableReferenceController<SelfHelpGuideQuestion, SelfHelpGuideQuestionTO> {

	@Autowired
	protected transient SelfHelpGuideQuestionService service;

	@Override
	protected AuditableCrudService<SelfHelpGuideQuestion> getService() {
		return service;
	}

	@Autowired
	protected transient SelfHelpGuideQuestionTOFactory factory;

	@Override
	protected TOFactory<SelfHelpGuideQuestionTO, SelfHelpGuideQuestion> getFactory() {
		return factory;
	}

	protected SelfHelpGuideQuestionController() {
		super(SelfHelpGuideQuestion.class, SelfHelpGuideQuestionTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SelfHelpGuideQuestionController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<SelfHelpGuideQuestionTO> getAllForParent(
			final @RequestParam(required = false) String selfReferenceGuideId
			) {
		if(selfReferenceGuideId == null || "".equals(selfReferenceGuideId))
		{
			return new PagedResponse<SelfHelpGuideQuestionTO>(true,0L,new ArrayList<SelfHelpGuideQuestionTO>());
		}

		final PagingWrapper<SelfHelpGuideQuestion> data =service.getAllForParent(
				SortingAndPaging.createForSingleSortWithPaging(
						ObjectStatus.ACTIVE , null,
						null, null, null, "questionNumber"),selfReferenceGuideId);

		PagedResponse<SelfHelpGuideQuestionTO> pagedResponse = new PagedResponse<SelfHelpGuideQuestionTO>(true, data.getResults(), getFactory()
				.asTOList(data.getRows()));
		return pagedResponse;
	}
	
	@Override
	@PreAuthorize(Permission.SECURITY_PERSON_READ)
	@RequestMapping	
	public @ResponseBody
	PagedResponse<SelfHelpGuideQuestionTO> getAll(ObjectStatus status,
			Integer start, Integer limit, String sort, String sortDirection) {
		return super.getAll(status, start, limit, sort, sortDirection);
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
	public @ResponseBody SelfHelpGuideQuestionTO create(@Valid @RequestBody final SelfHelpGuideQuestionTO obj)
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
	public @ResponseBody SelfHelpGuideQuestionTO save(@PathVariable final UUID id, @Valid @RequestBody final SelfHelpGuideQuestionTO obj)
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
