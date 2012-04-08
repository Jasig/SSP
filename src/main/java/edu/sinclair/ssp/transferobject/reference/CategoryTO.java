package edu.sinclair.ssp.transferobject.reference;

import java.util.UUID;

import edu.sinclair.ssp.model.reference.Category;
import edu.sinclair.ssp.transferobject.TransferObject;

public class CategoryTO extends AbstractReferenceTO<Category>
		implements TransferObject<Category> {

	public CategoryTO() {
		super();
	}

	public CategoryTO(UUID id) {
		super(id);
	}

	public CategoryTO(UUID id, String name) {
		super(id, name);
	}

	public CategoryTO(UUID id, String name, String description) {
		super(id, name, description);
	}

	public CategoryTO(Category model) {
		super();
		pullAttributesFromModel(model);
	}

	@Override
	public void pullAttributesFromModel(Category model) {
		super.fromModel(model);
	}

	@Override
	public Category pushAttributesToModel(Category model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public Category asModel() {
		return pushAttributesToModel(new Category());
	}

}
