package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.transferobject.reference.EthnicityTO;

public interface EthnicityTOFactory extends TransferObjectFactory<EthnicityTO, Ethnicity> {

	@Override
	public EthnicityTO toTO(Ethnicity from);

	@Override
	public Ethnicity toModel(EthnicityTO from);

	@Override
	public List<EthnicityTO> toTOList(List<Ethnicity> from);

	@Override
	public List<Ethnicity> toModelList(List<EthnicityTO> from);

}
