package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.EducationGoalTOFactory;
import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.transferobject.reference.EducationGoalTO;

@Service
public class EducationGoalTOFactoryImpl 
			extends AbstractTransferObjectFactory<EducationGoalTO, EducationGoal>
			implements EducationGoalTOFactory{

	@Override
	public EducationGoalTO toTO(EducationGoal from) {
		
		EducationGoalTO to = new EducationGoalTO();

		to.fromModel(from);
		
		return to;
	}

	@Override
	public EducationGoal toModel(EducationGoalTO from) {
		EducationGoal model = new EducationGoal();

		from.addToModel(model);
		
		return model;
	}

}
