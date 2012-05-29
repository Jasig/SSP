package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.ProgramStatusChangeReason;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * ProgramStatusChangeReason transfer object
 * 
 * @author jon.adams
 * 
 */
public class ProgramStatusChangeReasonTO
		extends AbstractReferenceTO<ProgramStatusChangeReason>
		implements TransferObject<ProgramStatusChangeReason> { // NOPMD
	/**
	 * Empty constructor
	 */
	public ProgramStatusChangeReasonTO() {
		super();
	}

	public ProgramStatusChangeReasonTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public ProgramStatusChangeReasonTO(final ProgramStatusChangeReason model) {
		super();
		from(model);
	}

	public static List<ProgramStatusChangeReasonTO> toTOList(
			final Collection<ProgramStatusChangeReason> models) {
		final List<ProgramStatusChangeReasonTO> tObjects = Lists.newArrayList();
		for (final ProgramStatusChangeReason model : models) {
			tObjects.add(new ProgramStatusChangeReasonTO(model)); // NOPMD
		}

		return tObjects;
	}
}