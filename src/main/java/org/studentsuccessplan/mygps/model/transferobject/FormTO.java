package org.studentsuccessplan.mygps.model.transferobject;

import java.util.List;
import java.util.UUID;

public class FormTO {

	private UUID id;
	private String label;
	private List<FormSectionTO> sections;

	public FormSectionTO getFormSectionById(UUID formSectionId) {
		for (FormSectionTO formSectionTO : getSections()) {
			if (formSectionTO.getId().equals(formSectionId)) {
				return formSectionTO;
			}
		}
		return null;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<FormSectionTO> getSections() {
		return sections;
	}

	public void setSections(List<FormSectionTO> sections) {
		this.sections = sections;
	}

}
