package org.studentsuccessplan.ssp.service.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.StudentStatus;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.util.sort.PagingWrapper;
import org.studentsuccessplan.ssp.util.sort.SortingAndPaging;

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
