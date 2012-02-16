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

import edu.sinclair.ssp.model.transferobject.ChallengeTO;
import edu.sinclair.ssp.model.transferobject.FundingSourceTO;

@Controller
@RequestMapping("/reference/fundingSource")
public class FundingSourceController {

	private static final Logger logger = LoggerFactory.getLogger(FundingSourceController.class);

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<FundingSourceTO> getAll() throws Exception {

		List<FundingSourceTO> fundingSourceTOs = new ArrayList<FundingSourceTO>();
		
		fundingSourceTOs.add(new FundingSourceTO(UUID.randomUUID().toString(), "Self", "You are funding college yourself."));
		fundingSourceTOs.add(new FundingSourceTO(UUID.randomUUID().toString(), "Family", "Family including spouse, parents, etc are funding your education."));
		fundingSourceTOs.add(new FundingSourceTO(UUID.randomUUID().toString(), "Employer", "Your employer is assisting with funding your education."));
		
		return fundingSourceTOs;
	}
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody FundingSourceTO getAll(@PathVariable String id) throws Exception {
		return new FundingSourceTO(UUID.randomUUID().toString(), "Loans", "Funding of education is being completed through bank loans.");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody FundingSourceTO save(@RequestParam("name") String name, @RequestParam("description") String description) throws Exception {
		return new FundingSourceTO(UUID.randomUUID().toString(), "Pell Grant", "Pell grant funding assists with college costs.");
	}
	
}
