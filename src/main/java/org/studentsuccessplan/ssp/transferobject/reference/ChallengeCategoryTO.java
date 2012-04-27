package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.ChallengeCategory;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class ChallengeCategoryTO extends AbstractReferenceTO<ChallengeCategory>
		implements TransferObject<ChallengeCategory> {

	public ChallengeCategoryTO() {
		super();
	}

	public ChallengeCategoryTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChallengeCategoryTO(final ChallengeCategory model) {
		super();
		from(model);
	}

	public static List<ChallengeCategoryTO> toTOList(
			final Collection<ChallengeCategory> models) {
		final List<ChallengeCategoryTO> tObjects = Lists.newArrayList();
		for (ChallengeCategory model : models) {
			tObjects.add(new ChallengeCategoryTO(model));
		}
		return tObjects;
	}
}
