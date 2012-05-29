package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.EarlyAlertRouting;

import com.google.common.collect.Lists;

/**
 * EarlyAlertRouting transfer object
 * 
 * @author jon.adams
 */
public class EarlyAlertRoutingTO
		extends AbstractAuditableTO<EarlyAlertRouting>
		implements TransferObject<EarlyAlertRouting>, Serializable {

	private static final long serialVersionUID = 5436157638643860410L;

	private String groupName;

	private String groupEmail;

	private UUID campusId;

	private UUID earlyAlertReasonId;

	private PersonLiteTO person;

	/**
	 * Empty constructor
	 */
	public EarlyAlertRoutingTO() {
		super();
	}

	/**
	 * Create a transfer object equivalent to the specified model
	 * 
	 * @param model
	 *            Model to copy
	 */
	public EarlyAlertRoutingTO(final EarlyAlertRouting model) {
		super();
		from(model);
	}

	@Override
	public final void from(final EarlyAlertRouting model) {
		super.from(model);

		groupName = model.getGroupName();
		groupEmail = model.getGroupEmail();
		campusId = model.getCampus() == null ? null : model.getCampus().getId();
		earlyAlertReasonId = model.getEarlyAlertReason() == null ? null : model
				.getEarlyAlertReason().getId();
		person = model.getPerson() == null ? null : new PersonLiteTO(
				model.getPerson());
	}

	/**
	 * Convert a collection of models to a list of equivalent transfer objects.
	 * 
	 * @param models
	 *            Collection of models to copy
	 * @return List of equivalent transfer objects
	 */
	public static List<EarlyAlertRoutingTO> toTOList(
			final Collection<EarlyAlertRouting> models) {
		final List<EarlyAlertRoutingTO> tObjects = Lists.newArrayList();
		for (final EarlyAlertRouting model : models) {
			tObjects.add(new EarlyAlertRoutingTO(model)); // NOPMD
		}

		return tObjects;
	}

	/**
	 * Gets the group name
	 * 
	 * @return the group name
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Sets the group name.
	 * 
	 * @param groupName
	 *            the group name; optional; max 255 characters
	 */
	public void setGroupName(final String groupName) {
		this.groupName = groupName;
	}

	/**
	 * Gets the group email
	 * 
	 * @return the group email
	 */
	public String getGroupEmail() {
		return groupEmail;
	}

	/**
	 * Sets the group email
	 * 
	 * @param groupEmail
	 *            the group email; max 255 characters
	 */
	public void setGroupEmail(final String groupEmail) {
		this.groupEmail = groupEmail;
	}

	/**
	 * Gets the associated Campus identifier
	 * 
	 * @return the CampusId
	 */
	public UUID getCampusId() {
		return campusId;
	}

	/**
	 * Sets the associated Campus by identifier
	 * 
	 * @param campusId
	 *            campus id
	 */
	public void setCampusId(@NotNull final UUID campusId) {
		this.campusId = campusId;
	}

	/**
	 * Gets the associated EarlyAlertReason identifier
	 * 
	 * @return the EarlyAlertReasonId
	 */
	public UUID getEarlyAlertReasonId() {
		return earlyAlertReasonId;
	}

	/**
	 * Sets the associated EarlyAlertReason by identifier
	 * 
	 * @param earlyAlertReasonId
	 *            earlyAlertReason id
	 */
	public void setEarlyAlertReasonId(@NotNull final UUID earlyAlertReasonId) {
		this.earlyAlertReasonId = earlyAlertReasonId;
	}

	/**
	 * Gets the associated Person
	 * 
	 * @return the person
	 */
	public PersonLiteTO getPerson() {
		return person;
	}

	/**
	 * Sets the associated Person
	 * 
	 * @param person
	 *            the person
	 */
	public void setPerson(final PersonLiteTO person) {
		this.person = person;
	}
}