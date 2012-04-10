package org.studentsuccessplan.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.dao.reference.StudentStatusDao;
import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.reference.StudentStatus;
import org.studentsuccessplan.ssp.service.ObjectNotFoundException;
import org.studentsuccessplan.ssp.service.reference.StudentStatusService;

@Service
@Transactional
public class StudentStatusServiceImpl implements StudentStatusService {

	@Autowired
	private StudentStatusDao dao;

	@Override
	public List<StudentStatus> getAll(ObjectStatus status, Integer firstResult,
			Integer maxResults, String sort, String sortDirection) {
		return dao.getAll(status, firstResult, maxResults, sort, sortDirection);
	}

	@Override
	public StudentStatus get(UUID id) throws ObjectNotFoundException {
		StudentStatus obj = dao.get(id);
		if (null == obj) {
			throw new ObjectNotFoundException(id, "StudentStatus");
		}

		return obj;
	}

	@Override
	public StudentStatus create(StudentStatus obj) {
		return dao.save(obj);
	}

	@Override
	public StudentStatus save(StudentStatus obj) throws ObjectNotFoundException {
		StudentStatus current = get(obj.getId());

		current.setName(obj.getName());
		current.setDescription(obj.getDescription());
		current.setObjectStatus(obj.getObjectStatus());

		return dao.save(current);
	}

	@Override
	public void delete(UUID id) throws ObjectNotFoundException {
		StudentStatus current = get(id);

		if (null != current) {
			current.setObjectStatus(ObjectStatus.DELETED);
			save(current);
		}
	}

	protected void setDao(StudentStatusDao dao) {
		this.dao = dao;
	}
}
