package edu.sinclair.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.service.reference.ChildCareArrangementService;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/childCareArrangement")
public class ChildCareArrangementController extends ReferenceController<ChildCareArrangement>{

	private static final Logger logger = LoggerFactory.getLogger(ChildCareArrangementController.class);

	@Autowired
	private ChildCareArrangementService service;
	
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<ChildCareArrangement> getAll() throws Exception {
		return service.getAll();
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public ChildCareArrangement get(UUID id) throws Exception {
		return service.get(id);
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ChildCareArrangement save(ChildCareArrangement obj,
			BindingResult result) throws Exception {
		if(result.hasErrors()){
			logger.debug("There were " + result.getErrorCount() + " errors.");
			return null;
		}
		
		return service.save(obj);
	}

	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public void delete(UUID id) throws Exception {
		service.delete(id);
	}

}
