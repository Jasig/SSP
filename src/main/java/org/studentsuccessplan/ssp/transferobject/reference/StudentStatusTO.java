package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.StudentStatus;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class StudentStatusTO
		extends AbstractReferenceTO<StudentStatus>
		implements TransferObject<StudentStatus> {

	public StudentStatusTO() {
		super();
	}

	public StudentStatusTO(UUID id) {
		super(id);
	}

	public StudentStatusTO(UUID id, String name) {
		super(id, name);
	}

	public StudentStatusTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public StudentStatusTO(StudentStatus model) {
		super();
		fromModel(model);
	}

	@Override
	public void fromModel(StudentStatus model) {
		super.fromModel(model);
	}

	@Override
	public StudentStatus addToModel(StudentStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public StudentStatus asModel() {
		return addToModel(new StudentStatus());
	}

	public static List<StudentStatusTO> listToTOList(
			List<StudentStatus> models) {
		List<StudentStatusTO> tos = Lists.newArrayList();
		for (StudentStatus model : models) {
			tos.add(new StudentStatusTO(model));
		}
		return tos;
	}

}
