package org.jasig.ssp.dao.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class ExternalPersonDaoTest {

	@Autowired
	private transient ExternalPersonDao dao;

	@Test
	public void getBySchoolId() {
		ExternalPerson person = null;
		try {
			person = dao.getBySchoolId("notInSsp");
		} catch (ObjectNotFoundException e) {
			fail("external User not found");
		}
		assertNotNull("Person was not found", person);
		assertEquals("Incorrect school id", "notInSsp", person.getSchoolId());
		assertEquals("Incorrect coach", "turing.1", person.getCoachSchoolId());
	}

	@Test(expected = ObjectNotFoundException.class)
	public void getBySchoolIdThrowException() throws ObjectNotFoundException {
		dao.getBySchoolId("borkborkbork");
	}

	@Test
	public void getBySchoolIds() {
		final List<String> schoolIds = Lists.newArrayList();
		schoolIds.add("ken.1");
		schoolIds.add("turing.1");

		final PagingWrapper<ExternalPerson> diff = dao
				.getBySchoolIds(schoolIds, new SortingAndPaging(
						ObjectStatus.ACTIVE));

		assertEquals("Incorrect number of external_user entries", 2L,
				diff.getResults());
	}
}
