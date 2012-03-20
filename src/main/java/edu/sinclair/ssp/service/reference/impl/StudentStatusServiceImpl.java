package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.dao.reference.StudentStatusDao;
import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.service.ObjectNotFoundException;
import edu.sinclair.ssp.service.reference.StudentStatusService;

@Service
@Transactional
public class StudentStatusServiceImpl implements StudentStatusService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(StudentStatusServiceImpl.class);

	@Autowired
	private StudentStatusDao dao;

	@Override
	public List<StudentStatus> getAll(ObjectStatus status) {
		return dao.getAll(status);
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

		if (obj.getName() != null) {
			current.setName(obj.getName());
		}
		if (obj.getDescription() != null) {
			current.setDescription(obj.getDescription());
		}
		if (obj.getObjectStatus() != null) {
			current.setObjectStatus(obj.getObjectStatus());
		}

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
