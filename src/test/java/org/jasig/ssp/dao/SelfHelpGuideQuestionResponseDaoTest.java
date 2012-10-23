/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.SelfHelpGuideQuestionResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-testConfig.xml")
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class SelfHelpGuideQuestionResponseDaoTest {

	@Autowired
	private SelfHelpGuideQuestionResponseDao dao;

	@Test
	public void criticalResponsesForEarlyAlert() {
		assertList(dao.criticalResponsesForEarlyAlert());
	}

	private void assertList(final List<SelfHelpGuideQuestionResponse> objects) {
		for (final SelfHelpGuideQuestionResponse object : objects) {
			assertNotNull(object.getId());
		}
		assertTrue(true);
	}

	@Test
	public void testGetAll() {
		assertList((List<SelfHelpGuideQuestionResponse>) dao.getAll(
				ObjectStatus.ALL).getRows());
	}
}
