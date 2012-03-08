package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.transferobject.reference.EducationGoalTO;

public interface EducationGoalTOFactory extends TransferObjectFactory<EducationGoalTO, EducationGoal> {

	@Override
	public EducationGoalTO toTO(EducationGoal from);

	@Override
	public EducationGoal toModel(EducationGoalTO from);

	@Override
	public List<EducationGoalTO> toTOList(List<EducationGoal> from);

	@Override
	public List<EducationGoal> toModelList(List<EducationGoalTO> from);

}
