package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.EducationLevel;
import edu.sinclair.ssp.transferobject.reference.EducationLevelTO;

public class EducationLevelTOFactoryTest {

	private EducationLevelTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new EducationLevelTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		EducationLevel from = new EducationLevel(UUID.randomUUID(), "Test EducationLevel");
		from.setCreatedBy(creator);
		
		EducationLevelTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		EducationLevelTO from = new EducationLevelTO(UUID.randomUUID(), "Test EducationLevel");
		from.setCreatedById(creator.getId());
		
		EducationLevel to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		EducationLevel c = new EducationLevel(UUID.randomUUID(), "Test EducationLevel");
		List<EducationLevel> from = Lists.newArrayList();
		from.add(c);
		List<EducationLevelTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		EducationLevelTO c = new EducationLevelTO(UUID.randomUUID(), "Test EducationLevel");
		List<EducationLevelTO> from = Lists.newArrayList();
		from.add(c);
		
		List<EducationLevel> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
