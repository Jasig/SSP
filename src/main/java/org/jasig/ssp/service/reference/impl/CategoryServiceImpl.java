package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.CategoryDao;
import org.jasig.ssp.dao.reference.ChallengeCategoryDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeCategory;
import org.jasig.ssp.service.reference.CategoryService;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Category service
 */
@Service
@Transactional
public class CategoryServiceImpl extends AbstractReferenceService<Category>
		implements CategoryService {

	@Autowired
	transient private CategoryDao dao;

	@Autowired
	private transient ChallengeCategoryDao challengeCategoryDao;

	@Override
	protected CategoryDao getDao() {
		return dao;
	}

	public CategoryServiceImpl() {
		super();
	}

	public CategoryServiceImpl(final CategoryDao dao,
			final ChallengeCategoryDao challengeCategoryDao) {
		super();
		this.dao = dao;
		this.challengeCategoryDao = challengeCategoryDao;
	}

	@Override
	public ChallengeCategory addChallengeToCategory(final Challenge challenge,
			final Category category) {
		final PagingWrapper<ChallengeCategory> challengeCategories = challengeCategoryDao
				.getAllForChallengeAndCategory(challenge.getId(),
						category.getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		ChallengeCategory challengeCategory = null;
		// if this challenge is already there and ACTIVE, ignore
		if (challengeCategories.getResults() < 1) {
			challengeCategory = new ChallengeCategory();
			challengeCategory.setCategory(category);
			challengeCategory.setChallenge(challenge);
			challengeCategory.setObjectStatus(ObjectStatus.ACTIVE);

			challengeCategory = challengeCategoryDao.save(challengeCategory);
		}

		return challengeCategory;
	}

	@Override
	public ChallengeCategory removeChallengeFromCategory(
			final Challenge challenge, final Category category) {
		// get current challenges for category
		final PagingWrapper<ChallengeCategory> challengeCategories = challengeCategoryDao
				.getAllForChallengeAndCategory(challenge.getId(),
						category.getId(),
						new SortingAndPaging(ObjectStatus.ACTIVE));

		ChallengeCategory challengeCategory = null;
		// if this challenge is already there and ACTIVE, delete
		if (challengeCategories.getResults() > 0) {
			for (ChallengeCategory item : challengeCategories
					.getRows()) {
				item.setObjectStatus(ObjectStatus.ACTIVE);

				// we'll just return the last one
				challengeCategory = challengeCategoryDao.save(item);
			}
		}

		return challengeCategory;
	}
}
