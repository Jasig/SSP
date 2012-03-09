package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.VeteranStatusTOFactory;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.transferobject.reference.VeteranStatusTO;

@Service
public class VeteranStatusTOFactoryImpl 
			extends AbstractTransferObjectFactory<VeteranStatusTO, VeteranStatus>
			implements VeteranStatusTOFactory{

	@Override
	public VeteranStatusTO toTO(VeteranStatus from) {
		
		VeteranStatusTO to = new VeteranStatusTO();

		to.fromModel(from);
		
		return to;
	}

	@Override
	public VeteranStatus toModel(VeteranStatusTO from) {
		VeteranStatus model = new VeteranStatus();

		from.addToModel(model);
		
		return model;
	}

}
