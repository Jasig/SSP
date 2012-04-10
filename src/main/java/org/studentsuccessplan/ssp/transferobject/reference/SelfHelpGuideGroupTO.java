package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

public class SelfHelpGuideGroupTO extends AbstractReferenceTO<SelfHelpGuideGroup>
		implements TransferObject<SelfHelpGuideGroup> {

	public SelfHelpGuideGroupTO() {
		super();
	}

	public SelfHelpGuideGroupTO(UUID id) {
		super(id);
	}

	public SelfHelpGuideGroupTO(UUID id, String name) {
		super(id, name);
	}

	public SelfHelpGuideGroupTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public SelfHelpGuideGroupTO(SelfHelpGuideGroup model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(SelfHelpGuideGroup model) {
		super.fromModel(model);
	}

	@Override
	public SelfHelpGuideGroup pushAttributesToModel(SelfHelpGuideGroup model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public SelfHelpGuideGroup asModel() {
		return pushAttributesToModel(new SelfHelpGuideGroup());
	}

}
