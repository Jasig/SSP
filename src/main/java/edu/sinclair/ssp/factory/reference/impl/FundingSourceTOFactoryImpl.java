package edu.sinclair.ssp.factory.reference.impl;

import org.springframework.stereotype.Service;

import edu.sinclair.ssp.factory.AbstractTransferObjectFactory;
import edu.sinclair.ssp.factory.reference.FundingSourceTOFactory;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.transferobject.reference.FundingSourceTO;

@Service
public class FundingSourceTOFactoryImpl 
			extends AbstractTransferObjectFactory<FundingSourceTO, FundingSource>
			implements FundingSourceTOFactory{

	@Override
	public FundingSourceTO toTO(FundingSource from) {
		
		FundingSourceTO to = new FundingSourceTO();

		to.fromModel(from);
		
		return to;
	}

	@Override
	public FundingSource toModel(FundingSourceTO from) {
		FundingSource model = new FundingSource();

		from.addToModel(model);
		
		return model;
	}

}
