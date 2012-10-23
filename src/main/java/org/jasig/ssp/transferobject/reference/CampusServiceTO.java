package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.CampusService;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class CampusServiceTO extends AbstractReferenceTO<CampusService>
		implements TransferObject<CampusService> {

	public CampusServiceTO() {
		super();
	}

	public CampusServiceTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public CampusServiceTO(final CampusService model) {
		super();
		from(model);
	}

	public static List<CampusServiceTO> toTOList(
			final Collection<CampusService> models) {
		final List<CampusServiceTO> tObjects = Lists.newArrayList();
		for (CampusService model : models) {
			tObjects.add(new CampusServiceTO(model));
		}
		return tObjects;
	}
}
