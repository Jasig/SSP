package org.jasig.ssp.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.SelfHelpGuideQuestionResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class SelfHelpGuideQuestionResponseDaoTest {

	@Autowired
	private SelfHelpGuideQuestionResponseDao dao;

	@Test
	public void criticalResponsesForEarlyAlert() {
		assertList(dao.criticalResponsesForEarlyAlert());
	}

	private void assertList(final List<SelfHelpGuideQuestionResponse> objects) {
		for (final SelfHelpGuideQuestionResponse object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}

	@Test
	public void testGetAll() {
		assertList((List<SelfHelpGuideQuestionResponse>) dao.getAll(
				ObjectStatus.ALL).getRows());
	}
}
