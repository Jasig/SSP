package edu.sinclair.ssp.factory.reference.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.reference.FundingSource;
import edu.sinclair.ssp.transferobject.reference.FundingSourceTO;

public class FundingSourceTOFactoryTest {

	private FundingSourceTOFactoryImpl factory;

	@Before
	public void setup(){
		factory = new FundingSourceTOFactoryImpl();

	}
	
	@Test
	public void toTO(){
		Person creator = new Person(UUID.randomUUID());
		
		FundingSource from = new FundingSource(UUID.randomUUID(), "Test FundingSource");
		from.setCreatedBy(creator);
		
		FundingSourceTO to = factory.toTO(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toModel(){
		Person creator = new Person(UUID.randomUUID());
		
		FundingSourceTO from = new FundingSourceTO(UUID.randomUUID(), "Test FundingSource");
		from.setCreatedById(creator.getId());
		
		FundingSource to = factory.toModel(from);
		
		assertNotNull(to);
		assertEquals(from.getId(), to.getId());
	}
	
	@Test
	public void toTOList(){
		FundingSource c = new FundingSource(UUID.randomUUID(), "Test FundingSource");
		List<FundingSource> from = Lists.newArrayList();
		from.add(c);
		List<FundingSourceTO> to = factory.toTOList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
	
	@Test
	public void toModelList(){
		FundingSourceTO c = new FundingSourceTO(UUID.randomUUID(), "Test FundingSource");
		List<FundingSourceTO> from = Lists.newArrayList();
		from.add(c);
		
		List<FundingSource> to = factory.toModelList(from);
		
		assertNotNull(to);
		assertEquals(1, to.size());
		assertEquals(c.getId(), to.get(0).getId());
	}
}
