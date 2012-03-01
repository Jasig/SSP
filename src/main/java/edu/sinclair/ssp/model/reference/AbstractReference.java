package edu.sinclair.ssp.model.reference;

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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import edu.sinclair.ssp.model.ObjectStatus;
import edu.sinclair.ssp.model.Person;

@MappedSuperclass
public abstract class AbstractReference {

	// jpa
	@Id
	@Type(type="pg-uuid")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private UUID id;
	
	//jpa
	@Column(name = "name", nullable = false, length=100)
	//validator
	@NotNull
	@NotEmpty
	private String name;
	
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false, updatable = false)
	private Person createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by", nullable = false, updatable = false)
	private Person modifiedBy;
	
	@Enumerated
	@Column(name = "object_status")
	private ObjectStatus objectStatus;

	@JsonIgnore
	@Version
	private int optlock;
	
	public AbstractReference() {}
	
	public AbstractReference(UUID id){
		this.id = id;
	}
	
	public AbstractReference(UUID id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public AbstractReference(UUID id, String name, String description) {
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

	public int getOptlock() {
		return optlock;
	}

	public void setOptlock(int optlock) {
		this.optlock = optlock;
	}

	public String toString(){
		if(null!=id){
			return id.toString();
		}else{
			return super.toString();
		}
	}
	
}
