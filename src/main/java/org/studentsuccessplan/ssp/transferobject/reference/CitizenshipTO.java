package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class CitizenshipTO extends AbstractReferenceTO<Citizenship>
		implements TransferObject<Citizenship> {

	public CitizenshipTO() {
		super();
	}

	public CitizenshipTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public CitizenshipTO(final Citizenship model) {
		super();
		from(model);
	}

	public static List<CitizenshipTO> toTOList(
			final Collection<Citizenship> models) {
		final List<CitizenshipTO> tObjects = Lists.newArrayList();
		for (Citizenship model : models) {
			tObjects.add(new CitizenshipTO(model));
		}
		return tObjects;
	}
}
