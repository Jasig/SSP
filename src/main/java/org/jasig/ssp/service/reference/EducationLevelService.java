package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.EducationLevel;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

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
