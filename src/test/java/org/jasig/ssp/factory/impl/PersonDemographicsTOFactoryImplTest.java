/**
 * 
 */
package org.jasig.ssp.factory.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import org.jasig.ssp.factory.PersonDemographicsTOFactory;
import org.jasig.ssp.model.PersonDemographics;
import org.jasig.ssp.model.reference.ChildCareArrangement;
import org.jasig.ssp.model.reference.Citizenship;
import org.jasig.ssp.model.reference.EmploymentShifts;
import org.jasig.ssp.model.reference.Ethnicity;
import org.jasig.ssp.model.reference.Genders;
import org.jasig.ssp.model.reference.MaritalStatus;
import org.jasig.ssp.model.reference.VeteranStatus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.PersonDemographicsTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for {@link PersonDemographicsTOFactoryImpl}
 * 
 * @author jon.adams
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../service/service-testConfig.xml")
@TransactionConfiguration
@Transactional
public class PersonDemographicsTOFactoryImplTest {
	@Autowired
	private transient PersonDemographicsTOFactory toFactory;

	/**
	 * Test method for PersonDemographicsTOFactoryImpl#getDao().
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
	 * {@link org.jasig.ssp.factory.impl.PersonDemographicsTOFactoryImpl#from(org.jasig.ssp.transferobject.PersonDemographicsTO)}
	 * .
	 */
	@Test
	public void testFromPersonDemographicsTOWithMissingData() {
		final PersonDemographicsTO obj = toFactory
				.from(new PersonDemographics());
		assertNull("Marital Status should have been null.",
				obj.getMaritalStatusId());
		assertNull("Person should have been null.", obj.getPersonId());
		assertNull("Citizenship should have been null.", obj.getCitizenshipId());
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.factory.impl.PersonDemographicsTOFactoryImpl#from(org.jasig.ssp.transferobject.PersonDemographicsTO)}
	 * .
	 */
	@Test
	public void testFromPersonDemographicsTO() {
		final PersonDemographics obj = new PersonDemographics();
		obj.setGender(Genders.M);
		obj.setEthnicity(new Ethnicity());
		obj.setMaritalStatus(new MaritalStatus());
		obj.setCitizenship(new Citizenship());
		obj.setVeteranStatus(new VeteranStatus());
		obj.setChildCareArrangement(new ChildCareArrangement());
		obj.setShift(EmploymentShifts.FIRST);

		final PersonDemographicsTO to = toFactory.from(obj);
		assertEquals("Genders did not match.", Genders.M.toString(),
				to.getGender());
		assertEquals("Shifts did not match.",
				EmploymentShifts.FIRST.toString(),
				to.getShift());
	}
}