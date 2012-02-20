package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.FundingSource;

@Service
public class FundingSourceService implements ReferenceService<FundingSource> {

	private static final Logger logger = LoggerFactory.getLogger(FundingSourceService.class);
	
	@Override
	public List<FundingSource> getAll() {
		List<FundingSource> fundingSources = Lists.newArrayList();
		
		fundingSources.add(new FundingSource(UUID.randomUUID(), "Self", "You are funding college yourself."));
		fundingSources.add(new FundingSource(UUID.randomUUID(), "Family", "Family including spouse, parents, etc are funding your education."));
		fundingSources.add(new FundingSource(UUID.randomUUID(), "Employer", "Your employer is assisting with funding your education."));
		
		return fundingSources;
	}

	@Override
	public FundingSource get(UUID id) {
		return new FundingSource(id, "Loans", "Funding of education is being completed through bank loans.");
	}

	@Override
	public FundingSource save(FundingSource obj) {
		if(null==obj.getDescription()){
			obj.setDescription("Pell grant funding assists with college costs.");
		}
		return obj;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
