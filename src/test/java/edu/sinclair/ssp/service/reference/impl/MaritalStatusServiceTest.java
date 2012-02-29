package edu.sinclair.ssp.service.reference.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.sinclair.ssp.model.reference.MaritalStatus;
import edu.sinclair.ssp.service.reference.impl.MaritalStatusServiceImpl;

public class MaritalStatusServiceTest {

	private MaritalStatusServiceImpl service;
	
	@Before
	public void setup(){
		service = new MaritalStatusServiceImpl();
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
