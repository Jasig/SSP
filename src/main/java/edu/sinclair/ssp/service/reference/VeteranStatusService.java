package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.VeteranStatus;

@Service
public class VeteranStatusService implements ReferenceService<VeteranStatus> {

	private static final Logger logger = LoggerFactory.getLogger(VeteranStatusService.class);

	@Override
	public List<VeteranStatus> getAll() {
		List<VeteranStatus> statuses = Lists.newArrayList();
		
		statuses.add(new VeteranStatus(UUID.randomUUID(), "Veteran"));
		statuses.add(new VeteranStatus(UUID.randomUUID(), "Dependent of a Veteran"));
		
		return statuses;
	}

	@Override
	public VeteranStatus get(UUID id) {
		return new VeteranStatus(id, "Veteran");
	}

	@Override
	public VeteranStatus save(VeteranStatus obj) {
		return obj;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
