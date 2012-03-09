package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import edu.sinclair.ssp.model.Auditable;

@MappedSuperclass
public abstract class AbstractReference extends Auditable {

	//jpa
	@Column(name = "name", nullable = false, length=100)
	//validator
	@NotNull
	@NotEmpty
	private String name;
	
	private String description;
	
	public AbstractReference() {
		super();
	}
	
	public AbstractReference(UUID id){
		super(id);
	}
	
	public AbstractReference(UUID id, String name) {
		super(id);
		this.name = name;
	}
	
	public AbstractReference(UUID id, String name, String description) {
		super(id);
		this.name = name;
		this.description = description;
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

}
