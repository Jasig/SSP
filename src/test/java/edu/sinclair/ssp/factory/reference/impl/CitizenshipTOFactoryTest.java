package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.Citizenship;
import edu.sinclair.ssp.transferobject.reference.CitizenshipTO;

public class CitizenshipTOFactoryTest {

	private CitizenshipTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new CitizenshipTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		Citizenship from = new Citizenship(UUID.randomUUID(), "Test Citizenship");
		from.setCreatedBy(creator);
		
		CitizenshipTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		CitizenshipTO from = new CitizenshipTO(UUID.randomUUID(), "Test Citizenship");
		from.setCreatedById(creator.getId());
		
		Citizenship to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		Citizenship c = new Citizenship(UUID.randomUUID(), "Test Citizenship");
		List<Citizenship> from = Lists.newArrayList();
		from.add(c);
		List<CitizenshipTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		CitizenshipTO c = new CitizenshipTO(UUID.randomUUID(), "Test Citizenship");
		List<CitizenshipTO> from = Lists.newArrayList();
		from.add(c);
		
		List<Citizenship> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
