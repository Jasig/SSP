package edu.sinclair.ssp.model.transferobject;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class EthnicityTO {

	@NotNull
	private UUID id;
	
	@NotNull
	@NotEmpty
	private String name;
	
	public EthnicityTO() {}
	
	public EthnicityTO(UUID id, String name) {
		this.id = id;
		this.name = name;
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
}
