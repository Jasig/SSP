package edu.sinclair.ssp.factory.tool.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.factory.tool.StudentIntakeTOFactory;
import edu.sinclair.ssp.model.tool.StudentIntakeForm;
import edu.sinclair.ssp.transferobject.tool.StudentIntakeFormTO;

@Service
@Transactional(readOnly = true)
public class StudentIntakeTOFactoryImpl implements StudentIntakeTOFactory {

	@Override
	public StudentIntakeFormTO toTO(StudentIntakeForm from) {
		return null;
	}

	@Override
	public StudentIntakeForm toModel(StudentIntakeFormTO from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentIntakeFormTO> toTOList(List<StudentIntakeForm> from) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentIntakeForm> toModelList(List<StudentIntakeFormTO> from) {
		// TODO Auto-generated method stub
		return null;
	}

}
