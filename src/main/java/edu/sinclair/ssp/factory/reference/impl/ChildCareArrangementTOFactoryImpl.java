package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.ChildCareArrangementTOFactory;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.transferobject.reference.ChildCareArrangementTO;

@Service
public class ChildCareArrangementTOFactoryImpl 
			extends AbstractTransferObjectFactory<ChildCareArrangementTO, ChildCareArrangement>
			implements ChildCareArrangementTOFactory{

	@Override
	public ChildCareArrangementTO toTO(ChildCareArrangement from) {
		ChildCareArrangementTO to = new ChildCareArrangementTO();
		
		to.fromModel(from);
		
		return to;
	}

	@Override
	public ChildCareArrangement toModel(ChildCareArrangementTO from) {
		ChildCareArrangement model = new ChildCareArrangement();

		from.addToModel(model);
		
		return model;
	}

}
