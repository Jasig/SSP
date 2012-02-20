package edu.sinclair.ssp.service.reference;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.sinclair.ssp.model.reference.MaritalStatus;

public class MaritalStatusServiceTest {

	private MaritalStatusService service;
	
	@Before
	public void setup(){
		service = new MaritalStatusService();
	}
	
	@Test
	public void testGetAll() {
		List<MaritalStatus> all = service.getAll();
		assertTrue(all.size()>0);
	}

	@Test
	public void testGet() {
		assertNotNull(service.get(UUID.randomUUID()));
	}

	@Test
	public void testSave() {
		assertNotNull(service.save(new MaritalStatus(UUID.randomUUID(), "test status")));
	}

	@Test
	public void testDelete() {
		service.delete(UUID.randomUUID());
		assertTrue(true);
	}

}
