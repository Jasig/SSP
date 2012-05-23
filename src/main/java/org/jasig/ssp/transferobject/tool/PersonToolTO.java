package org.jasig.ssp.transferobject.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.transferobject.AbstractAuditableTO;
import org.jasig.ssp.transferobject.TransferObject;

public class PersonToolTO
		extends AbstractAuditableTO<PersonTool>
		implements TransferObject<PersonTool>, Serializable {

	private static final long serialVersionUID = 1L;

	private UUID personId;

	private String toolCode, toolTitle;

	public PersonToolTO() {
		super();
	}

	public PersonToolTO(final PersonTool model) {
		super();
		from(model);
	}

	@Override
	public final void from(final PersonTool model) { // NOPMD
		super.from(model);

		toolCode = model.getTool().getCode();
		toolTitle = model.getTool().getTitle();
		personId = model.getPerson() == null ? null
				: model.getPerson().getId();
	}

	public static List<PersonToolTO> toTOList(
			final Collection<PersonTool> models) {
		final List<PersonToolTO> tObjects = new ArrayList<PersonToolTO>();
		if ((models != null) && !models.isEmpty()) {
			for (PersonTool model : models) {
				tObjects.add(new PersonToolTO(model)); // NOPMD
			}
		}
		return tObjects;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(final UUID personId) {
		this.personId = personId;
	}

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(final String toolCode) {
		this.toolCode = toolCode;
	}

	public String getToolTitle() {
		return toolTitle;
	}

	public void setToolTitle(final String toolTitle) {
		this.toolTitle = toolTitle;
	}

}
