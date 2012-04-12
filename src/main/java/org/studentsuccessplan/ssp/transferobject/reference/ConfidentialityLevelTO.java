package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ConfidentialityLevelTO extends
		AbstractReferenceTO<ConfidentialityLevel> implements
		TransferObject<ConfidentialityLevel> {

	public ConfidentialityLevelTO() {
		super();
	}

	public ConfidentialityLevelTO(UUID id) {
		super(id);
	}

	public ConfidentialityLevelTO(UUID id, String name) {
		super(id, name);
	}

	public ConfidentialityLevelTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ConfidentialityLevelTO(ConfidentialityLevel model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(ConfidentialityLevel model) {
		super.fromModel(model);
	}

	@Override
	public ConfidentialityLevel addToModel(ConfidentialityLevel model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ConfidentialityLevel asModel() {
		return addToModel(new ConfidentialityLevel());
	}

	public static List<ConfidentialityLevelTO> listToTOList(
			List<ConfidentialityLevel> models) {
		List<ConfidentialityLevelTO> tos = Lists.newArrayList();
		for (ConfidentialityLevel model : models) {
			tos.add(new ConfidentialityLevelTO(model));
		}
		return tos;
	}

}
