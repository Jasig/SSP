package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SelfHelpGuideTO extends AbstractReferenceTO<SelfHelpGuide>
		implements TransferObject<SelfHelpGuide> {

	public SelfHelpGuideTO() {
		super();
	}

	public SelfHelpGuideTO(final UUID id) {
		super(id);
	}

	public SelfHelpGuideTO(final UUID id, final String name) {
		super(id, name);
	}

	public SelfHelpGuideTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SelfHelpGuideTO(final SelfHelpGuide model) {
		super();
		fromModel(model);
	}

	@Override
	public SelfHelpGuide addToModel(final SelfHelpGuide model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public SelfHelpGuide asModel() {
		return addToModel(new SelfHelpGuide());
	}

	public static List<SelfHelpGuideTO> listToTOList(
			final List<SelfHelpGuide> models) {
		final List<SelfHelpGuideTO> tos = Lists.newArrayList();
		for (SelfHelpGuide model : models) {
			tos.add(new SelfHelpGuideTO(model));
		}
		return tos;
	}

}
