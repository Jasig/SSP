package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SpecialServiceGroupTO
		extends AbstractReferenceTO<SpecialServiceGroup>
		implements TransferObject<SpecialServiceGroup> { // NOPMD

	public SpecialServiceGroupTO() {
		super();
	}

	public SpecialServiceGroupTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SpecialServiceGroupTO(final SpecialServiceGroup model) {
		super();
		from(model);
	}

	public static List<SpecialServiceGroupTO> toTOList(
			final Collection<SpecialServiceGroup> models) {
		final List<SpecialServiceGroupTO> tObjects = Lists.newArrayList();
		for (final SpecialServiceGroup model : models) {
			tObjects.add(new SpecialServiceGroupTO(model));// NOPMD
		}

		return tObjects;
	}
}