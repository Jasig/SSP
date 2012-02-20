package edu.sinclair.ssp.service.reference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.sinclair.ssp.model.reference.EducationLevel;

public class EducationLevelServiceTest {

	private EducationLevelService service;
	
	@Before
	public void setup(){
		service = new EducationLevelService();
	}
	
	@Test
	public void testGetAll() {
		List<EducationLevel> all = service.getAll();
		assertTrue(all.size()>0);
	}

	@Test
	public void testGet() {
		assertNotNull(service.get(UUID.randomUUID()));
	}

	@Test
	public void testSave() {
		assertNotNull(service.save(new EducationLevel(UUID.randomUUID(), "test level")));
	}

	@Test
	public void testDelete() {
		service.delete(UUID.randomUUID());
		assertTrue(true);
	}

}
