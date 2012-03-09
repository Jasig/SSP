package edu.sinclair.ssp.factory.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.transferobject.PersonTO;

public class PersonTOFactoryTest {

	private PersonTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new PersonTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		Person from = new Person(UUID.randomUUID());
		from.setCreatedBy(creator);
		
		PersonTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		PersonTO from = new PersonTO(UUID.randomUUID());
		from.setCreatedById(creator.getId());
		
		Person to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		Person c = new Person(UUID.randomUUID());
		List<Person> from = Lists.newArrayList();
		from.add(c);
		List<PersonTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		PersonTO c = new PersonTO(UUID.randomUUID());
		List<PersonTO> from = Lists.newArrayList();
		from.add(c);
		
		List<Person> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}

