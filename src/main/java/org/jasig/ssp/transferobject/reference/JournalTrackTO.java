package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.JournalTrack;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class JournalTrackTO extends AbstractReferenceTO<JournalTrack>
		implements TransferObject<JournalTrack> {

	private int sortOrder;

	public JournalTrackTO() {
		super();
	}

	public JournalTrackTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public JournalTrackTO(final JournalTrack model) {
		super();
		from(model);
	}

	public static List<JournalTrackTO> toTOList(
			final Collection<JournalTrack> models) {
		final List<JournalTrackTO> tObjects = Lists.newArrayList();
		for (JournalTrack model : models) {
			tObjects.add(new JournalTrackTO(model));
		}
		return tObjects;
	}

	@Override
	public final void from(final JournalTrack model) {
		super.from(model);
		sortOrder = model.getSortOrder();
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
}
