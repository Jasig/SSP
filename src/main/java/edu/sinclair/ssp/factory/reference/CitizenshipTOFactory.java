package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.transferobject.reference.CitizenshipTO;

public interface CitizenshipTOFactory extends TransferObjectFactory<CitizenshipTO, Citizenship> {

	@Override
	public CitizenshipTO toTO(Citizenship from);

	@Override
	public Citizenship toModel(CitizenshipTO from);

	@Override
	public List<CitizenshipTO> toTOList(List<Citizenship> from);

	@Override
	public List<Citizenship> toModelList(List<CitizenshipTO> from);

}
