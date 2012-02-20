package edu.sinclair.ssp.web.api.reference;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.service.reference.VeteranStatusService;

@Controller
@RequestMapping("/reference/veteranStatus")
public class VeteranStatusController implements ReferenceController<VeteranStatus>{

	private static final Logger logger = LoggerFactory.getLogger(VeteranStatusController.class);

	@Autowired
	private VeteranStatusService service;
	
	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<VeteranStatus> getAll() throws Exception {
		return service.getAll();
	}

	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public VeteranStatus get(UUID id) throws Exception {
		return service.get(id);
	}

	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public VeteranStatus save(VeteranStatus obj, BindingResult result)
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
