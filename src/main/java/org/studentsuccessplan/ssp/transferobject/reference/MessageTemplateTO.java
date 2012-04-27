package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
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

	public MessageTemplateTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public MessageTemplateTO(MessageTemplate model) {
		super();
		from(model);
	}

	public static List<MessageTemplateTO> toTOList(
			final Collection<MessageTemplate> models) {
		final List<MessageTemplateTO> tObjects = Lists.newArrayList();
		for (MessageTemplate model : models) {
			tObjects.add(new MessageTemplateTO(model));
		}
		return tObjects;
	}
}
