package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class CitizenshipTO extends AbstractReferenceTO<Citizenship>
		implements TransferObject<Citizenship> {

	public CitizenshipTO() {
		super();
	}

	public CitizenshipTO(final UUID id) {
		super(id);
	}

	public CitizenshipTO(final UUID id, final String name) {
		super(id, name);
	}

	public CitizenshipTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public CitizenshipTO(final Citizenship model) {
		super();
		fromModel(model);
	}

	@Override
	public Citizenship addToModel(final Citizenship model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Citizenship asModel() {
		return addToModel(new Citizenship());
	}

	public static List<CitizenshipTO> listToTOList(
			final List<Citizenship> models) {
		final List<CitizenshipTO> tos = Lists.newArrayList();
		for (Citizenship model : models) {
			tos.add(new CitizenshipTO(model));
		}
		return tos;
	}

}
