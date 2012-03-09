package edu.sinclair.ssp.transferobject;

import java.util.Date;
import java.util.UUID;

import edu.sinclair.ssp.model.Auditable;
import edu.sinclair.ssp.model.ObjectStatus;

public abstract class AuditableTO {
	
	private UUID id;

	private Date createdDate;

	private UUID createdById;

	private Date modifiedDate;
	
	private UUID modifiedById;
	
	private ObjectStatus objectStatus;


	public AuditableTO(){}
	
	public AuditableTO(UUID id){
		this.id = id;
	}

	public void fromModel(Auditable model){
		if(model.getId()!=null){
			setId(model.getId());
		}
		if(model.getCreatedBy()!=null){
			setCreatedById(model.getCreatedBy().getId());
		}
		if(model.getModifiedBy()!=null){
			setModifiedById(model.getModifiedBy().getId());
		}
		if(model.getCreatedDate()!=null){
			setCreatedDate(model.getCreatedDate());
		}
		if(model.getModifiedDate()!=null){
			setModifiedDate(model.getModifiedDate());
		}
		if(model.getObjectStatus()!=null){
			setObjectStatus(model.getObjectStatus());
		}
	}
	
	public void addToModel(Auditable model){
		if(getId()!=null){
			model.setId(getId());
		}
		if(getObjectStatus()!=null){
			model.setObjectStatus(getObjectStatus());
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public UUID getCreatedById() {
		return createdById;
	}

	public void setCreatedById(UUID createdById) {
		this.createdById = createdById;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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
