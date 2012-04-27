package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
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

	public VeteranStatusTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public VeteranStatusTO(VeteranStatus model) {
		super();
		from(model);
	}

	public static List<VeteranStatusTO> toTOList(
			final Collection<VeteranStatus> models) {
		final List<VeteranStatusTO> tObjects = Lists.newArrayList();
		for (VeteranStatus model : models) {
			tObjects.add(new VeteranStatusTO(model));
		}
		return tObjects;
	}
}
