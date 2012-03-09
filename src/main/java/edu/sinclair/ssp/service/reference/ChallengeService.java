package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;

public interface ChallengeService extends AuditableCrudService<Challenge>{

	public List<Challenge> getAll(ObjectStatus status);

	public Challenge get(UUID id) throws ObjectNotFoundException;

	public Challenge create(Challenge obj);

	public Challenge save(Challenge obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}