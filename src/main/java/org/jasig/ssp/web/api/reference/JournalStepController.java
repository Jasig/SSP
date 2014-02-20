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
package org.jasig.ssp.web.api.reference;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.dao.reference.JournalStepDetailDao;
import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.JournalStepDetailTOFactory;
import org.jasig.ssp.factory.reference.JournalStepJournalStepDetailTOFactory;
import org.jasig.ssp.factory.reference.JournalStepTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.model.reference.JournalStepJournalStepDetail;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.JournalStepDetailService;
import org.jasig.ssp.service.reference.JournalStepService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.JournalAssociationTO;
import org.jasig.ssp.transferobject.reference.JournalStepDetailTO;
import org.jasig.ssp.transferobject.reference.JournalStepJournalStepDetailTO;
import org.jasig.ssp.transferobject.reference.JournalStepTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
@RequestMapping("/1/reference/journalStep")
public class JournalStepController
		extends
		AbstractAuditableReferenceController<JournalStep, JournalStepTO> {

	@Autowired
	protected transient JournalStepService service;

	@Autowired
	protected transient JournalStepDetailService journalStepDetailService;

	@Autowired
	protected transient JournalStepDetailDao journalStepDetailDao;

	@Override
	protected JournalStepService getService() {
		return service;
	}

	@Autowired
	protected transient JournalStepTOFactory factory;

	@Autowired
	protected transient JournalStepDetailTOFactory journalStepDetailTOFactory;

	@Autowired
	protected transient JournalStepJournalStepDetailTOFactory journalStepJournalStepDetailTOFactory;

	@Override
	protected TOFactory<JournalStepTO, JournalStep> getFactory() {
		return factory;
	}

	protected JournalStepController() {
		super(JournalStep.class, JournalStepTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalStepController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/{journalStepId}/journalStepJournalStepDetail", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<JournalStepJournalStepDetailTO> getAllJournalDetailAssociationsForJournalStep(
			final @PathVariable UUID journalStepId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final PagingWrapper<JournalStepJournalStepDetail> data =
				getService().getJournalStepDetailAssociationsForJournalStep(journalStepId,
				SortingAndPaging.createForSingleSortWithPaging(status, start,
						limit, sort, sortDirection, null));

		return new PagedResponse<JournalStepJournalStepDetailTO>(true,
				data.getResults(), journalStepJournalStepDetailTOFactory
					.asTOList(data.getRows()));
	}

	@RequestMapping(value = "/{journalStepId}/journalStepDetail", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<JournalStepDetailTO> getAllForJournalStep(
			final @PathVariable UUID journalStepId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final JournalStep journalStep = getService()
				.get(journalStepId);

		final PagingWrapper<JournalStepDetail> data = journalStepDetailService
				.getAllForJournalStep(journalStep,
						SortingAndPaging.createForSingleSortWithPaging(status, start,
								limit, sort, sortDirection, null));

		return new PagedResponse<JournalStepDetailTO>(true,
				data.getResults(), journalStepDetailTOFactory
						.asTOList(data.getRows()));
	}

	@RequestMapping(value = "/{id}/journalStepDetail", method = RequestMethod.POST)
	public @ResponseBody
	ServiceResponse addJournalStepDetailToJournalStep(
			@PathVariable final UUID id,
			@RequestBody @NotNull final JournalAssociationTO journalAssociation)
			throws ObjectNotFoundException {

		final JournalStepDetail journalStepDetail = journalStepDetailService
				.get(journalAssociation.getId());
		final JournalStep journalStep = service.get(id);
		for(JournalStepJournalStepDetail detail:journalStep.getJournalStepJournalStepDetails())
		{
			if(detail.getJournalStepDetail().getId().equals(journalStepDetail.getId())){
				service.removeJournalStepDetailFromJournalStep(journalStepDetail, journalStep);
				break;
			}
		}
		service.addJournalStepDetailToJournalStep(journalStepDetail,
				journalStep,journalAssociation.getSortOrder());

		return new ServiceResponse(true);
	}

	@RequestMapping(value = "/{id}/journalStepDetail", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse removeJournalStepDetailFromJournalStep(
			@PathVariable final UUID id,
			@RequestBody @NotNull final UUID journalStepDetailId)
			throws ObjectNotFoundException {

		final JournalStepDetail journalStepDetail = journalStepDetailDao
				.load(journalStepDetailId);
		final JournalStep journalStep = service.get(id);

		service.removeJournalStepDetailFromJournalStep(journalStepDetail,
				journalStep);

		return new ServiceResponse(true);
	}
}
