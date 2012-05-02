package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.StudentStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

public interface StudentStatusService extends
		AuditableCrudService<StudentStatus> {

	@Override
	public PagingWrapper<StudentStatus> getAll(SortingAndPaging sAndP);

	@Override
	public StudentStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	public StudentStatus create(StudentStatus obj);

	@Override
	public StudentStatus save(StudentStatus obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
