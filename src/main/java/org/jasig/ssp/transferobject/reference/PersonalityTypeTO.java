package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.PersonalityType;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class PersonalityTypeTO extends AbstractReferenceTO<PersonalityType>
		implements TransferObject<PersonalityType> {

	public PersonalityTypeTO() {
		super();
	}

	public PersonalityTypeTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public PersonalityTypeTO(final PersonalityType model) {
		super();
		from(model);
	}

	public static List<PersonalityTypeTO> toTOList(
			final Collection<PersonalityType> models) {
		final List<PersonalityTypeTO> tObjects = Lists.newArrayList();
		for (PersonalityType model : models) {
			tObjects.add(new PersonalityTypeTO(model));
		}
		return tObjects;
	}
}
