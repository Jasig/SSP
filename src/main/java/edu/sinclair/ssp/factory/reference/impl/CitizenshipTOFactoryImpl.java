package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.CitizenshipTOFactory;
import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.transferobject.reference.CitizenshipTO;

@Service
public class CitizenshipTOFactoryImpl 
			extends AbstractTransferObjectFactory<CitizenshipTO, Citizenship>
			implements CitizenshipTOFactory{

	@Override
	public CitizenshipTO toTO(Citizenship from) {
		
		CitizenshipTO to = new CitizenshipTO();

		to.fromModel(from);
		
		return to;
	}

	@Override
	public Citizenship toModel(CitizenshipTO from) {
		Citizenship model = new Citizenship();

		from.addToModel(model);
		
		return model;
	}

}
