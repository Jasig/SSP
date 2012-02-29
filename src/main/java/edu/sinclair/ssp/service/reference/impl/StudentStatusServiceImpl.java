package edu.sinclair.ssp.service.reference.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.service.reference.ReferenceService;
import edu.sinclair.ssp.service.reference.StudentStatusService;

@Service
public class StudentStatusServiceImpl implements ReferenceService<StudentStatus>, StudentStatusService {

	private static final Logger logger = LoggerFactory.getLogger(StudentStatusServiceImpl.class);

	@Override
	public List<StudentStatus> getAll() {
		List<StudentStatus> all = Lists.newArrayList();
		
		all.add(new StudentStatus(UUID.randomUUID(), "New"));
		all.add(new StudentStatus(UUID.randomUUID(), "Current"));
		all.add(new StudentStatus(UUID.randomUUID(), "Former"));
		
		return all;
	}

	@Override
	public StudentStatus get(UUID id) {
		return new StudentStatus(id, "New");
	}

	@Override
	public StudentStatus save(StudentStatus obj) {
		return obj;
	}

	@Override
	public void delete(UUID id) {
		logger.debug("deleting {}", id.toString());
	}

}
