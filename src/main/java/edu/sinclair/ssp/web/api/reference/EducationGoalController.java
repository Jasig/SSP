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

import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.service.reference.EducationGoalService;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/educationGoal")
public class EducationGoalController extends
		ReferenceController<EducationGoal> {

	private static final Logger logger = LoggerFactory.getLogger(EducationGoalController.class);

	@Autowired
	private EducationGoalService service;
	
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<EducationGoal> getAll() throws Exception {
		return service.getAll();
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public EducationGoal get(UUID id) throws Exception {
		return service.get(id);
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public EducationGoal save(EducationGoal obj, BindingResult result)
			throws Exception {
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
