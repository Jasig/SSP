package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.transferobject.reference.ChildCareArrangementTO;

public interface ChildCareArrangementTOFactory extends TransferObjectFactory<ChildCareArrangementTO, ChildCareArrangement> {

	@Override
	public ChildCareArrangementTO toTO(ChildCareArrangement from);

	@Override
	public ChildCareArrangement toModel(ChildCareArrangementTO from);

	@Override
	public List<ChildCareArrangementTO> toTOList(List<ChildCareArrangement> from);

	@Override
	public List<ChildCareArrangement> toModelList(List<ChildCareArrangementTO> from);

}
