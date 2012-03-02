package edu.sinclair.ssp.factory.tool;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.tool.StudentIntakeForm;
import edu.sinclair.ssp.transferobject.tool.StudentIntakeFormTO;

public interface StudentIntakeTOFactory extends TransferObjectFactory<StudentIntakeFormTO, StudentIntakeForm> {

	@Override
	public StudentIntakeFormTO toTO(StudentIntakeForm from);

	@Override
	public StudentIntakeForm toModel(StudentIntakeFormTO from);

	@Override
	public List<StudentIntakeFormTO> toTOList(List<StudentIntakeForm> from);

	@Override
	public List<StudentIntakeForm> toModelList(List<StudentIntakeFormTO> from);

}
