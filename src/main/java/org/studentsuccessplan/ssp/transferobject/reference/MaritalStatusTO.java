package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class MaritalStatusTO extends AbstractReferenceTO<MaritalStatus>
		implements TransferObject<MaritalStatus> {

	public MaritalStatusTO() {
		super();
	}

	public MaritalStatusTO(final UUID id) {
		super(id);
	}

	public MaritalStatusTO(final UUID id, final String name) {
		super(id, name);
	}

	public MaritalStatusTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public MaritalStatusTO(final MaritalStatus model) {
		super();
		fromModel(model);
	}

	@Override
	public MaritalStatus addToModel(final MaritalStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public MaritalStatus asModel() {
		return addToModel(new MaritalStatus());
	}

	public static List<MaritalStatusTO> listToTOList(
			final List<MaritalStatus> models) {
		final List<MaritalStatusTO> tos = Lists.newArrayList();
		for (MaritalStatus model : models) {
			tos.add(new MaritalStatusTO(model));
		}
		return tos;
	}

}
