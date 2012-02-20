package edu.sinclair.ssp.service.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.sinclair.ssp.model.reference.Challenge;

@Service
public class ChallengeService implements ReferenceService<Challenge> {

	private static final Logger logger = LoggerFactory.getLogger(ChallengeService.class);

	@Override
	public List<Challenge> getAll() {
		List<Challenge> challenges = new ArrayList<Challenge>();
		
		challenges.add(new Challenge(UUID.randomUUID(), "Transportation", "Transportation is a challenge for many."));
		challenges.add(new Challenge(UUID.randomUUID(), "Finances", "Financial aid is a challenge for most college students."));
		challenges.add(new Challenge(UUID.randomUUID(), "Child Care", "Child care is expensive and difficult to juggle with college."));
		
		return challenges;
	}

	@Override
	public Challenge get(UUID id) {
		return new Challenge(id, "Child Care", "Child care is expensive and difficult to juggle with college.");
	}

	@Override
	public Challenge save(Challenge obj) {
		if(null==obj.getDescription()){
			obj.setDescription("Child care is expensive and difficult to juggle with college.");
		}
		return obj;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
