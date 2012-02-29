package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.service.reference.EducationGoalService;
import edu.sinclair.ssp.service.reference.ReferenceService;

@Service
public class EducationGoalServiceImpl implements ReferenceService<EducationGoal>, EducationGoalService {

	private static final Logger logger = LoggerFactory.getLogger(EducationGoalServiceImpl.class);

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
