package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.Ethnicity;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class EthnicityTO extends AbstractReferenceTO<Ethnicity>
		implements TransferObject<Ethnicity> {

	public EthnicityTO() {
		super();
	}

	public EthnicityTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public EthnicityTO(final Ethnicity model) {
		super();
		from(model);
	}

	public static List<EthnicityTO> toTOList(
			final Collection<Ethnicity> models) {
		final List<EthnicityTO> tObjects = Lists.newArrayList();
		for (Ethnicity model : models) {
			tObjects.add(new EthnicityTO(model));
		}
		return tObjects;
	}
}
