package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.VeteranStatus;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class VeteranStatusTO extends AbstractReferenceTO<VeteranStatus>
		implements TransferObject<VeteranStatus> {

	public VeteranStatusTO() {
		super();
	}

	public VeteranStatusTO(final UUID id) {
		super(id);
	}

	public VeteranStatusTO(final UUID id, final String name) {
		super(id, name);
	}

	public VeteranStatusTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public VeteranStatusTO(final VeteranStatus model) {
		super();
		fromModel(model);
	}

	@Override
	public VeteranStatus addToModel(final VeteranStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public VeteranStatus asModel() {
		return addToModel(new VeteranStatus());
	}

	public static List<VeteranStatusTO> listToTOList(
			final List<VeteranStatus> models) {
		final List<VeteranStatusTO> tos = Lists.newArrayList();
		for (VeteranStatus model : models) {
			tos.add(new VeteranStatusTO(model));
		}
		return tos;
	}

}
