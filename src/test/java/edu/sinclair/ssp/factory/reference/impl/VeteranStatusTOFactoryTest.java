package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.VeteranStatus;
import edu.sinclair.ssp.transferobject.reference.VeteranStatusTO;

public class VeteranStatusTOFactoryTest {

	private VeteranStatusTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new VeteranStatusTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		VeteranStatus from = new VeteranStatus(UUID.randomUUID(), "Test VeteranStatus");
		from.setCreatedBy(creator);
		
		VeteranStatusTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		VeteranStatusTO from = new VeteranStatusTO(UUID.randomUUID(), "Test VeteranStatus");
		from.setCreatedById(creator.getId());
		
		VeteranStatus to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		VeteranStatus c = new VeteranStatus(UUID.randomUUID(), "Test VeteranStatus");
		List<VeteranStatus> from = Lists.newArrayList();
		from.add(c);
		List<VeteranStatusTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		VeteranStatusTO c = new VeteranStatusTO(UUID.randomUUID(), "Test VeteranStatus");
		List<VeteranStatusTO> from = Lists.newArrayList();
		from.add(c);
		
		List<VeteranStatus> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
