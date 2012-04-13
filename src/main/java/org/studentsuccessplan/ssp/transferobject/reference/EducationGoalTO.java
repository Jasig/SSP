package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.EducationGoal;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class EducationGoalTO extends AbstractReferenceTO<EducationGoal>
		implements TransferObject<EducationGoal> {

	public EducationGoalTO() {
		super();
	}

	public EducationGoalTO(final UUID id) {
		super(id);
	}

	public EducationGoalTO(final UUID id, final String name) {
		super(id, name);
	}

	public EducationGoalTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public EducationGoalTO(final EducationGoal model) {
		super();
		fromModel(model);
	}

	@Override
	public EducationGoal addToModel(final EducationGoal model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public EducationGoal asModel() {
		return addToModel(new EducationGoal());
	}

	public static List<EducationGoalTO> listToTOList(
			final List<EducationGoal> models) {
		final List<EducationGoalTO> tos = Lists.newArrayList();
		for (EducationGoal model : models) {
			tos.add(new EducationGoalTO(model));
		}
		return tos;
	}

}
