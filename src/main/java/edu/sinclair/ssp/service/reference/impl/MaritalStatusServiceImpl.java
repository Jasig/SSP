package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.service.reference.MaritalStatusService;
import edu.sinclair.ssp.service.reference.ReferenceService;

@Service
public class MaritalStatusServiceImpl implements ReferenceService<MaritalStatus>, MaritalStatusService {

	private static final Logger logger = LoggerFactory.getLogger(MaritalStatusServiceImpl.class);

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
