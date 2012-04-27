package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SelfHelpGuideGroupTO extends
		AbstractReferenceTO<SelfHelpGuideGroup>
		implements TransferObject<SelfHelpGuideGroup> {

	public SelfHelpGuideGroupTO() {
		super();
	}

	public SelfHelpGuideGroupTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SelfHelpGuideGroupTO(final SelfHelpGuideGroup model) {
		super();
		from(model);
	}

	public static List<SelfHelpGuideGroupTO> toTOList(
			final Collection<SelfHelpGuideGroup> models) {
		final List<SelfHelpGuideGroupTO> tObjects = Lists.newArrayList();
		for (SelfHelpGuideGroup model : models) {
			tObjects.add(new SelfHelpGuideGroupTO(model));
		}
		return tObjects;
	}
}
