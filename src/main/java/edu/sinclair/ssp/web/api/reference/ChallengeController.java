package edu.sinclair.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.model.transferobject.factory.IChallengeTOFactory;
import edu.sinclair.ssp.model.transferobject.reference.ChallengeTO;
import edu.sinclair.ssp.service.reference.ChallengeService;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/challenge")
public class ChallengeController extends ReferenceController<ChallengeTO>{

	private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);

	@Autowired
	private ChallengeService service;
	
	@Autowired
	private IChallengeTOFactory toFactory;
	
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<ChallengeTO> getAll() throws Exception {
		return toFactory.toTOList(service.getAll());
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody ChallengeTO get(@PathVariable UUID id) throws Exception {
		return toFactory.toTO(service.get(id));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ChallengeTO save(@Valid @RequestBody ChallengeTO obj) throws Exception {
		Challenge model = toFactory.toModel(obj);
		Challenge saveResult = service.save(model);
		return toFactory.toTO(saveResult);
	}

	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public void delete(@PathVariable UUID id) throws Exception {
		service.delete(id);
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody Exception handle(Exception e){
		logger.error("Error: ", e);
		return e;
	}
}