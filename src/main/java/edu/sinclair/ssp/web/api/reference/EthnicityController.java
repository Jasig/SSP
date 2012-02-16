package edu.sinclair.ssp.web.api.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.model.transferobject.EthnicityTO;

@Controller
@RequestMapping("/api/reference/ethnicity")
public class EthnicityController {

	private static final Logger logger = LoggerFactory.getLogger(EthnicityController.class);

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<EthnicityTO> getAll() throws Exception {

		List<EthnicityTO> ethnicityTOs = new ArrayList<EthnicityTO>();
		
		ethnicityTOs.add(new EthnicityTO(UUID.randomUUID().toString(), "Caucasian"));
		ethnicityTOs.add(new EthnicityTO(UUID.randomUUID().toString(), "Native American"));
		ethnicityTOs.add(new EthnicityTO(UUID.randomUUID().toString(), "African American"));
		
		return ethnicityTOs;
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody EthnicityTO get(@PathVariable String id) throws Exception {
		return new EthnicityTO(UUID.randomUUID().toString(), "Caucasian");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody EthnicityTO save(@RequestParam("name") String name, @RequestParam("description") String description) throws Exception {
		return new EthnicityTO(UUID.randomUUID().toString(), "Caucasian");
	}
	
}
