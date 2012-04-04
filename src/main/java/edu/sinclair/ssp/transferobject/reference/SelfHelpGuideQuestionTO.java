package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.SelfHelpGuideQuestion;
import edu.sinclair.ssp.transferobject.TransferObject;

public class SelfHelpGuideQuestionTO extends AbstractReferenceTO<SelfHelpGuideQuestion>
		implements TransferObject<SelfHelpGuideQuestion> {

	public SelfHelpGuideQuestionTO() {
		super();
	}

	public SelfHelpGuideQuestionTO(UUID id) {
		super(id);
	}

	public SelfHelpGuideQuestionTO(UUID id, String name) {
		super(id, name);
	}

	public SelfHelpGuideQuestionTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public SelfHelpGuideQuestionTO(SelfHelpGuideQuestion model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(SelfHelpGuideQuestion model) {
		super.fromModel(model);
	}

	@Override
	public SelfHelpGuideQuestion pushAttributesToModel(SelfHelpGuideQuestion model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public SelfHelpGuideQuestion asModel() {
		return pushAttributesToModel(new SelfHelpGuideQuestion());
	}

}
