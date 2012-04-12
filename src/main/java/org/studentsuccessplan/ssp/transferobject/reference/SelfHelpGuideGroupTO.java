package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.SelfHelpGuideGroup;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class SelfHelpGuideGroupTO extends
		AbstractReferenceTO<SelfHelpGuideGroup> implements
		TransferObject<SelfHelpGuideGroup> {

	public SelfHelpGuideGroupTO() {
		super();
	}

	public SelfHelpGuideGroupTO(UUID id) {
		super(id);
	}

	public SelfHelpGuideGroupTO(UUID id, String name) {
		super(id, name);
	}

	public SelfHelpGuideGroupTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public void fromModel(SelfHelpGuideGroup model) {
		super.fromModel(model);
	}

	@Override
	public SelfHelpGuideGroup addToModel(SelfHelpGuideGroup model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public SelfHelpGuideGroup asModel() {
		return addToModel(new SelfHelpGuideGroup());
	}

	public static List<SelfHelpGuideGroupTO> listToTOList(
			List<SelfHelpGuideGroup> models) {
		List<SelfHelpGuideGroupTO> tos = Lists.newArrayList();
		for (SelfHelpGuideGroup model : models) {
			SelfHelpGuideGroupTO obj = new SelfHelpGuideGroupTO();
			obj.fromModel(model);
			tos.add(obj);
		}
		return tos;
	}

}
