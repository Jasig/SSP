package edu.sinclair.ssp.service.reference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.sinclair.ssp.model.reference.Challenge;

public class ChallengeServiceTest {

	private ChallengeService service;
	
	@Before
	public void setup(){
		service = new ChallengeService();
	}
	
	@Test
	public void testGetAll() {
		List<Challenge> all = service.getAll();
		assertTrue(all.size()>0);
	}

	@Test
	public void testGet() {
		assertNotNull(service.get(UUID.randomUUID()));
	}

	@Test
	public void testSave() {
		assertNotNull(service.save(new Challenge(UUID.randomUUID(), "test challenge")));
	}

	@Test
	public void testDelete() {
		service.delete(UUID.randomUUID());
		assertTrue(true);
	}

}
