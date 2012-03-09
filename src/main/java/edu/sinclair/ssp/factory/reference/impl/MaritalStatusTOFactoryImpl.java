package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.MaritalStatusTOFactory;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.transferobject.reference.MaritalStatusTO;

@Service
public class MaritalStatusTOFactoryImpl 
			extends AbstractTransferObjectFactory<MaritalStatusTO, MaritalStatus>
			implements MaritalStatusTOFactory{

	@Override
	public MaritalStatusTO toTO(MaritalStatus from) {
		
		MaritalStatusTO to = new MaritalStatusTO();

		to.fromModel(from);
		
		return to;
	}

	@Override
	public MaritalStatus toModel(MaritalStatusTO from) {
		MaritalStatus model = new MaritalStatus();

		from.addToModel(model);
		
		return model;
	}

}
