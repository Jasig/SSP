package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.Ethnicity;
import edu.sinclair.ssp.transferobject.reference.EthnicityTO;

public class EthnicityTOFactoryTest {

	private EthnicityTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new EthnicityTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		Ethnicity from = new Ethnicity(UUID.randomUUID(), "Test Ethnicity");
		from.setCreatedBy(creator);
		
		EthnicityTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		EthnicityTO from = new EthnicityTO(UUID.randomUUID(), "Test Ethnicity");
		from.setCreatedById(creator.getId());
		
		Ethnicity to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		Ethnicity c = new Ethnicity(UUID.randomUUID(), "Test Ethnicity");
		List<Ethnicity> from = Lists.newArrayList();
		from.add(c);
		List<EthnicityTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		EthnicityTO c = new EthnicityTO(UUID.randomUUID(), "Test Ethnicity");
		List<EthnicityTO> from = Lists.newArrayList();
		from.add(c);
		
		List<Ethnicity> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
