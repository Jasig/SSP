package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.EthnicityTOFactory;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.transferobject.reference.EthnicityTO;

@Service
public class EthnicityTOFactoryImpl 
			extends AbstractTransferObjectFactory<EthnicityTO, Ethnicity>
			implements EthnicityTOFactory{

	@Override
	public EthnicityTO toTO(Ethnicity from) {
		
		EthnicityTO to = new EthnicityTO();

		to.fromModel(from);
		
		return to;
	}

	@Override
	public Ethnicity toModel(EthnicityTO from) {
		Ethnicity model = new Ethnicity();

		from.addToModel(model);
		
		return model;
	}

}
