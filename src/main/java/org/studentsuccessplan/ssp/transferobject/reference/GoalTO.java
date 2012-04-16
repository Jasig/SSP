package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.model.reference.Goal;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class GoalTO extends AbstractReferenceTO<Goal>
		implements TransferObject<Goal> {

	public UUID confidentialityLevelId;

	public GoalTO() {
		super();
	}

	public GoalTO(final UUID id) {
		super(id);
	}

	public GoalTO(final UUID id, final String name) {
		super(id, name);
	}

	public GoalTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public GoalTO(final Goal model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(final Goal model) {
		super.fromModel(model);

		confidentialityLevelId = model.getConfidentialityLevel().getId();
	}

	@Override
	public Goal addToModel(final Goal model) {
		super.addToModel(model);

		model.setConfidentialityLevel(new ConfidentialityLevel(
				confidentialityLevelId));
		return model;
	}

	@Override
	public Goal asModel() {
		return addToModel(new Goal());
	}

	public static List<GoalTO> listToTOList(
			final List<Goal> models) {
		final List<GoalTO> tos = Lists.newArrayList();
		for (Goal model : models) {
			tos.add(new GoalTO(model));
		}
		return tos;
	}

}
