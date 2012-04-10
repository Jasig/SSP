package org.studentsuccessplan.ssp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.UUID;

import org.junit.Test;

import org.studentsuccessplan.ssp.model.reference.FundingSource;

public class PersonFundingSourceTest {

	@Test
	public void testOverwrite() {
		String testString1 = "ts1";
		String testString2 = "ts2";

		PersonFundingSource pPersistent = new PersonFundingSource();
		pPersistent.setDescription(testString1);

		PersonFundingSource pFromTO = new PersonFundingSource();
		pFromTO.setDescription(testString2);

		pPersistent.overwrite(pFromTO);

		assertEquals("Field not copied correctly.", testString2,
				pPersistent.getDescription());
	}

	@Test
	public void testOverwriteWithPersonAndFundingSource() {
		String testString1 = "teststring1";
		String testString2 = "teststring2";
		String testString3 = "teststring3";

		Person person = new Person(UUID.randomUUID());
		Person person2 = new Person(UUID.randomUUID());

		FundingSource fundingSource = new FundingSource(UUID.randomUUID());
		FundingSource fundingSource2 = new FundingSource(UUID.randomUUID());

		fundingSource.setName(testString1);
		fundingSource2.setName(testString2);

		PersonFundingSource pPersistent = new PersonFundingSource();
		pPersistent.setPerson(person);
		pPersistent.setFundingSource(fundingSource);

		PersonFundingSource pFromTO = new PersonFundingSource();
		pFromTO.setPerson(person2);
		pFromTO.setFundingSource(fundingSource2);
		pFromTO.setDescription(testString3);

		pPersistent.overwrite(pFromTO);

		assertEquals(
				"PersonChallenge.Person property copied when it shouldn't have been.",
				person, pPersistent.getPerson());
		assertNotSame(
				"Person.challenge property copied exactly, instead of deep copying.",
				fundingSource2, pPersistent.getFundingSource());
		assertEquals(
				"PersonChallenge.Challenge property copied when it shouldn't have been.",
				testString1, pPersistent.getFundingSource().getName());
		assertEquals(
				"PersonChallenge.Description property not copied correctly.",
				testString3, pPersistent.getDescription());
	}
}
