package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuide;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SelfHelpGuideTO extends AbstractReferenceTO<SelfHelpGuide>
		implements TransferObject<SelfHelpGuide> {

	public SelfHelpGuideTO() {
		super();
	}

	public SelfHelpGuideTO(UUID id) {
		super(id);
	}

	public SelfHelpGuideTO(UUID id, String name) {
		super(id, name);
	}

	public SelfHelpGuideTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public void fromModel(SelfHelpGuide model) {
		super.fromModel(model);
	}

	@Override
	public SelfHelpGuide addToModel(SelfHelpGuide model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public SelfHelpGuide asModel() {
		return addToModel(new SelfHelpGuide());
	}

	public static List<SelfHelpGuideTO> listToTOList(List<SelfHelpGuide> guides) {
		List<SelfHelpGuideTO> tos = Lists.newArrayList();
		for (SelfHelpGuide model : guides) {
			SelfHelpGuideTO obj = new SelfHelpGuideTO();
			obj.fromModel(model);
			tos.add(obj);
		}
		return tos;
	}
}
