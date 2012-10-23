package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.DisabilityType;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class DisabilityTypeTO extends AbstractReferenceTO<DisabilityType>
		implements TransferObject<DisabilityType> {

	public DisabilityTypeTO() {
		super();
	}

	public DisabilityTypeTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public DisabilityTypeTO(final DisabilityType model) {
		super();
		from(model);
	}

	public static List<DisabilityTypeTO> toTOList(
			final Collection<DisabilityType> models) {
		final List<DisabilityTypeTO> tObjects = Lists.newArrayList();
		for (DisabilityType model : models) {
			tObjects.add(new DisabilityTypeTO(model));
		}
		return tObjects;
	}
}
