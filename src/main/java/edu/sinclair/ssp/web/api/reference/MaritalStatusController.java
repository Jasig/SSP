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

import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.service.reference.MaritalStatusService;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/maritalStatus")
public class MaritalStatusController extends
		ReferenceController<MaritalStatus> {

	//private static final Logger logger = LoggerFactory.getLogger(MaritalStatusController.class);

	@Autowired
	private MaritalStatusService service;
	
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<MaritalStatus> getAll() throws Exception {
		return service.getAll();
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody MaritalStatus get(@PathVariable UUID id) throws Exception {
		return service.get(id);
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody MaritalStatus save(@Valid MaritalStatus obj)
			throws Exception {
		return service.save(obj);
	}

	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public void delete(@PathVariable UUID id) throws Exception {
		service.delete(id);
	}

}
