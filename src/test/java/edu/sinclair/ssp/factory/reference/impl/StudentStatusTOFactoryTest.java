package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.StudentStatus;
import edu.sinclair.ssp.transferobject.reference.StudentStatusTO;

public class StudentStatusTOFactoryTest {

	private StudentStatusTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new StudentStatusTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		StudentStatus from = new StudentStatus(UUID.randomUUID(), "Test StudentStatus");
		from.setCreatedBy(creator);
		
		StudentStatusTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		StudentStatusTO from = new StudentStatusTO(UUID.randomUUID(), "Test StudentStatus");
		from.setCreatedById(creator.getId());
		
		StudentStatus to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		StudentStatus c = new StudentStatus(UUID.randomUUID(), "Test StudentStatus");
		List<StudentStatus> from = Lists.newArrayList();
		from.add(c);
		List<StudentStatusTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		StudentStatusTO c = new StudentStatusTO(UUID.randomUUID(), "Test StudentStatus");
		List<StudentStatusTO> from = Lists.newArrayList();
		from.add(c);
		
		List<StudentStatus> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
