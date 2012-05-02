package org.studentsuccessplan.ssp.transferobject;

import java.util.Date;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;

public abstract class AuditableTO<T extends Auditable>
		implements TransferObject<T> {

	public AuditableTO() {
		super();
	}

	public AuditableTO(final UUID id) {
		super();
		this.id = id;
	}

	private UUID id;

	private Date createdDate;

	private UUID createdById;

	private Date modifiedDate;

	private UUID modifiedById;

	private ObjectStatus objectStatus;

	@Override
	public void from(final T model) {
		id = model.getId();
		if (model.getCreatedBy() != null) {
			createdById = model.getCreatedBy().getId();
		}
		if (model.getModifiedBy() != null) {
			modifiedById = model.getModifiedBy().getId();
		}
		createdDate = model.getCreatedDate();
		modifiedDate = model.getModifiedDate();
		objectStatus = model.getObjectStatus();
	}

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

	public UUID getCreatedById() {
		return createdById;
	}

	public void setCreatedById(final UUID createdById) {
		this.createdById = createdById;
	}

	public Date getModifiedDate() {
		return modifiedDate == null ? null : new Date(modifiedDate.getTime());
	}

	public void setModifiedDate(final Date modifiedDate) {
		this.modifiedDate = modifiedDate == null ? null : new Date(
				modifiedDate.getTime());
	}

	public UUID getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(final UUID modifiedById) {
		this.modifiedById = modifiedById;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(final ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}
}
