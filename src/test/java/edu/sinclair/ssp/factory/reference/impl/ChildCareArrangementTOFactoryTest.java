package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.ChildCareArrangement;
import edu.sinclair.ssp.transferobject.reference.ChildCareArrangementTO;

public class ChildCareArrangementTOFactoryTest {

	private ChildCareArrangementTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new ChildCareArrangementTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		ChildCareArrangement from = new ChildCareArrangement(UUID.randomUUID(), "Test ChildCareArrangement");
		from.setCreatedBy(creator);
		
		ChildCareArrangementTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		ChildCareArrangementTO from = new ChildCareArrangementTO(UUID.randomUUID(), "Test ChildCareArrangement");
		from.setCreatedById(creator.getId());
		
		ChildCareArrangement to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		ChildCareArrangement c = new ChildCareArrangement(UUID.randomUUID(), "Test ChildCareArrangement");
		List<ChildCareArrangement> from = Lists.newArrayList();
		from.add(c);
		List<ChildCareArrangementTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		ChildCareArrangementTO c = new ChildCareArrangementTO(UUID.randomUUID(), "Test ChildCareArrangement");
		List<ChildCareArrangementTO> from = Lists.newArrayList();
		from.add(c);
		
		List<ChildCareArrangement> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
