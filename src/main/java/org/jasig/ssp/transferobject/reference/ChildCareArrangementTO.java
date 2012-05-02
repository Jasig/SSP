package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.ChildCareArrangement;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChildCareArrangementTO extends
		AbstractReferenceTO<ChildCareArrangement>
		implements TransferObject<ChildCareArrangement> {

	public ChildCareArrangementTO() {
		super();
	}

	public ChildCareArrangementTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChildCareArrangementTO(final ChildCareArrangement model) {
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
