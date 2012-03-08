package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.transferobject.reference.StudentStatusTO;

public interface StudentStatusTOFactory extends TransferObjectFactory<StudentStatusTO, StudentStatus> {

	@Override
	public StudentStatusTO toTO(StudentStatus from);

	@Override
	public StudentStatus toModel(StudentStatusTO from);

	@Override
	public List<StudentStatusTO> toTOList(List<StudentStatus> from);

	@Override
	public List<StudentStatus> toModelList(List<StudentStatusTO> from);

}
