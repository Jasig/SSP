package org.jasig.ssp.dao.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.RegistrationStatusByTerm;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class RegistrationStatusByTermDaoTest {

	@Autowired
	private transient RegistrationStatusByTermDao dao;

	@Autowired
	private transient PersonService personService;

	@Test
	public void getAll() throws ObjectNotFoundException {
		final Collection<RegistrationStatusByTerm> all = dao.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("GetAll should have returned some data.", all.isEmpty());
	}

	@Test
	public void getAllForPerson() throws ObjectNotFoundException {
		final Collection<RegistrationStatusByTerm> all = dao.getAllForPerson(
				personService.getBySchoolId("ken.1"),
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("GetAll should have returned some data.", all.isEmpty());
	}

	@Test
	public void getForTerm() throws ObjectNotFoundException {
		final RegistrationStatusByTerm rsbt = dao.getForTerm(
				personService.getBySchoolId("ken.1"), new Term("FA12"));
		assertEquals("should have found one reg status for ken", "ken.1",
				rsbt.getSchoolId());
		assertEquals("whould have found one reg status for autumn 12 for ken",
				"FA12", rsbt.getTermCode());
	}

	@Test
	public void getForTermWhenZero() throws ObjectNotFoundException {
		final RegistrationStatusByTerm rsbt = dao.getForTerm(
				personService.getBySchoolId("ken.1"), new Term("SP13"));
		assertNull(
				"Should not find any values with a course_count greater than zero",
				rsbt);
	}
}
