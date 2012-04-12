package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Citizenship;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class CitizenshipTO extends AbstractReferenceTO<Citizenship> implements
		TransferObject<Citizenship> {

	public CitizenshipTO() {
		super();
	}

	public CitizenshipTO(UUID id) {
		super(id);
	}

	public CitizenshipTO(UUID id, String name) {
		super(id, name);
	}

	public CitizenshipTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public void fromModel(Citizenship model) {
		super.fromModel(model);
	}

	@Override
	public Citizenship addToModel(Citizenship model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Citizenship asModel() {
		return addToModel(new Citizenship());
	}

	public static List<CitizenshipTO> listToTOList(List<Citizenship> models) {
		List<CitizenshipTO> tos = Lists.newArrayList();
		for (Citizenship model : models) {
			CitizenshipTO obj = new CitizenshipTO();
			obj.fromModel(model);
			tos.add(obj);
		}
		return tos;
	}

}
