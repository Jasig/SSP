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

import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.service.reference.ChildCareArrangementService;
import edu.sinclair.ssp.transferobject.Form;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/reference/childCareArrangement")
public class ChildCareArrangementController extends ReferenceController<ChildCareArrangement>{

	//private static final Logger logger = LoggerFactory.getLogger(ChildCareArrangementController.class);

	@Autowired
	private ChildCareArrangementService service;
	
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<ChildCareArrangement> getAll() throws Exception {
		return service.getAll();
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody ChildCareArrangement get(@PathVariable UUID id) throws Exception {
		return service.get(id);
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ChildCareArrangement save(@Valid ChildCareArrangement obj) throws Exception {
		return service.save(obj);
	}

	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public boolean delete(@PathVariable UUID id) throws Exception {
		service.delete(id);
		return true;
	}

	@Override
	public Form<ChildCareArrangement> create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
