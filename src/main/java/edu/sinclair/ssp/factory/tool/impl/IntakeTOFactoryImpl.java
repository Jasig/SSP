package edu.sinclair.ssp.factory.tool.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.tool.IntakeTOFactory;
import edu.sinclair.ssp.model.tool.IntakeForm;
import edu.sinclair.ssp.transferobject.tool.IntakeFormTO;

@Service
@Transactional(readOnly = true)
public class IntakeTOFactoryImpl 
			extends AbstractTransferObjectFactory<IntakeFormTO, IntakeForm>
			implements IntakeTOFactory {

	@Override
	public IntakeFormTO toTO(IntakeForm from) {
		IntakeFormTO to = new IntakeFormTO();
		to.fromModel(from);
		return to;
	}

	@Override
	public IntakeForm toModel(IntakeFormTO to) {
		IntakeForm model =  new IntakeForm();
		to.addToModel(model);
		return model;
	}

}
