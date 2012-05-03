package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.JournalSource;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class JournalSourceTO extends AbstractReferenceTO<JournalSource>
		implements TransferObject<JournalSource> {

	public JournalSourceTO() {
		super();
	}

	public JournalSourceTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public JournalSourceTO(final JournalSource model) {
		super();
		from(model);
	}

	public static List<JournalSourceTO> toTOList(
			final Collection<JournalSource> models) {
		final List<JournalSourceTO> tObjects = Lists.newArrayList();
		for (JournalSource model : models) {
			tObjects.add(new JournalSourceTO(model));
		}
		return tObjects;
	}
}
