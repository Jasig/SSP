package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.EducationLevelTOFactory;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.transferobject.reference.EducationLevelTO;

@Service
public class EducationLevelTOFactoryImpl 
			extends AbstractTransferObjectFactory<EducationLevelTO, EducationLevel>
			implements EducationLevelTOFactory{

	@Override
	public EducationLevelTO toTO(EducationLevel from) {
		
		EducationLevelTO to = new EducationLevelTO();

		to.fromModel(from);
		
		return to;
	}

	@Override
	public EducationLevel toModel(EducationLevelTO from) {
		EducationLevel model = new EducationLevel();

		from.addToModel(model);
		
		return model;
	}

}
