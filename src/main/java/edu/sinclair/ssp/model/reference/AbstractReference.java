package edu.sinclair.ssp.model.reference;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public abstract class AbstractReference {

	@NotNull
	private UUID id;
	
	@NotNull
	@NotEmpty
	private String name;
	
	private String description;
	
	public AbstractReference() {}
	
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

}
