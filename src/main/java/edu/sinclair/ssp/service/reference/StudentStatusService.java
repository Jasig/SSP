package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.AuditableCrudService;

public interface StudentStatusService extends AuditableCrudService<StudentStatus>{

	public List<StudentStatus> getAll(ObjectStatus status);

	public StudentStatus get(UUID id) throws ObjectNotFoundException;

	public StudentStatus create(StudentStatus obj);

	public StudentStatus save(StudentStatus obj) throws ObjectNotFoundException;

	public void delete(UUID id) throws ObjectNotFoundException;

}
