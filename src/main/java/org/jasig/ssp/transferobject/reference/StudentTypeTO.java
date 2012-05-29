package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.StudentType;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * StudentType transfer object
 * 
 * @author jon.adams
 * 
 */
public class StudentTypeTO
		extends AbstractReferenceTO<StudentType>
		implements TransferObject<StudentType> { // NOPMD

	private boolean requireInitialAppointment = false;

	/**
	 * Empty constructor
	 */
	public StudentTypeTO() {
		super();
	}

	public StudentTypeTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public StudentTypeTO(final StudentType model) {
		super();
		from(model);
	}

	@Override
	public final void from(final StudentType model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);

		requireInitialAppointment = model.isRequireInitialAppointment();
	}

	/**
	 * @return the requireInitialAppointment
	 */
	public boolean isRequireInitialAppointment() {
		return requireInitialAppointment;
	}

	/**
	 * @param requireInitialAppointment
	 *            the requireInitialAppointment to set
	 */
	public void setRequireInitialAppointment(
			final boolean requireInitialAppointment) {
		this.requireInitialAppointment = requireInitialAppointment;
	}

	public static List<StudentTypeTO> toTOList(
			final Collection<StudentType> models) {
		final List<StudentTypeTO> tObjects = Lists.newArrayList();
		for (final StudentType model : models) {
			tObjects.add(new StudentTypeTO(model)); // NOPMD
		}

		return tObjects;
	}
}