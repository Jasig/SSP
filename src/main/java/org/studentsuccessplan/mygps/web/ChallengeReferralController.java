package edu.sinclair.mygps.web;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.mygps.business.SelfHelpGuideManager;
import edu.sinclair.mygps.model.transferobject.ChallengeReferralTO;

@Controller
@RequestMapping("/challengereferral")
public class ChallengeReferralController {

	@Autowired
	private SelfHelpGuideManager manager;

	private Logger logger = LoggerFactory.getLogger(ChallengeReferralController.class);

	// Required for tests
	public void setManager(SelfHelpGuideManager manager) {
		this.manager = manager;
	}

	@RequestMapping(value="/getByChallengeId", method = RequestMethod.GET)
	public @ResponseBody
	List<ChallengeReferralTO> getByChallengeId(
			@RequestParam("challengeId") UUID challengeId) throws Exception {

		try {
			return manager.getChallengeReferralsByChallengeId(challengeId);
		} catch (Exception e) {
			logger.error("ERROR : getByChallengeId() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@RequestMapping(value="/search", method = RequestMethod.GET)
	public @ResponseBody
	List<ChallengeReferralTO> search(@RequestParam("query") String query,
			@RequestParam("challengeId") UUID challengeId) throws Exception {

		// TODO: Not using query param as we are no longer searching the referrals, simply returning based on challengeId and whether the referral is public.

		try {
			return manager.challengeReferralSearch(challengeId);
		} catch (Exception e) {
			logger.error("ERROR : search() : {}", e.getMessage(), e);
			throw e;
		}
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody String handleException(Exception e, HttpServletResponse response) {
		logger.error("ERROR : handleException()", e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return e.getMessage();
	}

}
