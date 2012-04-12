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

	public VeteranStatusTO(UUID id) {
		super(id);
	}

	public VeteranStatusTO(UUID id, String name) {
		super(id, name);
	}

	public VeteranStatusTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public VeteranStatusTO(VeteranStatus model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(VeteranStatus model) {
		super.fromModel(model);
	}

	@Override
	public VeteranStatus addToModel(VeteranStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public VeteranStatus asModel() {
		return addToModel(new VeteranStatus());
	}

	public static List<VeteranStatusTO> listToTOList(
			List<VeteranStatus> models) {
		List<VeteranStatusTO> tos = Lists.newArrayList();
		for (VeteranStatus model : models) {
			tos.add(new VeteranStatusTO(model));
		}
		return tos;
	}

}
