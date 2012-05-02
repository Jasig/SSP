package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.transferobject.TransferObject;

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

	public MessageTemplateTO(final MessageTemplate model) {
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
