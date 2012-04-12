package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Ethnicity;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class EthnicityTO extends AbstractReferenceTO<Ethnicity> implements
		TransferObject<Ethnicity> {

	public EthnicityTO() {
		super();
	}

	public EthnicityTO(UUID id) {
		super(id);
	}

	public EthnicityTO(UUID id, String name) {
		super(id, name);
	}

	public EthnicityTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public void fromModel(Ethnicity model) {
		super.fromModel(model);
	}

	@Override
	public Ethnicity addToModel(Ethnicity model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Ethnicity asModel() {
		return addToModel(new Ethnicity());
	}

	public static List<EthnicityTO> listToTOList(List<Ethnicity> models) {
		List<EthnicityTO> tos = Lists.newArrayList();
		for (Ethnicity model : models) {
			EthnicityTO obj = new EthnicityTO();
			obj.fromModel(model);
			tos.add(obj);
		}
		return tos;
	}

}
