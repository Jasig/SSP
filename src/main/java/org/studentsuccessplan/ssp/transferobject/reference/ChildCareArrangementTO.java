package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
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

	public ChildCareArrangementTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChildCareArrangementTO(ChildCareArrangement model) {
		super();
		from(model);
	}

	public static List<ChildCareArrangementTO> toTOList(
			final Collection<ChildCareArrangement> models) {
		final List<ChildCareArrangementTO> tObjects = Lists.newArrayList();
		for (ChildCareArrangement model : models) {
			tObjects.add(new ChildCareArrangementTO(model));
		}
		return tObjects;
	}
}
