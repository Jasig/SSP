package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.StudentStatusTOFactory;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.transferobject.reference.StudentStatusTO;

@Service
public class StudentStatusTOFactoryImpl 
			extends AbstractTransferObjectFactory<StudentStatusTO, StudentStatus>
			implements StudentStatusTOFactory{

	@Override
	public StudentStatusTO toTO(StudentStatus from) {
		
		StudentStatusTO to = new StudentStatusTO();

		to.fromModel(from);
		
		return to;
	}

	@Override
	public StudentStatus toModel(StudentStatusTO from) {
		StudentStatus model = new StudentStatus();

		from.addToModel(model);
		
		return model;
	}

}
