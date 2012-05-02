package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
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

	public SelfHelpGuideTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SelfHelpGuideTO(final SelfHelpGuide model) {
		super();
		from(model);
	}

	public static List<SelfHelpGuideTO> toTOList(
			final Collection<SelfHelpGuide> models) {
		final List<SelfHelpGuideTO> tObjects = Lists.newArrayList();
		for (SelfHelpGuide model : models) {
			tObjects.add(new SelfHelpGuideTO(model));
		}
		return tObjects;
	}
}
