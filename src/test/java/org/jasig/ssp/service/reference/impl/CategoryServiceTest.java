package org.jasig.ssp.service.reference.impl; // NOPMD

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.dao.reference.CategoryDao;
import org.jasig.ssp.dao.reference.ChallengeCategoryDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Category;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.model.reference.ChallengeCategory;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class CategoryServiceTest {

	private transient CategoryServiceImpl service;

	private transient CategoryDao dao;
	private transient ChallengeCategoryDao challengeCategoryDao;

	@Before
	public void setUp() {
		dao = createMock(CategoryDao.class);
		challengeCategoryDao = createMock(ChallengeCategoryDao.class);
		service = new CategoryServiceImpl(dao, challengeCategoryDao);
	}

	@Test
	public void testGetAll() {
		final List<Category> daoAll = new ArrayList<Category>();
		daoAll.add(new Category());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<Category>(daoAll));

		replay(dao);

		final List<Category> all = (List<Category>) service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("GetAll() should not have return an empty set.",
				all.isEmpty());
		verify(dao);
	}

	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Category daoOne = new Category(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Lookup should not have returned null.", service.get(id));
		verify(dao);
	}

	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final Category daoOne = new Category(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Saved instance should not have been null.",
				service.save(daoOne));
		verify(dao);
	}

	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Category daoOne = new Category(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(
				new ObjectNotFoundException(id, "Category"));

		replay(dao);

		service.delete(id);

		try {
			service.get(id);
			fail("Deleted item should not have been found.");
		} catch (final ObjectNotFoundException e) {
			verify(dao);
		}
	}

	@Test
	public void addChallengeToCategory() {
		final Challenge challenge = new Challenge(UUID.randomUUID());
		final Category category = new Category(UUID.randomUUID());

		final List<ChallengeCategory> challengeCategories = Lists
				.newArrayList();

		expect(
				challengeCategoryDao.getAllForChallengeAndCategory(
						eq(challenge.getId()), eq(category.getId()),
						isA(SortingAndPaging.class)))
				.andReturn(
						new PagingWrapper<ChallengeCategory>(0L,
								challengeCategories));
		expect(challengeCategoryDao.save(isA(ChallengeCategory.class)))
				.andReturn(new ChallengeCategory());

		replay(challengeCategoryDao);

		assertNotNull("AddChallengeToCategory should not have returned null.",
				service.addChallengeToCategory(challenge, category));

		verify(challengeCategoryDao);
	}

	@Test
	public void removeChallengeFromCategory() {
		final Challenge challenge = new Challenge(UUID.randomUUID());
		final Category category = new Category(UUID.randomUUID());

		final List<ChallengeCategory> challengeCategories = Lists
				.newArrayList();
		challengeCategories.add(new ChallengeCategory());

		expect(
				challengeCategoryDao.getAllForChallengeAndCategory(
						eq(challenge.getId()), eq(category.getId()),
						isA(SortingAndPaging.class)))
				.andReturn(
						new PagingWrapper<ChallengeCategory>(1L,
								challengeCategories));
		expect(challengeCategoryDao.save(isA(ChallengeCategory.class)))
				.andReturn(new ChallengeCategory());

		replay(challengeCategoryDao);

		assertNotNull(
				"RemoveChallengeFromCategory should not have returned null.",
				service.removeChallengeFromCategory(challenge, category));

		verify(challengeCategoryDao);
	}
}