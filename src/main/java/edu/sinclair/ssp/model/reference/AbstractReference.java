package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@MappedSuperclass
public abstract class AbstractReference {

	// jpa
	@Id
	@Type(type="pg-uuid")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	// validator
	@NotNull
	private UUID id;
	
	//jpa
	@Column(name = "name", nullable = false, length=100)
	//validator
	@NotNull
	@NotEmpty
	private String name;
	
	//validator
	private String description;
	
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
