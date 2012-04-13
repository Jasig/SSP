package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideQuestion;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SelfHelpGuideQuestionTO extends AbstractReferenceTO<SelfHelpGuideQuestion>
		implements TransferObject<SelfHelpGuideQuestion> {

	public SelfHelpGuideQuestionTO() {
		super();
	}

	public SelfHelpGuideQuestionTO(final UUID id) {
		super(id);
	}

	public SelfHelpGuideQuestionTO(final UUID id, final String name) {
		super(id, name);
	}

	public SelfHelpGuideQuestionTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SelfHelpGuideQuestionTO(final SelfHelpGuideQuestion model) {
		super();
		fromModel(model);
	}

	@Override
	public SelfHelpGuideQuestion addToModel(final SelfHelpGuideQuestion model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public SelfHelpGuideQuestion asModel() {
		return addToModel(new SelfHelpGuideQuestion());
	}

	public static List<SelfHelpGuideQuestionTO> listToTOList(
			final List<SelfHelpGuideQuestion> models) {
		final List<SelfHelpGuideQuestionTO> tos = Lists.newArrayList();
		for (SelfHelpGuideQuestion model : models) {
			tos.add(new SelfHelpGuideQuestionTO(model));
		}
		return tos;
	}

}
