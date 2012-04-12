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

	public FundingSourceTO(UUID id) {
		super(id);
	}

	public FundingSourceTO(UUID id, String name) {
		super(id, name);
	}

	public FundingSourceTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public FundingSourceTO(FundingSource model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(FundingSource model) {
		super.fromModel(model);
	}

	@Override
	public FundingSource addToModel(FundingSource model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public FundingSource asModel() {
		return addToModel(new FundingSource());
	}

	public static List<FundingSourceTO> listToTOList(
			List<FundingSource> models) {
		List<FundingSourceTO> tos = Lists.newArrayList();
		for (FundingSource model : models) {
			tos.add(new FundingSourceTO(model));
		}
		return tos;
	}

}
