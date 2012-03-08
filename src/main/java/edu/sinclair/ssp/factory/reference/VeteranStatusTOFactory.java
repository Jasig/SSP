package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.transferobject.reference.VeteranStatusTO;

public interface VeteranStatusTOFactory extends TransferObjectFactory<VeteranStatusTO, VeteranStatus> {

	@Override
	public VeteranStatusTO toTO(VeteranStatus from);

	@Override
	public VeteranStatus toModel(VeteranStatusTO from);

	@Override
	public List<VeteranStatusTO> toTOList(List<VeteranStatus> from);

	@Override
	public List<VeteranStatus> toModelList(List<VeteranStatusTO> from);

}
