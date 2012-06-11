package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.MessageTemplate;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class MessageTemplateTO extends AbstractReferenceTO<MessageTemplate>
		implements TransferObject<MessageTemplate> {

	private String subject;

	private String body;

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

	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 * 
	 * @param subject
	 *            Maximum length of 250 characters.
	 */
	public void setSubject(@NotNull final String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(@NotNull final String body) {
		this.body = body;
	}

	@Override
	public final void from(final MessageTemplate model) {
		super.from(model);

		subject = model.getSubject();
		body = model.getBody();
	}

	public static List<MessageTemplateTO> toTOList(
			final Collection<MessageTemplate> models) {
		final List<MessageTemplateTO> tObjects = Lists.newArrayList();
		for (final MessageTemplate model : models) {
			tObjects.add(new MessageTemplateTO(model)); // NOPMD
		}

		return tObjects;
	}
}