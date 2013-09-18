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

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.factory.PersonChallengeTOFactory;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonChallenge;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonChallengeService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.PagedResponse;
import org.jasig.ssp.transferobject.PersonChallengeTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.util.security.DynamicPermissionChecking;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Services to manipulate Challenges for a Person.
 * <p>
 * Mapped to URI path <code>/1/person/{personId}/challenge</code>
 */
@Controller
@RequestMapping("/1/person/{personId}/challenge")
public class PersonChallengeController extends
		AbstractPersonAssocController<PersonChallenge, PersonChallengeTO> {

	protected PersonChallengeController() {
		super(PersonChallenge.class, PersonChallengeTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonChallengeController.class);

	@Autowired
	private transient PersonChallengeService service;

	@Autowired
	private transient PersonChallengeTOFactory factory;

	@Autowired
	private transient ChallengeService challengeService;

	@Autowired
	private transient ChallengeTOFactory challengeTOFactory;

	@Override
	protected PersonChallengeTOFactory getFactory() {
		return factory;
	}

	@Override
	protected PersonChallengeService getService() {
		return service;
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Get PersonChallenges for a person, returns a shallow object At path /all
	 */
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody
	PagedResponse<PersonChallengeTO> getAll(
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
		final PagingWrapper<PersonChallenge> data = getService()
				.getAllForPerson(person,
						SortingAndPaging.createForSingleSortWithPaging(status, start,
								limit, sort, sortDirection, null));

		return new PagedResponse<PersonChallengeTO>(true,
				data.getResults(), getFactory()
						.asTOList(data.getRows()));
	}

	/**
	 * Get Challenges for a person, returns a deeper object graph than /all
	 * 
	 * @param personId
	 *            person
	 * @param status
	 *            status
	 * @param start
	 *            starting row (0-based)
	 * @param limit
	 *            row limit
	 * @param sort
	 *            sort field
	 * @param sortDirection
	 *            sort direction
	 * @return Challenges for a person
	 * @throws ObjectNotFoundException
	 *             if object could not be found
	 */
	@RequestMapping(method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	PagedResponse<ChallengeTO> getChallengesForPerson(
			@PathVariable final UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {

		checkPermissionForOp("READ");

		final Person person = personService.get(personId);
		final PagingWrapper<Challenge> data = challengeService.getAllForPerson(
				person,
				SortingAndPaging.createForSingleSortWithPaging(status, start,
						limit, sort, sortDirection, null));

		return new PagedResponse<ChallengeTO>(true, data.getResults(),
				challengeTOFactory.asTOList(data.getRows()));
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@DynamicPermissionChecking
	public @ResponseBody
	List<ChallengeTO> getChallengesForPerson(@PathVariable final UUID personId,
			@RequestParam("query") final String query)
			throws Exception {

		try {
			return challengeService.search(query,personService.get(personId),false);
		} catch (Exception e) {
			LOGGER.error("ERROR : search() : {}", e.getMessage(), e);
			throw e;
		}
	}
	@Override
	public String permissionBaseName() {
		return "CHALLENGE";
	}
}
