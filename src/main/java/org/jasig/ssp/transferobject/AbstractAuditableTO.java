package org.jasig.ssp.transferobject;

import java.util.Date;
import java.util.UUID;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;

/**
 * Transfer object for copy to and from equivalent Auditable models.
 * 
 * @param <T>
 *            Any {@link Auditable} model type.
 */
public abstract class AbstractAuditableTO<T extends Auditable>
		implements TransferObject<T> {

	/**
	 * Empty constructor.
	 */
	public AbstractAuditableTO() {
		super();
	}

	/**
	 * Construct a simple Auditable with the specified identifier.
	 * 
	 * @param id
	 *            Identifier
	 */
	public AbstractAuditableTO(final UUID id) {
		super();
		this.id = id;
	}

	private UUID id;

	private Date createdDate;

	private PersonLiteTO createdBy;

	private Date modifiedDate;

	private PersonLiteTO modifiedBy;

	private ObjectStatus objectStatus;

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate == null ? null : new Date(
				createdDate.getTime());
	}

	public PersonLiteTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final PersonLiteTO createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate == null ? null : new Date(modifiedDate.getTime());
	}

	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate == null ? null : new Date(
				modifiedDate.getTime());
	}

	public PersonLiteTO getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(
			final PersonLiteTO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(final ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

	@Override
	public void from(final T model) {
		id = model.getId();
		if (model.getCreatedBy() != null) {
			createdBy = new PersonLiteTO(model.getCreatedBy());
		}

		if (model.getModifiedBy() != null) {
			modifiedBy = new PersonLiteTO(model.getModifiedBy());
		}

		createdDate = model.getCreatedDate();
		modifiedDate = model.getModifiedDate();
		objectStatus = model.getObjectStatus();
	}

}
