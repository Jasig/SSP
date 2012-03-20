package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.transferobject.TransferObject;

public class StudentStatusTO extends AbstractReferenceTO implements
		TransferObject<StudentStatus> {

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
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(StudentStatus model) {
		super.fromModel(model);
	}

	@Override
	public StudentStatus pushAttributesToModel(StudentStatus model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public StudentStatus asModel() {
		return pushAttributesToModel(new StudentStatus());
	}

}
