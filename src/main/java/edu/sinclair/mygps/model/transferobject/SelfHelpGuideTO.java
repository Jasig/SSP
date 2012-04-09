package edu.sinclair.mygps.model.transferobject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.sinclair.ssp.model.reference.SelfHelpGuide;

public class SelfHelpGuideTO {

	private UUID id;
	private String name;
	private String description;

	public SelfHelpGuideTO() {
	}

	public SelfHelpGuideTO(SelfHelpGuide selfHelpGuide) {
		this.setDescription(selfHelpGuide.getDescription());
		this.setId(selfHelpGuide.getId());
		this.setName(selfHelpGuide.getName());
	}

	public static List<SelfHelpGuideTO> selfHelpGuidesToSelfHelpGuideTOs(
			List<SelfHelpGuide> selfHelpGuides) {

		List<SelfHelpGuideTO> selfHelpGuideTOs = new ArrayList<SelfHelpGuideTO>();

		for (SelfHelpGuide selfHelpGuide : selfHelpGuides) {
			selfHelpGuideTOs.add(new SelfHelpGuideTO(selfHelpGuide));
		}

		return selfHelpGuideTOs;
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
