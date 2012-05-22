package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.UUID;

import org.jasig.ssp.model.reference.ConfidentialityLevel;

public class ConfidentialityLevelLiteTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private String name;

	public ConfidentialityLevelLiteTO() {
		super();
	}

	public ConfidentialityLevelLiteTO(
			final UUID id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public static ConfidentialityLevelLiteTO fromModel(
			final ConfidentialityLevel level) {
		if (level == null) {
			return null;
		} else {
			return new ConfidentialityLevelLiteTO(level.getId(),
					level.getAcronym());
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
