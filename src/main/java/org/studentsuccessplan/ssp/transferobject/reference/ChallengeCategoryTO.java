package org.studentsuccessplan.ssp.transferobject.reference;

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

	public ChallengeCategoryTO(final UUID id) {
		super(id);
	}

	public ChallengeCategoryTO(final UUID id, final String name) {
		super(id, name);
	}

	public ChallengeCategoryTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ChallengeCategoryTO(final ChallengeCategory model) {
		super();
		fromModel(model);
	}

	@Override
	public ChallengeCategory addToModel(final ChallengeCategory model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public ChallengeCategory asModel() {
		return addToModel(new ChallengeCategory());
	}

	public static List<ChallengeCategoryTO> listToTOList(
			final List<ChallengeCategory> models) {
		final List<ChallengeCategoryTO> tos = Lists.newArrayList();
		for (ChallengeCategory model : models) {
			tos.add(new ChallengeCategoryTO(model));
		}
		return tos;
	}

}
