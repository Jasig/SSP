package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * Campus reference transfer objects
 * 
 * @author jon.adams
 */
public class CampusTO extends AbstractReferenceTO<Campus>
		implements TransferObject<Campus> {

	private UUID earlyAlertCoordinatorId;

	/**
	 * Empty constructor
	 */
	public CampusTO() {
		super();
	}

	/**
	 * Constructor to initialize the required properties of the class.
	 * 
	 * @param id
	 *            identifier
	 * @param name
	 *            name shown to the user
	 * @param earlyAlertCoordinatorId
	 *            Early Alert coordinator
	 */
	public CampusTO(@NotNull final UUID id, @NotNull final String name,
			@NotNull final UUID earlyAlertCoordinatorId) {
		super();
		super.setId(id);
		super.setName(name);
		this.earlyAlertCoordinatorId = earlyAlertCoordinatorId;
	}

	/**
	 * Constructor to initialize all properties of the class.
	 * 
	 * @param id
	 *            identifier
	 * @param name
	 *            name shown to the user
	 * @param description
	 *            description shown to the user in detail views
	 * @param earlyAlertCoordinatorId
	 *            Early Alert coordinator
	 */
	public CampusTO(@NotNull final UUID id, @NotNull final String name,
			final String description,
			@NotNull final UUID earlyAlertCoordinatorId) {
		super(id, name, description);
		this.earlyAlertCoordinatorId = earlyAlertCoordinatorId;
	}

	/**
	 * Construct a transfer object based on the data in the supplied model.
	 * 
	 * @param model
	 *            Model data to copy
	 */
	public CampusTO(@NotNull final Campus model) {
		super();

		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		from(model);
	}

	@Override
	public final void from(final Campus model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);
		earlyAlertCoordinatorId = model.getEarlyAlertCoordinatorId();
	}

	/**
	 * @return earlyAlertCoordinatorId the EarlyAlertCoordinatorId
	 */
	public UUID getEarlyAlertCoordinatorId() {
		return earlyAlertCoordinatorId;
	}

	/**
	 * @param earlyAlertCoordinatorId
	 *            the EarlyAlertCoordinatorId to set
	 */
	public void setEarlyAlertCoordinatorId(
			@NotNull final UUID earlyAlertCoordinatorId) {
		if (earlyAlertCoordinatorId == null) {
			throw new IllegalArgumentException(
					"EarlyAlertCoordinatorId can not be null.");
		}

		this.earlyAlertCoordinatorId = earlyAlertCoordinatorId;
	}

	/**
	 * Convert a collection of models to a collection of equivalent transfer
	 * objects.
	 * 
	 * @param models
	 *            Collection of models to copy
	 * @return A collection of equivalent transfer objects.
	 */
	public static List<CampusTO> toTOList(
			@NotNull final Collection<Campus> models) {
		final List<CampusTO> tObjects = Lists.newArrayList();
		for (Campus model : models) {
			tObjects.add(new CampusTO(model)); // NOPMD by jon.adams on 5/8/12
		}

		return tObjects;
	}
}
