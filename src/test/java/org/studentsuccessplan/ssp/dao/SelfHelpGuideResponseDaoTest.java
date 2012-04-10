package org.studentsuccessplan.ssp.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import org.studentsuccessplan.ssp.model.ObjectStatus;
import org.studentsuccessplan.ssp.model.SelfHelpGuideResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class SelfHelpGuideResponseDaoTest {

	@Autowired
	private SelfHelpGuideResponseDao dao;

	@Test
	public void forEarlyAlert() {
		assertList(dao.forEarlyAlert());
	}

	private void assertList(List<SelfHelpGuideResponse> objects) {
		for (SelfHelpGuideResponse object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}

	@Test
	public void testGetAll() {
		assertList(dao.getAll(ObjectStatus.ALL));
	}
}
