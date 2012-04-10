package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.service.AuditableCrudService;
import edu.sinclair.ssp.service.ObjectNotFoundException;

public interface StudentStatusService extends AuditableCrudService<StudentStatus> {

	@Override
	public List<StudentStatus> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection);

	@Override
	public StudentStatus get(UUID id) throws ObjectNotFoundException;

	@Override
	public StudentStatus create(StudentStatus obj);

	@Override
	public StudentStatus save(StudentStatus obj) throws ObjectNotFoundException;

	@Override
	public void delete(UUID id) throws ObjectNotFoundException;

}
