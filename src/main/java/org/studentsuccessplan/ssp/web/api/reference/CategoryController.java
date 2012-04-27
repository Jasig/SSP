package org.studentsuccessplan.ssp.web.api.reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.studentsuccessplan.ssp.factory.TOFactory;
import org.studentsuccessplan.ssp.factory.reference.CategoryTOFactory;
import org.studentsuccessplan.ssp.model.reference.Category;
import org.studentsuccessplan.ssp.service.AuditableCrudService;
import org.studentsuccessplan.ssp.service.reference.CategoryService;
import org.studentsuccessplan.ssp.transferobject.reference.CategoryTO;

@PreAuthorize("hasRole('ROLE_USER')")
@Controller
@RequestMapping("/1/reference/category")
public class CategoryController
		extends
		AbstractAuditableReferenceController<Category, CategoryTO> {

	@Autowired
	protected transient CategoryService service;

	@Override
	protected AuditableCrudService<Category> getService() {
		return service;
	}

	@Autowired
	protected transient CategoryTOFactory factory;

	@Override
	protected TOFactory<CategoryTO, Category> getFactory() {
		return factory;
	}

	protected CategoryController() {
		super(Category.class, CategoryTO.class);
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CategoryController.class);

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
