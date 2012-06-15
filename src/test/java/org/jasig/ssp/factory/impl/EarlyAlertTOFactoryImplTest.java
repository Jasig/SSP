/**
 * 
 */
package org.jasig.ssp.factory.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.jasig.ssp.factory.EarlyAlertTOFactory;
import org.jasig.ssp.model.EarlyAlert;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for {@link EarlyAlertTOFactoryImpl}
 * 
 * @author jon.adams
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../service/service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class EarlyAlertTOFactoryImplTest {
	@Autowired
	private transient EarlyAlertTOFactory toFactory;

	/**
	 * Test method for EarlyAlertTOFactoryImpl#getDao().
	 * 
	 * @throws ObjectNotFoundException
	 *             If object not found, which is expected for this test.
	 */
	@Test(expected = ObjectNotFoundException.class)
	public void testGetDaoViaBaseClassFromCall() throws ObjectNotFoundException {
		assertNotNull("DAO should not have been null.",
				toFactory.from(UUID.randomUUID()));
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.factory.impl.EarlyAlertTOFactoryImpl#from(org.jasig.ssp.transferobject.EarlyAlertTO)}
	 * .
	 */
	@Test
	public void testFromEarlyAlertTOWithMissingData() {
		final EarlyAlertTO obj = toFactory.from(new EarlyAlert());
		assertNull("Campus should have been null.", obj.getCampusId());
		assertNull("Person should have been null.", obj.getPersonId());
		assertTrue("EarlyAlertReasonIds should not have been empty.",
				obj.getEarlyAlertReasonIds().isEmpty());
	}
}