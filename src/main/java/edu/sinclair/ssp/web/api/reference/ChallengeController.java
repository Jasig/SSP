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

import edu.sinclair.ssp.factory.reference.ChallengeTOFactory;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.transferobject.ServiceResponse;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;
import edu.sinclair.ssp.web.api.RestController;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/challenge")
public class ChallengeController extends RestController<ChallengeTO>{

	private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);

	@Autowired
	private ChallengeService service;
	
	@Autowired
	private ChallengeTOFactory toFactory;
	
	@Override
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody List<ChallengeTO> getAll() throws Exception {
		return toFactory.toTOList(service.getAll());
	}
	
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ChallengeTO get(@PathVariable UUID id) throws Exception {
		Challenge challenge = service.get(id);
		if(challenge!=null){
			return toFactory.toTO(challenge);
		}else{
			return null;
		}
	}
	
	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ChallengeTO create(@Valid @RequestBody ChallengeTO obj) throws Exception {
		Challenge challenge = toFactory.toModel(obj);
		if(null!=challenge){
			Challenge savedChallenge = service.save(challenge);
			if(null!=challenge){
				return toFactory.toTO(savedChallenge);
			}
		}
		return null;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody ChallengeTO save(@PathVariable UUID id, @Valid @RequestBody ChallengeTO obj) throws Exception {
		Challenge model = toFactory.toModel(obj);
		model.setId(id);
		Challenge savedChallenge = service.save(model);
		if(null!=savedChallenge){
			return toFactory.toTO(savedChallenge);
		}
		return null;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ServiceResponse delete(@PathVariable UUID id) throws Exception {
		service.delete(id);
		return new ServiceResponse(true);
	}
	
	@Override
	@ExceptionHandler(Exception.class)
	public @ResponseBody ServiceResponse handle(Exception e){
		logger.error("Error: ", e);
		return new ServiceResponse(false, e.getMessage());
	}
}