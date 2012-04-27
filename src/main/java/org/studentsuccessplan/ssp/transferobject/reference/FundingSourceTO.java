package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
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

	public FundingSourceTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public FundingSourceTO(FundingSource model) {
		super();
		from(model);
	}

	public static List<FundingSourceTO> toTOList(
			final Collection<FundingSource> models) {
		final List<FundingSourceTO> tObjects = Lists.newArrayList();
		for (FundingSource model : models) {
			tObjects.add(new FundingSourceTO(model));
		}
		return tObjects;
	}
}
