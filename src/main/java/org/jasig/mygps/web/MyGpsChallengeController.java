package org.jasig.mygps.web;

import java.util.List;

import org.jasig.ssp.factory.reference.ChallengeReferralTOFactory;
import org.jasig.ssp.factory.reference.ChallengeTOFactory;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.service.SecurityService;
import org.jasig.ssp.service.reference.ChallengeReferralService;
import org.jasig.ssp.service.reference.ChallengeService;
import org.jasig.ssp.transferobject.reference.ChallengeTO;
import org.jasig.ssp.web.api.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
@RequestMapping("/1/mygps/challenge")
public class MyGpsChallengeController extends AbstractBaseController {

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient ChallengeService challengeService;

	@Autowired
	private transient ChallengeReferralService challengeReferralService;

	@Autowired
	private transient ChallengeReferralTOFactory challengeReferralTOFactory;

	@Autowired
	private transient ChallengeTOFactory challengeTOFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsChallengeController.class);

	/**
	 * Empty constructor
	 */
	public MyGpsChallengeController() {
		super();
	}

	// Needed for tests, will be removed in the future.
	public MyGpsChallengeController(final ChallengeService challengeService) {
		super();
		this.challengeService = challengeService;
	}

	/**
	 * Retrieve all applicable, visible Challenges for the specified query, that
	 * are not already assigned as Tasks for the current user.
	 * <p>
	 * Also filters out inactive Challenges, and those that are not marked to
	 * show in the SelfHelpSearch.
	 * 
	 * @param query
	 *            Text string to compare with a SQL LIKE clause on the
	 *            SelfHelpGuide Question, Description, and Tags fields
	 * @return All Challenges that match the specified criteria
	 * @throws Exception
	 *             If there were any unexpected exceptions thrown.
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody
	List<ChallengeTO> search(@RequestParam("query") final String query)
			throws Exception {
		try {
			final List<Challenge> challenges = challengeService
					.challengeSearch(query);

			final List<ChallengeTO> challengeTOs = Lists.newArrayList();

			for (Challenge challenge : challenges) {
				ChallengeTO challengeTO = challengeTOFactory.from(challenge);

				List<ChallengeReferral> referrals = challengeReferralService
						.byChallengeIdNotOnActiveTaskList(challenge,
								securityService.currentUser().getPerson(),
								securityService.getSessionId());
				challengeTO
						.setChallengeChallengeReferrals(challengeReferralTOFactory
								.asTOList(referrals));
				challengeTOs.add(challengeTO);
			}

			// TODO: (performance) Challenge search service does the
			// byChallengeIdNotOnActiveTaskList lookup already but doesn't
			// return the TOs so it has to be done again here. Or better yet,
			// done as a database set operation instead.

			return challengeTOs;
		} catch (Exception e) {
			LOGGER.error("ERROR : search() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
