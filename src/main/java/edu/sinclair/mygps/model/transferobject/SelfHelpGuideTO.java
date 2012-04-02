package edu.sinclair.mygps.model.transferobject;

import java.util.UUID;

public class SelfHelpGuideTO {

	private UUID id;
	private String name;
	private String description;

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
