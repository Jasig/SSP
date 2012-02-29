package edu.sinclair.ssp.service.reference;

import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.StudentStatus;

public interface StudentStatusService {

	public List<StudentStatus> getAll();

	public StudentStatus get(UUID id);

	public StudentStatus save(StudentStatus obj);

	public void delete(UUID id);

}