package edu.sinclair.ssp.factory.reference.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.Challenge;
import edu.sinclair.ssp.service.PersonService;
import edu.sinclair.ssp.service.reference.ChallengeService;
import edu.sinclair.ssp.transferobject.reference.ChallengeTO;

public class ChallengeTOFactoryTest {

	private ChallengeTOFactoryImpl factory;
	private PersonService personService;
	private ChallengeService service;

	@Before
	public void setup(){
		factory = new ChallengeTOFactoryImpl();
		service = createMock(ChallengeService.class);
		personService = createMock(PersonService.class);
		
		factory.setChallengeService(service);
		factory.setPersonService(personService);
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
		
		expect(service.get(from.getId())).andReturn(null);
		expect(personService.personFromId(creator.getId())).andReturn(creator);
		
		replay(service);
		replay(personService);
		
		Challenge to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
		verify(service);
		verify(personService);
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
		
		expect(service.get(c.getId())).andReturn(null);
		
		replay(service);
		
		List<Challenge> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
		verify(service);
	}
}
