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

	public MessageTemplateTO(final UUID id) {
		super(id);
	}

	public MessageTemplateTO(final UUID id, final String name) {
		super(id, name);
	}

	public MessageTemplateTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public MessageTemplateTO(final MessageTemplate model) {
		super();
		fromModel(model);
	}

	@Override
	public MessageTemplate addToModel(final MessageTemplate model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public MessageTemplate asModel() {
		return addToModel(new MessageTemplate());
	}

	public static List<MessageTemplateTO> listToTOList(
			final List<MessageTemplate> models) {
		final List<MessageTemplateTO> tos = Lists.newArrayList();
		for (MessageTemplate model : models) {
			tos.add(new MessageTemplateTO(model));
		}
		return tos;
	}

}
