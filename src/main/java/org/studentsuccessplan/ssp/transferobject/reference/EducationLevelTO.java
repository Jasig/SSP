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

	public EducationLevelTO(final UUID id) {
		super(id);
	}

	public EducationLevelTO(final UUID id, final String name) {
		super(id, name);
	}

	public EducationLevelTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public EducationLevelTO(final EducationLevel model) {
		super();
		fromModel(model);
	}

	@Override
	public EducationLevel addToModel(final EducationLevel model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public EducationLevel asModel() {
		return addToModel(new EducationLevel());
	}

	public static List<EducationLevelTO> listToTOList(
			final List<EducationLevel> models) {
		final List<EducationLevelTO> tos = Lists.newArrayList();
		for (EducationLevel model : models) {
			tos.add(new EducationLevelTO(model));
		}
		return tos;
	}

}
