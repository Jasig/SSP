package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.service.reference.CitizenshipService;
import edu.sinclair.ssp.service.reference.ReferenceService;

@Service
public class CitizenshipServiceImpl implements ReferenceService<Citizenship>, CitizenshipService {

	private static final Logger logger = LoggerFactory.getLogger(CitizenshipServiceImpl.class);

	@Override
	public List<Citizenship> getAll() {
		List<Citizenship> all = Lists.newArrayList();
		
		all.add(new Citizenship(UUID.randomUUID(), "US Citizen"));
		all.add(new Citizenship(UUID.randomUUID(), "International"));
		all.add(new Citizenship(UUID.randomUUID(), "Permanent Resident"));
		
		return all;
	}

	@Override
	public Citizenship get(UUID id) {
		return new Citizenship(id, "US Citizen");
	}

	@Override
	public Citizenship save(Citizenship obj) {
		return obj;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
