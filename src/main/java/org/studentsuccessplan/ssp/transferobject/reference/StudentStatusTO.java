package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.StudentStatus;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class StudentStatusTO extends AbstractReferenceTO<StudentStatus>
		implements TransferObject<StudentStatus> {

	public StudentStatusTO() {
		super();
	}

	public StudentStatusTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public StudentStatusTO(final StudentStatus model) {
		super();
		from(model);
	}

	public static List<StudentStatusTO> toTOList(
			final Collection<StudentStatus> models) {
		final List<StudentStatusTO> tObjects = Lists.newArrayList();
		for (StudentStatus model : models) {
			tObjects.add(new StudentStatusTO(model));
		}
		return tObjects;
	}
}
