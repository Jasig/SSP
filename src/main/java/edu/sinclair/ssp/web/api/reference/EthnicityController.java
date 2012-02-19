package edu.sinclair.ssp.web.api.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.model.transferobject.EthnicityTO;

@Controller
@RequestMapping("/reference/ethnicity")
public class EthnicityController implements ReferenceController<EthnicityTO>{

	private static final Logger logger = LoggerFactory.getLogger(EthnicityController.class);

	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<EthnicityTO> getAll() throws Exception {

		List<EthnicityTO> ethnicityTOs = new ArrayList<EthnicityTO>();
		
		ethnicityTOs.add(new EthnicityTO(UUID.randomUUID(), "Caucasian"));
		ethnicityTOs.add(new EthnicityTO(UUID.randomUUID(), "Native American"));
		ethnicityTOs.add(new EthnicityTO(UUID.randomUUID(), "African American"));
		
		return ethnicityTOs;
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody EthnicityTO get(@PathVariable UUID id) throws Exception {
		return new EthnicityTO(id, "Caucasian");
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody EthnicityTO save(@Valid EthnicityTO ethnicity) throws Exception {
		return ethnicity;
	}
	
	@Override
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public void delete(@PathVariable UUID id) throws Exception {
		logger.debug("deleting {}", id.toString());
	}
	
}
