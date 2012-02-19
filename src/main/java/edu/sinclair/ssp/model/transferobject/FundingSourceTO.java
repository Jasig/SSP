package edu.sinclair.ssp.model.transferobject;

import java.util.UUID;

import javax.validation.constraints.NotNull;

public class FundingSourceTO {

	@NotNull
	private UUID id;
	
	@NotNull
	private String name;
	
	private String description;
	
	public FundingSourceTO() {}
	
	public FundingSourceTO(UUID id, String name, String description) {
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
