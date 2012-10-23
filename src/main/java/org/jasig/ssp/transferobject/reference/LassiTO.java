package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.Lassi;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class LassiTO extends AbstractReferenceTO<Lassi>
		implements TransferObject<Lassi> {

	public LassiTO() {
		super();
	}

	public LassiTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public LassiTO(final Lassi model) {
		super();
		from(model);
	}

	public static List<LassiTO> toTOList(
			final Collection<Lassi> models) {
		final List<LassiTO> tObjects = Lists.newArrayList();
		for (Lassi model : models) {
			tObjects.add(new LassiTO(model));
		}
		return tObjects;
	}
}
