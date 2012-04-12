package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.MessageTemplate;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class MessageTemplateTO extends AbstractReferenceTO<MessageTemplate>
		implements TransferObject<MessageTemplate> {

	public MessageTemplateTO() {
		super();
	}

	public MessageTemplateTO(UUID id) {
		super(id);
	}

	public MessageTemplateTO(UUID id, String name) {
		super(id, name);
	}

	public MessageTemplateTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	@Override
	public void fromModel(MessageTemplate model) {
		super.fromModel(model);
	}

	@Override
	public MessageTemplate addToModel(MessageTemplate model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public MessageTemplate asModel() {
		return addToModel(new MessageTemplate());
	}

	public static List<MessageTemplateTO> listToTOList(
			List<MessageTemplate> models) {
		List<MessageTemplateTO> tos = Lists.newArrayList();
		for (MessageTemplate model : models) {
			MessageTemplateTO obj = new MessageTemplateTO();
			obj.fromModel(model);
			tos.add(obj);
		}
		return tos;
	}

}
