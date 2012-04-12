package org.studentsuccessplan.ssp.dao;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.studentsuccessplan.ssp.model.CustomTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("reference/dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class CustomTaskDaoTest extends AbstractTaskDaoTest<CustomTask> {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Autowired
	private CustomTaskDao dao;

	@Override
	protected CustomTaskDao getDao() {
		return dao;
	}

}
