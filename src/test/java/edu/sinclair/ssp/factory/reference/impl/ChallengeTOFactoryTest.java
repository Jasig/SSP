package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;

public class ChallengeTOFactoryTest {

	private ChallengeTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new ChallengeTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		Challenge from = new Challenge(UUID.randomUUID(), "Test Challenge");
		from.setCreatedBy(creator);
		
		ChallengeTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		ChallengeTO from = new ChallengeTO(UUID.randomUUID(), "Test Challenge");
		from.setCreatedById(creator.getId());
		
		Challenge to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		Challenge c = new Challenge(UUID.randomUUID(), "Test Challenge");
		List<Challenge> from = Lists.newArrayList();
		from.add(c);
		List<ChallengeTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		ChallengeTO c = new ChallengeTO(UUID.randomUUID(), "Test Challenge");
		List<ChallengeTO> from = Lists.newArrayList();
		from.add(c);
		
		List<Challenge> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
