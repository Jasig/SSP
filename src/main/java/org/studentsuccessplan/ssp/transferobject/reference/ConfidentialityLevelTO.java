package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.studentsuccessplan.ssp.model.reference.ConfidentialityLevel;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ConfidentialityLevelTO extends
		AbstractReferenceTO<ConfidentialityLevel>
		implements TransferObject<ConfidentialityLevel> {

	@NotNull
	@NotEmpty
	private String acronym;

	public ConfidentialityLevelTO() {
		super();
	}

	public ConfidentialityLevelTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ConfidentialityLevelTO(final ConfidentialityLevel model) {
		super();
		from(model);
	}

	public static List<ConfidentialityLevelTO> toTOList(
			final Collection<ConfidentialityLevel> models) {
		final List<ConfidentialityLevelTO> tObjects = Lists.newArrayList();
		for (ConfidentialityLevel model : models) {
			tObjects.add(new ConfidentialityLevelTO(model));
		}
		return tObjects;
	}

	@Override
	public final void from(final ConfidentialityLevel model) {
		super.from(model);
		acronym = model.getAcronym();
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}

}
