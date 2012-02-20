package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.ChildCareArrangement;

@Service
public class ChildCareArrangementService implements
		ReferenceService<ChildCareArrangement> {

	private static final Logger logger = LoggerFactory.getLogger(ChildCareArrangementService.class);

	@Override
	public List<ChildCareArrangement> getAll() {
		List<ChildCareArrangement> all = Lists.newArrayList();
		
		all.add(new ChildCareArrangement(UUID.randomUUID(), "Daycare"));
		all.add(new ChildCareArrangement(UUID.randomUUID(), "Home Provider"));
		all.add(new ChildCareArrangement(UUID.randomUUID(), "Family / Friend"));
		
		return all;
	}

	@Override
	public ChildCareArrangement get(UUID id) {
		return new ChildCareArrangement(id, "Daycare");
	}

	@Override
	public ChildCareArrangement save(ChildCareArrangement obj) {
		return obj;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
