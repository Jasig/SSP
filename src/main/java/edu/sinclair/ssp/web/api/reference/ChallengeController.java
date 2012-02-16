package edu.sinclair.ssp.web.api.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.model.transferobject.ChallengeTO;

@Controller
@RequestMapping("/reference/challenge")
public class ChallengeController {

	private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<ChallengeTO> getAll() throws Exception {

		List<ChallengeTO> challengeTOs = new ArrayList<ChallengeTO>();
		
		challengeTOs.add(new ChallengeTO(UUID.randomUUID().toString(), "Transportation", "Transportation is a challenge for many."));
		challengeTOs.add(new ChallengeTO(UUID.randomUUID().toString(), "Finances", "Financial aid is a challenge for most college students."));
		challengeTOs.add(new ChallengeTO(UUID.randomUUID().toString(), "Child Care", "Child care is expensive and difficult to juggle with college."));
		
		return challengeTOs;
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody ChallengeTO getAll(@PathVariable String id) throws Exception {
		return new ChallengeTO(UUID.randomUUID().toString(), "Child Care", "Child care is expensive and difficult to juggle with college.");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ChallengeTO save(@RequestParam("name") String name, @RequestParam("description") String description) throws Exception {
		return new ChallengeTO(UUID.randomUUID().toString(), "Child Care", "Child care is expensive and difficult to juggle with college.");
	}
	
}
