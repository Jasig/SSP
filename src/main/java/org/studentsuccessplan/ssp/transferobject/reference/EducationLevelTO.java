package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.EducationLevel;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class EducationLevelTO extends AbstractReferenceTO<EducationLevel>
		implements TransferObject<EducationLevel> {

	public EducationLevelTO() {
		super();
	}

	public EducationLevelTO(UUID id) {
		super(id);
	}

	public EducationLevelTO(UUID id, String name) {
		super(id, name);
	}

	public EducationLevelTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public void fromModel(EducationLevel model) {
		super.fromModel(model);
	}

	@Override
	public EducationLevel addToModel(EducationLevel model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public EducationLevel asModel() {
		return addToModel(new EducationLevel());
	}

	public static List<EducationLevelTO> listToTOList(
			List<EducationLevel> models) {
		List<EducationLevelTO> tos = Lists.newArrayList();
		for (EducationLevel model : models) {
			EducationLevelTO obj = new EducationLevelTO();
			obj.fromModel(model);
			tos.add(obj);
		}
		return tos;
	}

}
