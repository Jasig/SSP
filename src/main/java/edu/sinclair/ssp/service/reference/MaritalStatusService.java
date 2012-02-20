package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.MaritalStatus;

@Service
public class MaritalStatusService implements ReferenceService<MaritalStatus> {

	private static final Logger logger = LoggerFactory.getLogger(MaritalStatusService.class);

	@Override
	public List<MaritalStatus> getAll() {
		List<MaritalStatus> all = Lists.newArrayList();
		
		all.add(new MaritalStatus(UUID.randomUUID(), "Married"));
		all.add(new MaritalStatus(UUID.randomUUID(), "Single"));
		
		return all;
	}

	@Override
	public MaritalStatus get(UUID id) {
		return new MaritalStatus(id, "Married");
	}

	@Override
	public MaritalStatus save(MaritalStatus obj) {
		return obj;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
