package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.DisabilityStatus;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class DisabilityStatusTO extends AbstractReferenceTO<DisabilityStatus>
		implements TransferObject<DisabilityStatus> {

	public DisabilityStatusTO() {
		super();
	}

	public DisabilityStatusTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public DisabilityStatusTO(final DisabilityStatus model) {
		super();
		from(model);
	}

	public static List<DisabilityStatusTO> toTOList(
			final Collection<DisabilityStatus> models) {
		final List<DisabilityStatusTO> tObjects = Lists.newArrayList();
		for (DisabilityStatus model : models) {
			tObjects.add(new DisabilityStatusTO(model));
		}
		return tObjects;
	}
}
