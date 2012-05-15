package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.JournalStepDetail;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class JournalStepDetailTO extends AbstractReferenceTO<JournalStepDetail>
		implements TransferObject<JournalStepDetail>, Serializable {

	private static final long serialVersionUID = 7539604345170318617L;

	private int sortOrder;

	public JournalStepDetailTO() {
		super();
	}

	public JournalStepDetailTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public JournalStepDetailTO(final JournalStepDetail model) {
		super();
		from(model);
	}

	public static List<JournalStepDetailTO> toTOList(
			final Collection<JournalStepDetail> models) {
		final List<JournalStepDetailTO> tObjects = Lists.newArrayList();
		for (JournalStepDetail model : models) {
			tObjects.add(new JournalStepDetailTO(model)); // NOPMD by jon.adams
		}

		return tObjects;
	}

	@Override
	public final void from(final JournalStepDetail model) {
		super.from(model);
		sortOrder = model.getSortOrder();
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(final int sortOrder) {
		this.sortOrder = sortOrder;
	}
}
