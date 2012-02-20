package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.EducationGoal;

public class EducationGoalService implements ReferenceService<EducationGoal> {

	private static final Logger logger = LoggerFactory.getLogger(EducationGoalService.class);

	@Override
	public List<EducationGoal> getAll() {
		List<EducationGoal> all = Lists.newArrayList();
		
		all.add(new EducationGoal(UUID.randomUUID(), "goal 1"));
		all.add(new EducationGoal(UUID.randomUUID(), "goal 2"));
		all.add(new EducationGoal(UUID.randomUUID(), "goal 3"));
		
		return all;
	}

	@Override
	public EducationGoal get(UUID id) {
		return new EducationGoal(id, "goal 1");
	}

	@Override
	public EducationGoal save(EducationGoal obj) {
		return obj;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
