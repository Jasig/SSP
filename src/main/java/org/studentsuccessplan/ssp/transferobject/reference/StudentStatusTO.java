package org.studentsuccessplan.ssp.transferobject.reference;

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

	public StudentStatusTO(final UUID id) {
		super(id);
	}

	public StudentStatusTO(final UUID id, final String name) {
		super(id, name);
	}

	public StudentStatusTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public StudentStatusTO(final StudentStatus model) {
		super();
		fromModel(model);
	}

	@Override
	public StudentStatus addToModel(final StudentStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public StudentStatus asModel() {
		return addToModel(new StudentStatus());
	}

	public static List<StudentStatusTO> listToTOList(
			final List<StudentStatus> models) {
		final List<StudentStatusTO> tos = Lists.newArrayList();
		for (StudentStatus model : models) {
			tos.add(new StudentStatusTO(model));
		}
		return tos;
	}

}
