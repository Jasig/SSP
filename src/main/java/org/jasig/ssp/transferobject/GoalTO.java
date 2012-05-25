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

/**
 * Goal transfer object
 */
public class GoalTO
		extends AbstractAuditableTO<Goal>
		implements TransferObject<Goal>, Serializable {

	private static final long serialVersionUID = 5011875522731047877L;

	@NotNull
	@NotEmpty
	private String name;

	private String description;

	private UUID personId;

	public ConfidentialityLevelLiteTO confidentialityLevel;

	/**
	 * Empty constructor
	 */
	public GoalTO() {
		super();
	}

	/**
	 * Create a transfer object equivalent to the specified model
	 * 
	 * @param model
	 *            Model to copy
	 */
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

	/**
	 * Convert a collection of models to a list of equivalent transfer objects.
	 * 
	 * @param models
	 *            Collection of models to copy
	 * @return List of equivalent transfer objects
	 */
	public static List<GoalTO> toTOList(
			final Collection<Goal> models) {
		final List<GoalTO> tObjects = Lists.newArrayList();
		for (final Goal model : models) {
			tObjects.add(new GoalTO(model)); // NOPMD
		}

		return tObjects;
	}

	/**
	 * Gets the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 *            the name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the description
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description
	 * 
	 * @param description
	 *            the description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * Gets the associated Person identifier
	 * 
	 * @return the PersonId
	 */
	public UUID getPersonId() {
		return personId;
	}

	/**
	 * Sets the associated Person by identifier
	 * 
	 * @param personId
	 *            person id
	 */
	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	/**
	 * Gets the confidentiality level
	 * 
	 * @return the confidentiality level
	 */
	public ConfidentialityLevelLiteTO getConfidentialityLevel() {
		return confidentialityLevel;
	}

	/**
	 * Sets the confidentiality level
	 * 
	 * @param confidentialityLevel
	 *            the confidentiality level
	 */
	public void setConfidentialityLevel(
			final ConfidentialityLevelLiteTO confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}
}