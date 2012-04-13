package org.studentsuccessplan.ssp.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.studentsuccessplan.ssp.model.reference.Challenge;
import org.studentsuccessplan.ssp.transferobject.reference.ChallengeTO;

import com.google.common.collect.Lists;

public class TransferObjectListFactoryTest {

	private TransferObjectListFactory<ChallengeTO, Challenge> listFactory;

	@Before
	public void setup() {
		listFactory = TransferObjectListFactory.newFactory(ChallengeTO.class);
	}

	@Test
	public void toTOList() {
		List<Challenge> models = Lists.newArrayList();
		models.add(new Challenge(UUID.randomUUID()));

		List<ChallengeTO> tos = listFactory.toTOList(models);

		assertNotNull(tos);
		assertEquals(1, tos.size());
		assertEquals(tos.get(0).getId(), models.get(0).getId());
	}

	@Test
	public void toModelList() {
		List<ChallengeTO> tos = Lists.newArrayList();
		tos.add(new ChallengeTO(UUID.randomUUID()));

		List<Challenge> models = listFactory.toModelList(tos);

		assertNotNull(models);
		assertEquals(1, models.size());
		assertEquals(tos.get(0).getId(), models.get(0).getId());
	}

}
