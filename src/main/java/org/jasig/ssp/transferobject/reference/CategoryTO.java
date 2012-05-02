package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class CategoryTO extends AbstractReferenceTO<Category>
		implements TransferObject<Category> {

	public CategoryTO() {
		super();
	}

	public CategoryTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public CategoryTO(final Category model) {
		super();
		from(model);
	}

	public static List<CategoryTO> toTOList(
			final Collection<Category> models) {
		final List<CategoryTO> tObjects = Lists.newArrayList();
		for (Category model : models) {
			tObjects.add(new CategoryTO(model));
		}
		return tObjects;
	}
}
