package org.jasig.ssp.service.reference;

import java.util.UUID;

import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeCategory;
import org.jasig.ssp.service.AuditableCrudService;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;

/**
 * Category service
 */
public interface CategoryService extends AuditableCrudService<Category> {

	@Override
	PagingWrapper<Category> getAll(SortingAndPaging sAndP);

	@Override
	Category get(UUID id) throws ObjectNotFoundException;

	@Override
	Category create(Category obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	Category save(Category obj) throws ObjectNotFoundException,
			ValidationException;

	@Override
	void delete(UUID id) throws ObjectNotFoundException;

	ChallengeCategory addChallengeToCategory(Challenge challenge,
			Category category);

	ChallengeCategory removeChallengeFromCategory(Challenge challenge,
			Category category);
}
