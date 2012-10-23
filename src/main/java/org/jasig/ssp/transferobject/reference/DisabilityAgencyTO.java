package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.DisabilityAgency;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class DisabilityAgencyTO extends AbstractReferenceTO<DisabilityAgency>
		implements TransferObject<DisabilityAgency> {

	public DisabilityAgencyTO() {
		super();
	}

	public DisabilityAgencyTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public DisabilityAgencyTO(final DisabilityAgency model) {
		super();
		from(model);
	}

	public static List<DisabilityAgencyTO> toTOList(
			final Collection<DisabilityAgency> models) {
		final List<DisabilityAgencyTO> tObjects = Lists.newArrayList();
		for (DisabilityAgency model : models) {
			tObjects.add(new DisabilityAgencyTO(model));
		}
		return tObjects;
	}
}
