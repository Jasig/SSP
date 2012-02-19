package edu.sinclair.ssp.web.api.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sinclair.ssp.model.transferobject.FundingSourceTO;

@Controller
@RequestMapping("/reference/fundingSource")
public class FundingSourceController implements ReferenceController<FundingSourceTO>{

	private static final Logger logger = LoggerFactory.getLogger(FundingSourceController.class);

	@Override
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public @ResponseBody List<FundingSourceTO> getAll() throws Exception {

		List<FundingSourceTO> fundingSourceTOs = new ArrayList<FundingSourceTO>();
		
		fundingSourceTOs.add(new FundingSourceTO(UUID.randomUUID(), "Self", "You are funding college yourself."));
		fundingSourceTOs.add(new FundingSourceTO(UUID.randomUUID(), "Family", "Family including spouse, parents, etc are funding your education."));
		fundingSourceTOs.add(new FundingSourceTO(UUID.randomUUID(), "Employer", "Your employer is assisting with funding your education."));
		
		return fundingSourceTOs;
	}
	
	@Override
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public @ResponseBody FundingSourceTO get(@PathVariable UUID id) throws Exception {
		return new FundingSourceTO(id, "Loans", "Funding of education is being completed through bank loans.");
	}
	
	@Override
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody FundingSourceTO save(FundingSourceTO obj, BindingResult result) throws Exception {
		if(result.hasErrors()){
			logger.debug("There were " + result.getErrorCount() + " errors.");
			return null;
		}
		
		if(null==obj.getDescription()){
			obj.setDescription("Pell grant funding assists with college costs.");
		}
		return obj;
	}

	@Override
	public void delete(UUID id) throws Exception {
		logger.debug("deleting {}", id.toString());
		
	}
	
}
