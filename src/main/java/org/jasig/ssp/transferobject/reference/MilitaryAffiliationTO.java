package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.MilitaryAffiliation;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class MilitaryAffiliationTO extends AbstractReferenceTO<MilitaryAffiliation>
		implements TransferObject<MilitaryAffiliation> {

	public MilitaryAffiliationTO() {
		super();
	}

	public MilitaryAffiliationTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public MilitaryAffiliationTO(final MilitaryAffiliation model) {
		super();
		from(model);
	}

	public static List<MilitaryAffiliationTO> toTOList(
			final Collection<MilitaryAffiliation> models) {
		final List<MilitaryAffiliationTO> tObjects = Lists.newArrayList();
		for (MilitaryAffiliation model : models) {
			tObjects.add(new MilitaryAffiliationTO(model));
		}
		return tObjects;
	}
}
