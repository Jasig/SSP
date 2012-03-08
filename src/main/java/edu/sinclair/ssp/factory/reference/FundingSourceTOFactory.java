package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.transferobject.reference.FundingSourceTO;

public interface FundingSourceTOFactory extends TransferObjectFactory<FundingSourceTO, FundingSource> {

	@Override
	public FundingSourceTO toTO(FundingSource from);

	@Override
	public FundingSource toModel(FundingSourceTO from);

	@Override
	public List<FundingSourceTO> toTOList(List<FundingSource> from);

	@Override
	public List<FundingSource> toModelList(List<FundingSourceTO> from);

}
