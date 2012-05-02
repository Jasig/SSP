package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.EducationLevel;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class EducationLevelTO extends AbstractReferenceTO<EducationLevel>
		implements TransferObject<EducationLevel> {

	public EducationLevelTO() {
		super();
	}

	public EducationLevelTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public EducationLevelTO(final EducationLevel model) {
		super();
		from(model);
	}

	public static List<EducationLevelTO> toTOList(
			final Collection<EducationLevel> models) {
		final List<EducationLevelTO> tObjects = Lists.newArrayList();
		for (EducationLevel model : models) {
			tObjects.add(new EducationLevelTO(model));
		}
		return tObjects;
	}
}
