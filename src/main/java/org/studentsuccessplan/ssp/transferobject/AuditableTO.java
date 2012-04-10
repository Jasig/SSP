package edu.sinclair.ssp.transferobject;

import java.util.Date;
import java.util.UUID;

import edu.sinclair.ssp.model.Auditable;
import edu.sinclair.ssp.model.ObjectStatus;

public abstract class AuditableTO<T> implements TransferObject<T> {

	private UUID id;

	private Date createdDate;

	private UUID createdById;

	private Date modifiedDate;

	private UUID modifiedById;

	private ObjectStatus objectStatus;

	public AuditableTO() {
	}

	public AuditableTO(UUID id) {
		this.id = id;
	}

	public void fromModel(Auditable model) {
		setId(model.getId());
		if (model.getCreatedBy() != null) {
			setCreatedById(model.getCreatedBy().getId());
		}
		if (model.getModifiedBy() != null) {
			setModifiedById(model.getModifiedBy().getId());
		}
		setCreatedDate(model.getCreatedDate());
		setModifiedDate(model.getModifiedDate());
		setObjectStatus(model.getObjectStatus());
	}

	public void addToModel(Auditable model) {
		model.setId(getId());
		model.setObjectStatus(getObjectStatus());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate == null ? null : new Date(
				createdDate.getTime());
	}

	public UUID getCreatedById() {
		return createdById;
	}

	public void setCreatedById(UUID createdById) {
		this.createdById = createdById;
	}

	public Date getModifiedDate() {
		return modifiedDate == null ? null : new Date(modifiedDate.getTime());
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate == null ? null : new Date(
				modifiedDate.getTime());
	}

	public UUID getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(UUID modifiedById) {
		this.modifiedById = modifiedById;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}
}
