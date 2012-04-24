package org.studentsuccessplan.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChallengeCategory;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface ChallengeCategoryService extends AuditableCrudService<ChallengeCategory> {

	@Override
	public List<ChallengeCategory> getAll(SortingAndPaging sAndP);

	@Override
	public ChallengeCategory get(UUID id) throws ObjectNotFoundException;

	@Override
	public ChallengeCategory create(ChallengeCategory obj);

	@Override
	public ChallengeCategory save(ChallengeCategory obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
