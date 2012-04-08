package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface ChallengeService extends AuditableCrudService<Challenge> {

	@Override
	List<Challenge> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	Challenge get(UUID id) throws ObjectNotFoundException;

	@Override
	Challenge create(Challenge obj);

	@Override
	Challenge save(Challenge obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}