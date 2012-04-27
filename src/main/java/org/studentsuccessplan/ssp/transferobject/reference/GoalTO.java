package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Goal;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class GoalTO extends AbstractReferenceTO<Goal>
		implements TransferObject<Goal> {

	public UUID confidentialityLevelId;

	public GoalTO() {
		super();
	}

	public GoalTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public GoalTO(final Goal model) {
		super();
		from(model);
	}

	public static List<GoalTO> toTOList(
			final Collection<Goal> models) {
		final List<GoalTO> tObjects = Lists.newArrayList();
		for (Goal model : models) {
			tObjects.add(new GoalTO(model));
		}
		return tObjects;
	}

	public UUID getConfidentialityLevelId() {
		return confidentialityLevelId;
	}

	public void setConfidentialityLevelId(final UUID confidentialityLevelId) {
		this.confidentialityLevelId = confidentialityLevelId;
	}

	@Override
	public final void from(final Goal model) {
		super.from(model);

		if (model.getConfidentialityLevel() != null) {
			confidentialityLevelId = model.getConfidentialityLevel().getId();
		}
	}
}
