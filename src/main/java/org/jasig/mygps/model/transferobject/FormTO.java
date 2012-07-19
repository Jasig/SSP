package org.jasig.mygps.model.transferobject;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Student Intake Form transfer object
 */
public class FormTO implements Serializable {

	private static final long serialVersionUID = -1978459152865190470L;

	private UUID id;

	private String label;

	private List<FormSectionTO> sections;

	public FormSectionTO getFormSectionById(final UUID formSectionId) {
		for (final FormSectionTO formSectionTO : getSections()) {
			if (formSectionTO.getId().equals(formSectionId)) {
				return formSectionTO;
			}
		}
		return null;
	}

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public List<FormSectionTO> getSections() {
		return sections;
	}

	public void setSections(final List<FormSectionTO> sections) {
		this.sections = sections;
	}
}