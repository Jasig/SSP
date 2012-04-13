package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ConfidentialityLevelTO extends AbstractReferenceTO<ConfidentialityLevel>
		implements TransferObject<ConfidentialityLevel> {

	public ConfidentialityLevelTO() {
		super();
	}

	public ConfidentialityLevelTO(final UUID id) {
		super(id);
	}

	public ConfidentialityLevelTO(final UUID id, final String name) {
		super(id, name);
	}

	public ConfidentialityLevelTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ConfidentialityLevelTO(final ConfidentialityLevel model) {
		super();
		fromModel(model);
	}

	@Override
	public ConfidentialityLevel addToModel(final ConfidentialityLevel model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ConfidentialityLevel asModel() {
		return addToModel(new ConfidentialityLevel());
	}

	public static List<ConfidentialityLevelTO> listToTOList(
			final List<ConfidentialityLevel> models) {
		final List<ConfidentialityLevelTO> tos = Lists.newArrayList();
		for (ConfidentialityLevel model : models) {
			tos.add(new ConfidentialityLevelTO(model));
		}
		return tos;
	}

}
