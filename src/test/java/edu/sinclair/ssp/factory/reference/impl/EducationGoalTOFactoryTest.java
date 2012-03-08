package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.EducationGoal;
import edu.sinclair.ssp.transferobject.reference.EducationGoalTO;

public class EducationGoalTOFactoryTest {

	private EducationGoalTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new EducationGoalTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		EducationGoal from = new EducationGoal(UUID.randomUUID(), "Test EducationGoal");
		from.setCreatedBy(creator);
		
		EducationGoalTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		EducationGoalTO from = new EducationGoalTO(UUID.randomUUID(), "Test EducationGoal");
		from.setCreatedById(creator.getId());
		
		EducationGoal to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		EducationGoal c = new EducationGoal(UUID.randomUUID(), "Test EducationGoal");
		List<EducationGoal> from = Lists.newArrayList();
		from.add(c);
		List<EducationGoalTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		EducationGoalTO c = new EducationGoalTO(UUID.randomUUID(), "Test EducationGoal");
		List<EducationGoalTO> from = Lists.newArrayList();
		from.add(c);
		
		List<EducationGoal> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
