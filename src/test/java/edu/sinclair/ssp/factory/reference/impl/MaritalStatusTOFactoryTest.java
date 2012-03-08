package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.transferobject.reference.MaritalStatusTO;

public class MaritalStatusTOFactoryTest {

	private MaritalStatusTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new MaritalStatusTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		MaritalStatus from = new MaritalStatus(UUID.randomUUID(), "Test MaritalStatus");
		from.setCreatedBy(creator);
		
		MaritalStatusTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		MaritalStatusTO from = new MaritalStatusTO(UUID.randomUUID(), "Test MaritalStatus");
		from.setCreatedById(creator.getId());
		
		MaritalStatus to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		MaritalStatus c = new MaritalStatus(UUID.randomUUID(), "Test MaritalStatus");
		List<MaritalStatus> from = Lists.newArrayList();
		from.add(c);
		List<MaritalStatusTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		MaritalStatusTO c = new MaritalStatusTO(UUID.randomUUID(), "Test MaritalStatus");
		List<MaritalStatusTO> from = Lists.newArrayList();
		from.add(c);
		
		List<MaritalStatus> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
