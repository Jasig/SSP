package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.JournalStep;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class JournalStepTO extends AbstractReferenceTO<JournalStep>
		implements TransferObject<JournalStep> {

	private int sortOrder;

	public JournalStepTO() {
		super();
	}

	public JournalStepTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public JournalStepTO(final JournalStep model) {
		super();
		from(model);
	}

	public static List<JournalStepTO> toTOList(
			final Collection<JournalStep> models) {
		final List<JournalStepTO> tObjects = Lists.newArrayList();
		for (JournalStep model : models) {
			tObjects.add(new JournalStepTO(model));
		}
		return tObjects;
	}

	@Override
	public final void from(final JournalStep model) {
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
