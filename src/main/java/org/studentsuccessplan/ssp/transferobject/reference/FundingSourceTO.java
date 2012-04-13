package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.FundingSource;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class FundingSourceTO extends AbstractReferenceTO<FundingSource>
		implements TransferObject<FundingSource> {

	public FundingSourceTO() {
		super();
	}

	public FundingSourceTO(final UUID id) {
		super(id);
	}

	public FundingSourceTO(final UUID id, final String name) {
		super(id, name);
	}

	public FundingSourceTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public FundingSourceTO(final FundingSource model) {
		super();
		fromModel(model);
	}

	@Override
	public FundingSource addToModel(final FundingSource model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public FundingSource asModel() {
		return addToModel(new FundingSource());
	}

	public static List<FundingSourceTO> listToTOList(
			final List<FundingSource> models) {
		final List<FundingSourceTO> tos = Lists.newArrayList();
		for (FundingSource model : models) {
			tos.add(new FundingSourceTO(model));
		}
		return tos;
	}

}
