package org.studentsuccessplan.mygps.web;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.mygps.business.SelfHelpGuideManager;
import org.studentsuccessplan.mygps.model.transferobject.ChallengeReferralTO;

@Controller
@RequestMapping("/mygps/challengereferral")
public class MyGpsChallengeReferralController extends AbstractMyGpsController {

	@Autowired
	private SelfHelpGuideManager manager;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsChallengeReferralController.class);

	protected void setManager(SelfHelpGuideManager manager) {
		this.manager = manager;
	}

	@RequestMapping(value = "/getByChallengeId", method = RequestMethod.GET)
	public @ResponseBody
	List<ChallengeReferralTO> getByChallengeId(
			@RequestParam("challengeId") UUID challengeId) throws Exception {

		try {
			return manager.getChallengeReferralsByChallengeId(challengeId);
		} catch (Exception e) {
			LOGGER.error("ERROR : getByChallengeId() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody
	List<ChallengeReferralTO> search(@RequestParam("query") String query,
			@RequestParam("challengeId") UUID challengeId) throws Exception {

		// Not using query param as we are no longer searching the
		// referrals, simply returning based on challengeId and whether the
		// referral is public.

		try {
			return manager.challengeReferralSearch(challengeId);
		} catch (Exception e) {
			LOGGER.error("ERROR : search() : {}", e.getMessage(), e);
			throw e;
		}
	}
}
