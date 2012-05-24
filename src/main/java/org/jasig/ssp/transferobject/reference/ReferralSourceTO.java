package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.ReferralSource;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ReferralSourceTO
		extends AbstractReferenceTO<ReferralSource>
		implements TransferObject<ReferralSource> { // NOPMD

	public ReferralSourceTO() {
		super();
	}

	public ReferralSourceTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ReferralSourceTO(final ReferralSource model) {
		super();
		from(model);
	}

	public static List<ReferralSourceTO> toTOList(
			final Collection<ReferralSource> models) {
		final List<ReferralSourceTO> tObjects = Lists.newArrayList();
		for (final ReferralSource model : models) {
			tObjects.add(new ReferralSourceTO(model)); // NOPMD
		}

		return tObjects;
	}
}