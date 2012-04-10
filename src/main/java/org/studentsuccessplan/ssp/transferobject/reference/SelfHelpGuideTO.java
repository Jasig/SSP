package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.SelfHelpGuide;
import edu.sinclair.ssp.transferobject.TransferObject;

public class SelfHelpGuideTO extends AbstractReferenceTO<SelfHelpGuide>
		implements TransferObject<SelfHelpGuide> {

	public SelfHelpGuideTO() {
		super();
	}

	public SelfHelpGuideTO(UUID id) {
		super(id);
	}

	public SelfHelpGuideTO(UUID id, String name) {
		super(id, name);
	}

	public SelfHelpGuideTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public SelfHelpGuideTO(SelfHelpGuide model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(SelfHelpGuide model) {
		super.fromModel(model);
	}

	@Override
	public SelfHelpGuide pushAttributesToModel(SelfHelpGuide model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public SelfHelpGuide asModel() {
		return pushAttributesToModel(new SelfHelpGuide());
	}

}
