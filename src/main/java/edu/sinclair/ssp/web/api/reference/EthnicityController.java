package edu.sinclair.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.service.reference.EthnicityService;
import edu.sinclair.ssp.web.api.RestController;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/ethnicity")
public class EthnicityController extends RestController<Ethnicity>{

	//private static final Logger logger = LoggerFactory.getLogger(EthnicityController.class);

	@Autowired
	private EthnicityService service;
	
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Ethnicity> getAll() throws Exception {
		return service.getAll();
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody Ethnicity get(@PathVariable UUID id) throws Exception {
		return service.get(id);
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody boolean save(@PathVariable UUID id, @Valid Ethnicity ethnicity) throws Exception {
		service.save(ethnicity);
		return true;
	}
	
	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public boolean delete(@PathVariable UUID id) throws Exception {
		service.delete(id);
		return true;
	}

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody boolean create(@Valid Ethnicity ethnicity) throws Exception {
		service.save(ethnicity);
		return true;
	}
	
}
