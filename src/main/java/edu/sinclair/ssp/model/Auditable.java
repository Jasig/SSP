package edu.sinclair.ssp.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class Auditable {
	// jpa
	@Id
	@Type(type="pg-uuid")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	private Person createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date modifiedDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Person modifiedBy;
	
	@Enumerated
	@Column(nullable = false)
	private ObjectStatus objectStatus;

	/**
	@Version
	private int optlock;*/


	public Auditable(){}

	public Auditable(UUID id){
		this.id = id;
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

	public Person getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Person createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Person getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Person modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

	public String toString(){
		if(null!=id){
			return id.toString();
		}else{
			return super.toString();
		}
	}
}
