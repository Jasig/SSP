package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.transferobject.reference.MaritalStatusTO;

public interface MaritalStatusTOFactory extends TransferObjectFactory<MaritalStatusTO, MaritalStatus> {

	@Override
	public MaritalStatusTO toTO(MaritalStatus from);

	@Override
	public MaritalStatus toModel(MaritalStatusTO from);

	@Override
	public List<MaritalStatusTO> toTOList(List<MaritalStatus> from);

	@Override
	public List<MaritalStatus> toModelList(List<MaritalStatusTO> from);

}
