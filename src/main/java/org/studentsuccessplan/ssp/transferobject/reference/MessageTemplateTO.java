package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.MessageTemplate;
import edu.sinclair.ssp.transferobject.TransferObject;

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

	public MessageTemplateTO(MessageTemplate model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(MessageTemplate model) {
		super.fromModel(model);
	}

	@Override
	public MessageTemplate pushAttributesToModel(MessageTemplate model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public MessageTemplate asModel() {
		return pushAttributesToModel(new MessageTemplate());
	}

}
