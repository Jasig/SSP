package edu.sinclair.ssp.web.api.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.model.transferobject.ChallengeTO;

@Controller
@RequestMapping("/reference/challenge")
public class ChallengeController implements ReferenceController<ChallengeTO>{

	private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);

	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<ChallengeTO> getAll() throws Exception {

		List<ChallengeTO> challengeTOs = new ArrayList<ChallengeTO>();
		
		challengeTOs.add(new ChallengeTO(UUID.randomUUID(), "Transportation", "Transportation is a challenge for many."));
		challengeTOs.add(new ChallengeTO(UUID.randomUUID(), "Finances", "Financial aid is a challenge for most college students."));
		challengeTOs.add(new ChallengeTO(UUID.randomUUID(), "Child Care", "Child care is expensive and difficult to juggle with college."));
		
		return challengeTOs;
	}
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ChallengeTO get(@PathVariable UUID id) throws Exception {
		return new ChallengeTO(id, "Child Care", "Child care is expensive and difficult to juggle with college.");
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ChallengeTO save(ChallengeTO obj, BindingResult result) throws Exception {
		if(result.hasErrors()){
			logger.debug("There were " + result.getErrorCount() + " errors.");
			return null;
		}
		
		if(null==obj.getDescription()){
			obj.setDescription("Child care is expensive and difficult to juggle with college.");
		}
		return obj;
	}

	@Override
	public void delete(UUID id) throws Exception {
		logger.debug("deleting {}", id.toString());
	}
	
}
