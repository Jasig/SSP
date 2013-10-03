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
package org.jasig.ssp.web.api.reference; // NOPMD

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.factory.TOFactory;
import org.jasig.ssp.factory.reference.JournalStepTOFactory;
import org.jasig.ssp.factory.reference.JournalTrackJournalStepTOFactory;
import org.jasig.ssp.factory.reference.JournalTrackTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.model.reference.JournalTrackJournalStep;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.JournalStepService;
import org.jasig.ssp.service.reference.JournalTrackService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.JournalAssociationTO;
import org.jasig.ssp.transferobject.reference.JournalStepTO;
import org.jasig.ssp.transferobject.reference.JournalTrackJournalStepTO;
import org.jasig.ssp.transferobject.reference.JournalTrackTO;
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
@RequestMapping("/1/reference/journalTrack")
public class JournalTrackController
		extends
		AbstractAuditableReferenceController<JournalTrack, JournalTrackTO> {

	@Autowired
	protected transient JournalTrackService service;

	@Autowired
	protected transient JournalStepService journalStepService;

	@Override
	protected AuditableCrudService<JournalTrack> getService() {
		return service;
	}

	@Autowired
	protected transient JournalTrackTOFactory factory;

	@Autowired
	protected transient JournalStepTOFactory journalStepFactory;

	@Autowired
	protected transient JournalTrackJournalStepTOFactory journalTrackJournalStepTOFactory;

	@Override
	protected TOFactory<JournalTrackTO, JournalTrack> getFactory() {
		return factory;
	}

	protected JournalTrackController() {
		super(JournalTrack.class, JournalTrackTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JournalTrackController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@RequestMapping(value = "/{journalTrackId}/journalTrackJournalStep", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<JournalTrackJournalStepTO> getAllJournalStepAssociationsForJournalTrack(
			final @PathVariable UUID journalTrackId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final PagingWrapper<JournalTrackJournalStep> data =
				service.getJournalStepAssociationsForJournalTrack(journalTrackId,
						SortingAndPaging.createForSingleSortWithPaging(status, start,
								limit, sort, sortDirection, null));

		return new PagedResponse<JournalTrackJournalStepTO>(true,
				data.getResults(), journalTrackJournalStepTOFactory
				.asTOList(data.getRows()));
	}

	@RequestMapping(value = "/{journalTrackId}/journalStep", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<JournalStepTO> getAllForJournalTrack(
			final @PathVariable UUID journalTrackId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final JournalTrack journalTrack = getService()
				.get(journalTrackId);

		SortingAndPaging sAndP = null;
		if(limit != null){
			sAndP = SortingAndPaging.createForSingleSortWithPaging(status, start, limit, sort, sortDirection, "sortOrder");
		}else{
			if(sort == null || sort.length() == 0)
				sAndP = SortingAndPaging.createForSingleSortAll(status, "sortOrder", sortDirection);
			else
				sAndP = SortingAndPaging.createForSingleSortAll(status, sort, sortDirection);
		}
		
		
		final PagingWrapper<JournalStep> data = journalStepService
				.getAllForJournalTrack(journalTrack,sAndP);

		return new PagedResponse<JournalStepTO>(true,
				data.getResults(), journalStepFactory
						.asTOList(data.getRows()));
	}

	@RequestMapping(value = "/{id}/journalStep", method = RequestMethod.POST)
	public @ResponseBody
	ServiceResponse addJournalStepToJournalTrack(@PathVariable final UUID id,
			@RequestBody @NotNull final JournalAssociationTO journalAssociation)
			throws ObjectNotFoundException {

		final JournalStep journalStep = journalStepService.get(journalAssociation.getId());
		final JournalTrack journalTrack = service.get(id);

		service.addJournalStepToJournalTrack(journalStep, journalTrack,journalAssociation.getSortOrder());

		return new ServiceResponse(true);
	}

	@RequestMapping(value = "/{id}/journalStep", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse removeJournalStepFromJournalTrack(
			@PathVariable final UUID id,
			@RequestBody @NotNull final UUID journalStepId)
			throws ObjectNotFoundException {

		final JournalStep journalStep = journalStepService.get(journalStepId);
		final JournalTrack journalTrack = service.get(id);

		service.removeJournalStepFromJournalTrack(journalStep, journalTrack);

		return new ServiceResponse(true);
	}
}