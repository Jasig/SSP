package edu.sinclair.ssp.factory.reference;

import java.util.List;

import edu.sinclair.ssp.factory.TransferObjectFactory;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.transferobject.reference.EducationLevelTO;

public interface EducationLevelTOFactory extends TransferObjectFactory<EducationLevelTO, EducationLevel> {

	@Override
	public EducationLevelTO toTO(EducationLevel from);

	@Override
	public EducationLevel toModel(EducationLevelTO from);

	@Override
	public List<EducationLevelTO> toTOList(List<EducationLevel> from);

	@Override
	public List<EducationLevel> toModelList(List<EducationLevelTO> from);

}
