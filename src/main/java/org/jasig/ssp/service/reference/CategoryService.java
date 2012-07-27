package org.jasig.ssp.service.reference;

import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeCategory;
import org.jasig.ssp.service.ReferenceService;

/**
 * Category service
 */
public interface CategoryService extends ReferenceService<Category> {

	ChallengeCategory addChallengeToCategory(Challenge challenge,
			Category category);

	ChallengeCategory removeChallengeFromCategory(Challenge challenge,
			Category category);
}
