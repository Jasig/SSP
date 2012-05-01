package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.EducationLevel;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

public interface EducationLevelService extends
		AuditableCrudService<EducationLevel> {

	@Override
	public PagingWrapper<EducationLevel> getAll(SortingAndPaging sAndP);

	@Override
	public EducationLevel get(UUID id) throws ObjectNotFoundException;

	@Override
	public EducationLevel create(EducationLevel obj);

	@Override
	public EducationLevel save(EducationLevel obj)
			throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
