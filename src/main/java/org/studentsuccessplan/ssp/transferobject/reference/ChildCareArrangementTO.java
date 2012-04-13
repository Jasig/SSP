package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChildCareArrangement;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChildCareArrangementTO extends AbstractReferenceTO<ChildCareArrangement>
		implements TransferObject<ChildCareArrangement> {

	public ChildCareArrangementTO() {
		super();
	}

	public ChildCareArrangementTO(final UUID id) {
		super(id);
	}

	public ChildCareArrangementTO(final UUID id, final String name) {
		super(id, name);
	}

	public ChildCareArrangementTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChildCareArrangementTO(final ChildCareArrangement model) {
		super();
		fromModel(model);
	}

	@Override
	public ChildCareArrangement addToModel(final ChildCareArrangement model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ChildCareArrangement asModel() {
		return addToModel(new ChildCareArrangement());
	}

	public static List<ChildCareArrangementTO> listToTOList(
			final List<ChildCareArrangement> models) {
		final List<ChildCareArrangementTO> tos = Lists.newArrayList();
		for (ChildCareArrangement model : models) {
			tos.add(new ChildCareArrangementTO(model));
		}
		return tos;
	}

}
