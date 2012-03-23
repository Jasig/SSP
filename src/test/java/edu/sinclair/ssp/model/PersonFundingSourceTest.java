package edu.sinclair.ssp.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;

import edu.sinclair.ssp.model.reference.FundingSource;

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
	public void testOverwriteWithPersonAndCollections() {
		String testString1 = "ts1";
		String testString2 = "ts2";
		Date testDate = new Date();
		Date testDate2 = new Date();
		testDate2.setTime(testDate2.getTime() + 24);

		Person person = new Person(UUID.randomUUID());
		Person person2 = new Person(UUID.randomUUID());

		FundingSource fundingSource = new FundingSource(UUID.randomUUID());
		FundingSource fundingSource2 = new FundingSource(UUID.randomUUID());

		person.setBirthDate(testDate);
		person2.setBirthDate(testDate2);

		fundingSource.setName(testString1);
		fundingSource2.setName(testString2);

		PersonFundingSource pPersistent = new PersonFundingSource();
		pPersistent.setPerson(person);
		pPersistent.setFundingSource(fundingSource);

		PersonFundingSource pFromTO = new PersonFundingSource();
		pFromTO.setPerson(person2);
		pFromTO.setFundingSource(fundingSource2);

		pPersistent.overwriteWithPersonAndFundingSource(pFromTO, person2);

		assertEquals("Person property not copied correctly.", person2,
				pPersistent.getPerson());
		assertEquals("Person.birthDate property not copied correctly.",
				testDate2, pPersistent.getPerson().getBirthDate());
		assertNotSame(
				"Person.fundingSource property copied exactly, instead of deep copying.",
				fundingSource2, pPersistent.getFundingSource());
		assertEquals("Person.fundingSource property not copied correctly.",
				testString2, pPersistent.getFundingSource().getName());
	}
}
