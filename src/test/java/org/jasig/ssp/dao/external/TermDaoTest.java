package org.jasig.ssp.dao.external;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.SortingAndPaging;
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
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class TermDaoTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TermDaoTest.class);

	@Autowired
	private transient TermDao dao;

	@Test
	public void getAll() throws ObjectNotFoundException {
		Collection<Term> all = dao.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertList(all);
	}

	@Test(expected = ObjectNotFoundException.class)
	public void testNull() throws ObjectNotFoundException {
		final String[] id = { "id" };
		final Term term = dao.get(id);

		assertNull(term);
	}

	private void assertList(final Collection<Term> objects) {
		for (Term object : objects) {
			assertNotNull(object.getId());
		}
	}

}
