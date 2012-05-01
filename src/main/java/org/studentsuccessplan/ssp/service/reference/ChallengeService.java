package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface ChallengeService extends AuditableCrudService<Challenge> {

	@Override
	PagingWrapper<Challenge> getAll(SortingAndPaging sAndP);

	@Override
	Challenge get(UUID id) throws ObjectNotFoundException;

	@Override
	Challenge create(Challenge obj);

	@Override
	Challenge save(Challenge obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	List<Challenge> challengeSearch(String query);
}