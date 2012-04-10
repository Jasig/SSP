package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.Category;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

public class CategoryTO extends AbstractReferenceTO<Category> implements
		TransferObject<Category> {

	public CategoryTO() {
		super();
	}

	public CategoryTO(final UUID id) {
		super(id);
	}

	public CategoryTO(final UUID id, final String name) {
		super(id, name);
	}

	public CategoryTO(final UUID id, final String name, final String description) {
		super(id, name, description);
	}

	@Override
	public void pullAttributesFromModel(final Category model) {
		super.fromModel(model);
	}

	@Override
	public Category pushAttributesToModel(final Category model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Category asModel() {
		return pushAttributesToModel(new Category());
	}

}
