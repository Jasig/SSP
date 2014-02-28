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
import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.security.permissions.Permission;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.ServiceResponse;
import org.jasig.ssp.transferobject.reference.ChallengeReferralTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
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
@RequestMapping("/1/reference/challenge")
public class ChallengeController
		extends
		AbstractAuditableReferenceController<Challenge, ChallengeTO> {

	@Autowired
	protected transient ChallengeService service;

	@Autowired
	protected transient ChallengeReferralService challengeReferralService;

	@Override
	protected AuditableCrudService<Challenge> getService() {
		return service;
	}

	@Autowired
	protected transient ChallengeTOFactory factory;

	@Autowired
	protected transient ChallengeReferralTOFactory challengeReferralTOFactory;

	@Override
	protected TOFactory<ChallengeTO, Challenge> getFactory() {
		return factory;
	}

	protected ChallengeController() {
		super(Challenge.class, ChallengeTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Get all {@link ChallengeReferralTO} associated with the specified
	 * {@link ChallengeTO}.
	 * 
	 * @param id
	 *            challenge id
	 * @param status
	 *            {@link ObjectStatus}
	 * @param start
	 *            Optional start (0-based)
	 * @param limit
	 *            Optional row limit
	 * @param sort
	 *            Optional sort column
	 * @param sortDirection
	 *            Optional sort direction
	 * @return All {@link ChallengeReferralTO} associated with the specified
	 *         {@link ChallengeTO}.
	 * @throws ObjectNotFoundException
	 *             If the specified challenge could not be found.
	 */
	@RequestMapping(value = "/{id}/challengeReferral", method = RequestMethod.GET)
	@PreAuthorize(Permission.SECURITY_REFERENCE_READ)
	public @ResponseBody
	PagedResponse<ChallengeReferralTO> getChallengeReferralsForChallenge(
			@PathVariable final UUID id,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		final Challenge challenge = service.get(id);
		

		final PagingWrapper<ChallengeReferral> data = challengeReferralService
				.getAllForChallenge(challenge, SortingAndPaging
						.createForSingleSortWithPaging(status, start, limit, sort == null ? "name":sort,
								sortDirection, null));

		return new PagedResponse<ChallengeReferralTO>(true,
				data.getResults(),
				challengeReferralTOFactory.asTOSet(data.getRows()));
	}

	/**
	 * Adds an association between a challenge and a challenge referral.
	 * 
	 * @param id
	 *            challenge id
	 * @param challengeReferralId
	 *            challenge referral id
	 * @return ServiceResponse with appropriate status
	 * @throws ObjectNotFoundException
	 *             If one of the objects were not found
	 */
	@RequestMapping(value = "/{id}/challengeReferral", method = RequestMethod.POST)
	public @ResponseBody
	ServiceResponse addChallengeReferralToChallenge(
			@PathVariable final UUID id,
			@RequestBody @NotNull final UUID challengeReferralId)
			throws ObjectNotFoundException {

		final Challenge challenge = service.get(id);
		final ChallengeReferral referral = challengeReferralService
				.get(challengeReferralId);

		service.addChallengeReferralToChallenge(referral, challenge);

		return new ServiceResponse(true);
	}

	/**
	 * Remove an association between a challenge and a challenge referral.
	 * 
	 * @param id
	 *            challenge id
	 * @param challengeReferralId
	 *            challenge referral id
	 * @return ServiceResponse with appropriate status
	 * @throws ObjectNotFoundException
	 *             If one of the objects were not found
	 */
	@RequestMapping(value = "/{id}/challengeReferral", method = RequestMethod.DELETE)
	public @ResponseBody
	ServiceResponse removeChallengeReferralFromChallenge(
			@PathVariable final UUID id,
			@RequestBody @NotNull final UUID challengeReferralId)
			throws ObjectNotFoundException {

		final Challenge challenge = service.get(id);
		final ChallengeReferral referral = challengeReferralService
				.get(challengeReferralId);

		service.removeChallengeReferralFromChallenge(referral, challenge);
		return new ServiceResponse(true);
	}
}