package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EducationGoal;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface EducationGoalService extends
		AuditableCrudService<EducationGoal> {

	@Override
	public PagingWrapper<EducationGoal> getAll(SortingAndPaging sAndP);

	@Override
	public EducationGoal get(UUID id) throws ObjectNotFoundException;

	@Override
	public EducationGoal create(EducationGoal obj);

	@Override
	public EducationGoal save(EducationGoal obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
