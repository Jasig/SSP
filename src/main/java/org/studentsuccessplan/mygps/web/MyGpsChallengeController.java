package org.studentsuccessplan.mygps.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.studentsuccessplan.ssp.service.reference.ChallengeService;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

@Controller
@RequestMapping("/mygps/challenge")
public class MyGpsChallengeController extends AbstractMyGpsController {

	@Autowired
	private ChallengeService challengeService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MyGpsChallengeController.class);

	public MyGpsChallengeController() {
	}

	public MyGpsChallengeController(ChallengeService challengeService) {
		this.challengeService = challengeService;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody
	List<ChallengeTO> search(@RequestParam("query") String query)
			throws Exception {

		try {
			return ChallengeTO.listToTOList(challengeService
					.challengeSearch(query));
		} catch (Exception e) {
			LOGGER.error("ERROR : search() : {}", e.getMessage(), e);
			throw e;
		}
	}
}
