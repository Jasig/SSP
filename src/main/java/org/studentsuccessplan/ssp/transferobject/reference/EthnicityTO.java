package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Ethnicity;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class EthnicityTO extends AbstractReferenceTO<Ethnicity>
		implements TransferObject<Ethnicity> {

	public EthnicityTO() {
		super();
	}

	public EthnicityTO(final UUID id) {
		super(id);
	}

	public EthnicityTO(final UUID id, final String name) {
		super(id, name);
	}

	public EthnicityTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public EthnicityTO(final Ethnicity model) {
		super();
		fromModel(model);
	}

	@Override
	public Ethnicity addToModel(final Ethnicity model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Ethnicity asModel() {
		return addToModel(new Ethnicity());
	}

	public static List<EthnicityTO> listToTOList(
			final List<Ethnicity> models) {
		final List<EthnicityTO> tos = Lists.newArrayList();
		for (Ethnicity model : models) {
			tos.add(new EthnicityTO(model));
		}
		return tos;
	}

}
