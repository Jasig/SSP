package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.StudentStatus;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;

/**
 * StudentStatus service
 */
public interface StudentStatusService extends
		AuditableCrudService<StudentStatus> {

	@Override
	PagingWrapper<StudentStatus> getAll(SortingAndPaging sAndP);

	@Override
	StudentStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	StudentStatus create(StudentStatus obj) throws ObjectNotFoundException;

	@Override
	StudentStatus save(StudentStatus obj) throws ObjectNotFoundException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;
}
