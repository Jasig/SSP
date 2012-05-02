package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
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

	public EducationGoalTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public EducationGoalTO(final EducationGoal model) {
		super();
		from(model);
	}

	public static List<EducationGoalTO> toTOList(
			final Collection<EducationGoal> models) {
		final List<EducationGoalTO> tObjects = Lists.newArrayList();
		for (EducationGoal model : models) {
			tObjects.add(new EducationGoalTO(model));
		}
		return tObjects;
	}
}
