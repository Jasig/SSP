package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChildCareArrangementTO extends
		AbstractReferenceTO<ChildCareArrangement> implements
		TransferObject<ChildCareArrangement> {

	public ChildCareArrangementTO() {
		super();
	}

	public ChildCareArrangementTO(UUID id) {
		super(id);
	}

	public ChildCareArrangementTO(UUID id, String name) {
		super(id, name);
	}

	public ChildCareArrangementTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public ChildCareArrangementTO(ChildCareArrangement model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(ChildCareArrangement model) {
		super.fromModel(model);
	}

	@Override
	public ChildCareArrangement addToModel(ChildCareArrangement model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ChildCareArrangement asModel() {
		return addToModel(new ChildCareArrangement());
	}

	public static List<ChildCareArrangementTO> listToTOList(
			List<ChildCareArrangement> models) {
		List<ChildCareArrangementTO> tos = Lists.newArrayList();
		for (ChildCareArrangement model : models) {
			tos.add(new ChildCareArrangementTO(model));
		}
		return tos;
	}

}
