package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.DisabilityAccommodation;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class DisabilityAccommodationTO extends AbstractReferenceTO<DisabilityAccommodation>
		implements TransferObject<DisabilityAccommodation> {

	public DisabilityAccommodationTO() {
		super();
	}

	public DisabilityAccommodationTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public DisabilityAccommodationTO(final DisabilityAccommodation model) {
		super();
		from(model);
	}

	public static List<DisabilityAccommodationTO> toTOList(
			final Collection<DisabilityAccommodation> models) {
		final List<DisabilityAccommodationTO> tObjects = Lists.newArrayList();
		for (DisabilityAccommodation model : models) {
			tObjects.add(new DisabilityAccommodationTO(model));
		}
		return tObjects;
	}
}
