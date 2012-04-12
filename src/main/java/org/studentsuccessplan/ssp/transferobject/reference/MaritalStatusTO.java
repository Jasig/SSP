package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.MaritalStatus;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class MaritalStatusTO extends AbstractReferenceTO<MaritalStatus>
		implements TransferObject<MaritalStatus> {

	public MaritalStatusTO() {
		super();
	}

	public MaritalStatusTO(UUID id) {
		super(id);
	}

	public MaritalStatusTO(UUID id, String name) {
		super(id, name);
	}

	public MaritalStatusTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public MaritalStatus addToModel(MaritalStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public MaritalStatus asModel() {
		return addToModel(new MaritalStatus());
	}

	public static List<MaritalStatusTO> listToTOList(List<MaritalStatus> models) {
		List<MaritalStatusTO> tos = Lists.newArrayList();
		for (MaritalStatus model : models) {
			MaritalStatusTO obj = new MaritalStatusTO();
			obj.fromModel(model);
			tos.add(obj);
		}
		return tos;
	}

}
