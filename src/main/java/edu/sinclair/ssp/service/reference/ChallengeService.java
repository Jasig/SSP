package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.Challenge;

public interface ChallengeService {

	public List<Challenge> getAll();

	public Challenge get(UUID id);

	public Challenge save(Challenge obj);

	public void delete(UUID id);

}