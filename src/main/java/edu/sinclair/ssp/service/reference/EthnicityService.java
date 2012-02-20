package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.Ethnicity;

@Service
public class EthnicityService implements ReferenceService<Ethnicity> {

	private static final Logger logger = LoggerFactory.getLogger(EthnicityService.class);

	@Override
	public List<Ethnicity> getAll() {
		List<Ethnicity> ethnicities = Lists.newArrayList();
		
		ethnicities.add(new Ethnicity(UUID.randomUUID(), "Caucasian"));
		ethnicities.add(new Ethnicity(UUID.randomUUID(), "Native American"));
		ethnicities.add(new Ethnicity(UUID.randomUUID(), "African American"));
		
		return ethnicities;
	}

	@Override
	public Ethnicity get(UUID id) {
		return new Ethnicity(id, "Caucasian");
	}

	@Override
	public Ethnicity save(Ethnicity ethnicity) {
		return ethnicity;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
