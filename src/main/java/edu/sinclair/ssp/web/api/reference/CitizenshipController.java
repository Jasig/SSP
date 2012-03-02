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

import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.service.reference.CitizenshipService;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/citizenship")
public class CitizenshipController extends ReferenceController<Citizenship>{

	//private static final Logger logger = LoggerFactory.getLogger(CitizenshipController.class);

	@Autowired
	private CitizenshipService service;

	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<Citizenship> getAll() throws Exception {
		return service.getAll();
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody Citizenship get (@PathVariable UUID id) throws Exception {
		return service.get(id);
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Citizenship save(@Valid Citizenship obj) throws Exception {
		
		return service.save(obj);
	}

	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public void delete(@PathVariable UUID id) throws Exception {
		service.delete(id);
	}

}
