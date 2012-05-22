package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.ssp.model.Goal;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelLiteTO;

import com.google.common.collect.Lists;

public class GoalTO
		extends AbstractAuditableTO<Goal>
		implements TransferObject<Goal>, Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@NotEmpty
	private String name;

	private String description;

	private UUID personId;

	public ConfidentialityLevelLiteTO confidentialityLevel;

	public GoalTO() {
		super();
	}

	public GoalTO(final Goal model) {
		super();
		from(model);
	}

	@Override
	public final void from(final Goal model) {
		super.from(model);

		name = model.getName();
		description = model.getDescription();
		personId = model.getPerson() == null ? null
				: model.getPerson().getId();
		confidentialityLevel = ConfidentialityLevelLiteTO.fromModel(
				model.getConfidentialityLevel());

	}

	public static List<GoalTO> toTOList(
			final Collection<Goal> models) {
		final List<GoalTO> tObjects = Lists.newArrayList();
		for (Goal model : models) {
			tObjects.add(new GoalTO(model));
		}
		return tObjects;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public ConfidentialityLevelLiteTO getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(
			final ConfidentialityLevelLiteTO confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}
}
