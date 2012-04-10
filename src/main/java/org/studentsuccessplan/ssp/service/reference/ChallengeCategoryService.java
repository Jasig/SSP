package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.ChallengeCategory;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;

public interface ChallengeCategoryService extends AuditableCrudService<ChallengeCategory> {

	@Override
	public List<ChallengeCategory> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public ChallengeCategory get(UUID id) throws ObjectNotFoundException;

	@Override
	public ChallengeCategory create(ChallengeCategory obj);

	@Override
	public ChallengeCategory save(ChallengeCategory obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
