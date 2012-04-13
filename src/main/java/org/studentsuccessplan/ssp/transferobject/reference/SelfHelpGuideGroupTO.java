package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SelfHelpGuideGroupTO extends AbstractReferenceTO<SelfHelpGuideGroup>
		implements TransferObject<SelfHelpGuideGroup> {

	public SelfHelpGuideGroupTO() {
		super();
	}

	public SelfHelpGuideGroupTO(final UUID id) {
		super(id);
	}

	public SelfHelpGuideGroupTO(final UUID id, final String name) {
		super(id, name);
	}

	public SelfHelpGuideGroupTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SelfHelpGuideGroupTO(final SelfHelpGuideGroup model) {
		super();
		fromModel(model);
	}

	@Override
	public SelfHelpGuideGroup addToModel(final SelfHelpGuideGroup model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public SelfHelpGuideGroup asModel() {
		return addToModel(new SelfHelpGuideGroup());
	}

	public static List<SelfHelpGuideGroupTO> listToTOList(
			final List<SelfHelpGuideGroup> models) {
		final List<SelfHelpGuideGroupTO> tos = Lists.newArrayList();
		for (SelfHelpGuideGroup model : models) {
			tos.add(new SelfHelpGuideGroupTO(model));
		}
		return tos;
	}

}
