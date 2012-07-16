package org.jasig.ssp.dao.reference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ChallengeChallengeReferral;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ChallengeChallengeReferralDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChallengeChallengeReferralDaoTest.class);

	@Autowired
	private transient ChallengeChallengeReferralDao dao;

	@Autowired
	private transient ChallengeReferralDao challengeReferralDao;

	@Autowired
	private transient ChallengeDao challengeDao;

	@Autowired
	private SecurityServiceInTestEnvironment securityService;

	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final ChallengeChallengeReferral challengeChallengeReferral = dao
				.get(id);

		assertNull(challengeChallengeReferral);
	}

	@Test
	public void testSaveNew() throws ObjectNotFoundException {
		UUID saved;

		ChallengeChallengeReferral obj = new ChallengeChallengeReferral();
		obj.setChallenge(challengeDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next());
		obj.setChallengeReferral(challengeReferralDao.get(UUID
				.fromString("43724de8-93cb-411c-a9fe-322a62756d04")));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		assertNotNull(obj.getId());
		saved = obj.getId();

		LOGGER.debug(obj.toString());

		obj = dao.get(saved);
		assertNotNull(obj);
		assertNotNull(obj.getId());

		final Collection<ChallengeChallengeReferral> all = dao.getAll(
				ObjectStatus.ACTIVE)
				.getRows();
		assertNotNull(all);
		assertTrue(!all.isEmpty());
		assertList(all);

		dao.delete(obj);
	}

	private void assertList(final Collection<ChallengeChallengeReferral> objects) {
		for (final ChallengeChallengeReferral object : objects) {
			assertNotNull(object.getId());
		}
	}

	@Test
	public void uuidGeneration() throws ObjectNotFoundException {
		final ChallengeChallengeReferral obj = new ChallengeChallengeReferral();
		obj.setChallenge(challengeDao.getAll(ObjectStatus.ACTIVE).getRows()
				.iterator().next());
		obj.setChallengeReferral(challengeReferralDao.get(UUID
				.fromString("51a92fd5-7639-46dd-accc-215305e46bc0")));
		obj.setObjectStatus(ObjectStatus.ACTIVE);
		dao.save(obj);

		dao.delete(obj);
	}

	@Test
	public void getAllforChallengeReferral() {
		final PagingWrapper<ChallengeChallengeReferral> wrapper = dao
				.getAllForChallenge(
						UUID.randomUUID(), new SortingAndPaging(
								ObjectStatus.ACTIVE));
		assertList(wrapper.getRows());
	}

	@Test
	public void getAllforChallengeAndChallengeReferral() {
		final PagingWrapper<ChallengeChallengeReferral> wrapper = dao
				.getAllforChallengeReferralAndChallenge(
						UUID.randomUUID(), UUID.randomUUID(),
						new SortingAndPaging(ObjectStatus.ACTIVE));
		assertList(wrapper.getRows());
	}
}
