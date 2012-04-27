package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
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

	public MaritalStatusTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public MaritalStatusTO(final MaritalStatus model) {
		super();
		from(model);
	}

	public static List<MaritalStatusTO> toTOList(
			final Collection<MaritalStatus> models) {
		final List<MaritalStatusTO> tObjects = Lists.newArrayList();
		for (MaritalStatus model : models) {
			tObjects.add(new MaritalStatusTO(model));
		}
		return tObjects;
	}
}
