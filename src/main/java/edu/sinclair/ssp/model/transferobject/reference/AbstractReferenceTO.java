package edu.sinclair.ssp.model.transferobject.reference;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import edu.sinclair.ssp.model.ObjectStatus;

public abstract class AbstractReferenceTO {

	private UUID id;
	
	//validator
	@NotNull
	@NotEmpty
	private String name;
	
	private String description;
	
	private Date createdDate;

	private UUID createdById;

	private Date modifiedDate;
	
	private UUID modifiedById;
	
	private ObjectStatus objectStatus;
	
	public AbstractReferenceTO() {}
	
	public AbstractReferenceTO(UUID id){
		this.id = id;
	}
	
	public AbstractReferenceTO(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public AbstractReferenceTO(UUID id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
