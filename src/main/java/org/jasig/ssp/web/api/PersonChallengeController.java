package org.jasig.ssp.web.api;

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
import org.jasig.ssp.transferobject.PagingTO;
import org.jasig.ssp.transferobject.PersonChallengeTO;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
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
	PagingTO<PersonChallengeTO, PersonChallenge> getAll(
			@PathVariable final UUID personId,
			final @RequestParam(required = false) ObjectStatus status,
			final @RequestParam(required = false) Integer start,
			final @RequestParam(required = false) Integer limit,
			final @RequestParam(required = false) String sort,
			final @RequestParam(required = false) String sortDirection)
			throws ObjectNotFoundException {
		// Check permissions
		checkPermissionForOp("READ");

		// Validate parameters
		if (status != null && ObjectStatus.DELETED.equals(status)) {
			throw new IllegalArgumentException(
					"You can not request deleted data.");
		}

		// Run getAll for the specified person
		final Person person = personService.get(personId);
		final PagingWrapper<PersonChallenge> data = getService()
				.getAllForPerson(person,
						SortingAndPaging.createForSingleSort(status, start,
								limit, sort, sortDirection, null));

		return new PagingTO<PersonChallengeTO, PersonChallenge>(true,
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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody
	PagingTO<ChallengeTO, Challenge> getChallengesForPerson(
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
				SortingAndPaging.createForSingleSort(status, start,
						limit, sort, sortDirection, null));

		return new PagingTO<ChallengeTO, Challenge>(true, data.getResults(),
				challengeTOFactory.asTOList(data.getRows()));
	}

	@Override
	public String permissionBaseName() {
		return "CHALLENGE";
	}
}
